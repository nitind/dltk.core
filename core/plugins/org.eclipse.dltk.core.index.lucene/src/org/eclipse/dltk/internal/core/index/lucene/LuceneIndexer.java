/*******************************************************************************
 * Copyright (c) 2016, 2019 Zend Technologies and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index.lucene;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Scorable;
import org.apache.lucene.search.ScoreMode;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IDLTKLanguageToolkitExtension;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.core.index2.AbstractIndexer;
import org.eclipse.dltk.core.index2.search.ISearchEngine;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.internal.core.util.Util;

/**
 * Lucene based implementation for DLTK indexer.
 * 
 * @author Michal Niewrzal, Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class LuceneIndexer extends AbstractIndexer {

	private static final class TimestampsCollector implements Collector {

		private final Map<String, Long> fResult;

		public TimestampsCollector(Map<String, Long> result) {
			this.fResult = result;
		}

		@Override
		public ScoreMode scoreMode() {
			return ScoreMode.COMPLETE_NO_SCORES;
		}

		@Override
		public LeafCollector getLeafCollector(LeafReaderContext context)
				throws IOException {
			final LeafReader reader = context.reader();
			final NumericDocValues timestampField = reader
					.getNumericDocValues(IndexFields.NDV_TIMESTAMP);
			final BinaryDocValues pathField = reader
					.getBinaryDocValues(IndexFields.F_PATH);
			return new LeafCollector() {
				@Override
				public void setScorer(Scorable scorable) throws IOException {
				}

				@Override
				public void collect(int docId) throws IOException {
					if (timestampField.advanceExact(docId)
							&& pathField.advanceExact(docId)) {
						fResult.put(pathField.binaryValue().utf8ToString(),
								timestampField.longValue());
					}
				}
			};
		}

	}

	private String fFile;
	private String fContainer;

	@Override
	public ISearchEngine createSearchEngine() {
		return new LuceneSearchEngine();
	}

	@Override
	public Map<String, Long> getDocuments(IPath containerPath) {
		IndexSearcher indexSearcher = null;
		String container = containerPath.toString();
		try {
			final Map<String, Long> result = new HashMap<>();
			indexSearcher = LuceneManager.INSTANCE
					.findTimestampsSearcher(container).acquire();
			indexSearcher.search(new MatchAllDocsQuery(),
					new TimestampsCollector(result));
			return result;
		} catch (IOException e) {
			Logger.logException(e);
		} finally {
			if (indexSearcher != null) {
				try {
					LuceneManager.INSTANCE.findTimestampsSearcher(container)
							.release(indexSearcher);
				} catch (IOException e) {
					Logger.logException(e);
				}
			}
		}
		return Collections.emptyMap();
	}

	@Override
	public void addDeclaration(DeclarationInfo info) {
		IndexWriter writer = LuceneManager.INSTANCE.findIndexWriter(fContainer,
				IndexType.DECLARATIONS, info.elementType);
		try {
			writer.addDocument(
					DocumentFactory.createForDeclaration(fFile, info));
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	public void addReference(ReferenceInfo info) {
		IndexWriter writer = LuceneManager.INSTANCE.findIndexWriter(fContainer,
				IndexType.REFERENCES, info.elementType);
		try {
			writer.addDocument(DocumentFactory.createForReference(fFile, info));
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	public void indexDocument(ISourceModule sourceModule) {
		final IFileHandle fileHandle = EnvironmentPathUtils
				.getFile(sourceModule);
		IDLTKLanguageToolkit toolkit = DLTKLanguageManager
				.getLanguageToolkit(sourceModule);
		if (toolkit == null) {
			return;
		}
		resetDocument(sourceModule, toolkit);
		long lastModified = fileHandle == null ? 0 : fileHandle.lastModified();
		// Cleanup and write new info...
		LuceneManager.INSTANCE.delete(fContainer, fFile);
		IndexWriter indexWriter = LuceneManager.INSTANCE
				.findTimestampsWriter(fContainer);
		try {
			indexWriter.addDocument(
					DocumentFactory.createForTimestamp(fFile, lastModified));
		} catch (Exception e) {
			Logger.logException(e);
		}
		super.indexDocument(sourceModule);
	}

	@Override
	public void removeContainer(IPath containerPath) {
		LuceneManager.INSTANCE.delete(containerPath.toString());
	}

	@Override
	public void removeDocument(IPath containerPath, String sourceModulePath) {
		LuceneManager.INSTANCE.delete(containerPath.toString(),
				sourceModulePath);
	}

	private void resetDocument(ISourceModule sourceModule,
			IDLTKLanguageToolkit toolkit) {
		IPath containerPath;
		if (sourceModule instanceof SourceModule) {
			containerPath = sourceModule.getScriptProject().getPath();
		} else {
			containerPath = sourceModule
					.getAncestor(IModelElement.PROJECT_FRAGMENT).getPath();
		}
		String relativePath;
		if (toolkit instanceof IDLTKLanguageToolkitExtension
				&& ((IDLTKLanguageToolkitExtension) toolkit)
						.isArchiveFileName(sourceModule.getPath().toString())) {
			relativePath = ((ExternalSourceModule) sourceModule).getFullPath()
					.toString();
		} else {
			relativePath = Util.relativePath(sourceModule.getPath(),
					containerPath.segmentCount());
		}
		this.fContainer = containerPath.toString();
		this.fFile = relativePath;
	}

}
