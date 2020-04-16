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

import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_DOC;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_ELEMENT_NAME;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_METADATA;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_PARENT;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_PATH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.BDV_QUALIFIER;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_CC_NAME;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_ELEMENT_NAME_LC;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_PARENT;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_PATH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.F_QUALIFIER;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_FLAGS;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_LENGTH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_NAME_LENGTH;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_NAME_OFFSET;
import static org.eclipse.dltk.internal.core.index.lucene.IndexFields.NDV_OFFSET;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorable;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.BytesRef;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.dltk.core.index2.search.ISearchEngineExtension;
import org.eclipse.dltk.core.index2.search.ISearchRequestor;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.search.DLTKSearchScope;

/**
 * Lucene based implementation for DLTK search engine.
 * 
 * @author Michal Niewrzal, Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class LuceneSearchEngine implements ISearchEngineExtension {

	private static final class SearchScope {

		static List<String> getContainers(IDLTKSearchScope scope) {
			List<String> containers = new ArrayList<>();
			for (IPath path : scope.enclosingProjectsAndZips()) {
				containers.add(path.toString());
			}
			return containers;
		}

		static List<String> getScripts(IDLTKSearchScope scope) {
			List<String> scripts = new ArrayList<>();
			if (scope instanceof DLTKSearchScope) {
				String[] relativePaths = ((DLTKSearchScope) scope)
						.getRelativePaths();
				String[] fileExtensions = ScriptModelUtil
						.getFileExtensions(scope.getLanguageToolkit());
				for (String relativePath : relativePaths) {
					if (relativePath.length() > 0) {
						if (fileExtensions != null) {
							boolean isScriptFile = false;
							for (String ext : fileExtensions) {
								if (relativePath.endsWith("." + ext)) { //$NON-NLS-1$
									isScriptFile = true;
									break;
								}
							}
							if (!isScriptFile) {
								break;
							}
						}
						scripts.add(relativePath);
					}
				}
			}
			return scripts;
		}

	}

	private static final class ResultsCollector implements Collector {

		private static final String[] NUMERIC_FIELDS = new String[] {
				NDV_OFFSET, NDV_LENGTH, NDV_FLAGS, NDV_NAME_OFFSET,
				NDV_NAME_LENGTH };
		private static final String[] BINARY_FIELDS = new String[] { BDV_PATH,
				BDV_ELEMENT_NAME, BDV_QUALIFIER, BDV_PARENT, BDV_METADATA,
				BDV_DOC };
		private Map<String, NumericDocValues> fDocNumericValues;
		private Map<String, BinaryDocValues> fDocBinaryValues;
		private String fContainer;
		private int fElementType;
		private List<SearchMatch> fResult = new LinkedList<>();

		public ResultsCollector(String container, int elementType) {
			this.fContainer = container;
			this.fElementType = elementType;
		}

		public List<SearchMatch> getfResult() {
			return fResult;
		}

		@Override
		public ScoreMode scoreMode() {
			return ScoreMode.COMPLETE_NO_SCORES;
		}

		@Override
		public LeafCollector getLeafCollector(final LeafReaderContext context)
				throws IOException {
			final LeafReader reader = context.reader();
			fDocNumericValues = new HashMap<>();
			for (String field : NUMERIC_FIELDS) {
				NumericDocValues docValues = reader.getNumericDocValues(field);
				if (docValues != null) {
					fDocNumericValues.put(field, docValues);
				}
			}
			fDocBinaryValues = new HashMap<>();
			for (String field : BINARY_FIELDS) {
				BinaryDocValues docValues = reader.getBinaryDocValues(field);
				if (docValues != null) {
					fDocBinaryValues.put(field, docValues);
				}
			}
			return new LeafCollector() {

				@Override
				public void setScorer(Scorable scorable) throws IOException {
				}

				@Override
				public void collect(int docId) throws IOException {
					addResult(docId);
				}
			};
		}

		private void addResult(int docId) {
			fResult.add(new SearchMatch(fContainer, fElementType,
					getNumericValue(NDV_OFFSET, docId),
					getNumericValue(NDV_LENGTH, docId),
					getNumericValue(NDV_NAME_OFFSET, docId),
					getNumericValue(NDV_NAME_LENGTH, docId),
					getNumericValue(NDV_FLAGS, docId),
					getStringValue(BDV_ELEMENT_NAME, docId),
					getStringValue(BDV_PATH, docId),
					getStringValue(BDV_PARENT, docId),
					getStringValue(BDV_QUALIFIER, docId),
					getStringValue(BDV_DOC, docId),
					getStringValue(BDV_METADATA, docId)));
		}

		private long getNumericValue(String field, int docId) {
			NumericDocValues docValues = fDocNumericValues.get(field);
			if (docValues != null) {
				try {
					docValues.advanceExact(docId);
					if (!docValues.advanceExact(docId)) {
						return 0;
					}
					return docValues.longValue();
				} catch (IOException e) {
					Logger.logException(e);
				}
			}
			return 0;
		}

		private String getStringValue(String field, int docId) {
			BinaryDocValues docValues = fDocBinaryValues.get(field);
			if (docValues != null) {
				try {
					if (!docValues.advanceExact(docId)) {
						return null;
					}
					BytesRef bytesRef = docValues.binaryValue();
					if (bytesRef.length > 0)
						return bytesRef.utf8ToString();
				} catch (IOException e) {
					Logger.logException(e);
				}

			}
			return null;
		}

	}

	@Override
	public void search(int elementType, String qualifier, String elementName,
			int trueFlags, int falseFlags, int limit, SearchFor searchFor,
			MatchRule matchRule, IDLTKSearchScope scope,
			ISearchRequestor requestor, IProgressMonitor monitor) {
		search(elementType, qualifier, elementName, null, trueFlags, falseFlags,
				limit, searchFor, matchRule, scope, requestor, monitor);
	}

	@Override
	public void search(int elementType, String qualifier, String elementName,
			String parent, int trueFlags, int falseFlags, int limit,
			SearchFor searchFor, MatchRule matchRule, IDLTKSearchScope scope,
			ISearchRequestor requestor, IProgressMonitor monitor) {
		boolean searchForDecls = searchFor == SearchFor.DECLARATIONS
				|| searchFor == SearchFor.ALL_OCCURRENCES;
		boolean searchForRefs = searchFor == SearchFor.REFERENCES
				|| searchFor == SearchFor.ALL_OCCURRENCES;

		List<SearchTask> tasks = new LinkedList<>();
		List<String> containers = SearchScope.getContainers(scope);
		List<String> scripts = SearchScope.getScripts(scope);
		final SearchMatchHandler searchMatchHandler = new SearchMatchHandler(
				scope, requestor);
		if (searchForRefs) {
			for (String container : containers) {
				tasks.add(new SearchTask(elementType, qualifier, elementName,
						parent, trueFlags, falseFlags, true, matchRule, scripts,
						container));
			}
			tasks.stream().map(ForkJoinTask::fork).forEach(t -> t.join()
					.stream().forEach(m -> searchMatchHandler.handle(m, true)));

		}
		if (searchForDecls) {
			for (String container : containers) {
				tasks.add(new SearchTask(elementType, qualifier, elementName,
						parent, trueFlags, falseFlags, false, matchRule,
						scripts, container));
			}
			tasks.stream().map(ForkJoinTask::fork).forEach(t -> t.join()
					.stream().forEach(m -> searchMatchHandler.handle(m, true)));
		}
	}

	private Query createQuery(final String elementName, final String qualifier,
			final String parent, final int trueFlags, final int falseFlags,
			final boolean searchForRefs, MatchRule matchRule,
			List<String> scripts) {
		BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
		if (!scripts.isEmpty()) {
			BooleanQuery.Builder scriptQueryBuilder = new BooleanQuery.Builder();
			for (String script : scripts) {
				scriptQueryBuilder.add(new TermQuery(new Term(F_PATH, script)),
						Occur.FILTER);
			}
			queryBuilder.add(scriptQueryBuilder.build(), Occur.FILTER);
		}
		if (elementName != null && !elementName.isEmpty()) {
			String elementNameLC = elementName.toLowerCase();
			Query nameQuery = null;
			Term nameCaseInsensitiveTerm = new Term(F_ELEMENT_NAME_LC,
					elementNameLC);
			if (matchRule == MatchRule.PREFIX) {
				nameQuery = new PrefixQuery(nameCaseInsensitiveTerm);
			} else if (matchRule == MatchRule.EXACT) {
				nameQuery = new TermQuery(nameCaseInsensitiveTerm);
			} else if (matchRule == MatchRule.CAMEL_CASE) {
				nameQuery = new PrefixQuery(new Term(F_CC_NAME, elementName));
			} else if (matchRule == MatchRule.PATTERN) {
				nameQuery = new WildcardQuery(nameCaseInsensitiveTerm);
			} else {
				throw new UnsupportedOperationException();
			}
			if (nameQuery != null) {
				queryBuilder.add(nameQuery, Occur.FILTER);
			}
		}
		if (qualifier != null && !qualifier.isEmpty()) {
			queryBuilder.add(new TermQuery(new Term(F_QUALIFIER, qualifier)),
					Occur.FILTER);
		}
		if (parent != null && !parent.isEmpty()) {
			queryBuilder.add(new TermQuery(new Term(F_PARENT, parent)),
					Occur.FILTER);
		}
		if (trueFlags != 0 || falseFlags != 0) {
			queryBuilder.add(new BitFlagsQuery(trueFlags, falseFlags),
					Occur.FILTER);
		}
		BooleanQuery query = queryBuilder.build();
		return query.clauses().isEmpty() ? null : query;
	}

	private class SearchTask extends RecursiveTask<List<SearchMatch>> {
		int elementType;
		String qualifier;
		String elementName;
		String parent;
		int trueFlags;
		int falseFlags;
		boolean searchForRefs;
		MatchRule matchRule;
		List<String> scripts;
		String container;

		private SearchTask(int elementType, String qualifier,
				String elementName, String parent, int trueFlags,
				final int falseFlags, boolean searchForRefs,
				MatchRule matchRule, List<String> scripts, String container) {
			this.elementType = elementType;
			this.qualifier = qualifier;
			this.elementName = elementName;
			this.parent = parent;
			this.trueFlags = trueFlags;
			this.falseFlags = falseFlags;
			this.searchForRefs = searchForRefs;
			this.matchRule = matchRule;
			this.scripts = scripts;
			this.container = container;
		}

		@Override
		protected List<SearchMatch> compute() {
			SearcherManager searcherManager = LuceneManager.INSTANCE
					.findIndexSearcher(container,
							searchForRefs ? IndexType.REFERENCES
									: IndexType.DECLARATIONS,
							elementType);
			if (searcherManager == null) {
				return Collections.emptyList();
			}
			IndexSearcher indexSearcher = null;
			try {
				indexSearcher = searcherManager.acquire();
				Query query = createQuery(elementName, qualifier, parent,
						trueFlags, falseFlags, searchForRefs, matchRule,
						scripts);
				ResultsCollector collector = new ResultsCollector(container,
						elementType);
				if (query != null) {
					indexSearcher.search(query, collector);
				} else {
					indexSearcher.search(new MatchAllDocsQuery(), collector);
				}
				return collector.getfResult();
			} catch (IOException e) {
				Logger.logException(e);
			} finally {
				if (indexSearcher != null) {
					try {
						searcherManager.release(indexSearcher);
					} catch (IOException e) {
						Logger.logException(e);
					}
				}
			}
			return Collections.emptyList();
		}
	}

}
