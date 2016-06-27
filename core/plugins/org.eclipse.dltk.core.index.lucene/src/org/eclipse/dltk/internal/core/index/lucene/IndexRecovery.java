/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.eclipse.dltk.internal.core.search.ProjectIndexerManager;

/**
 * Lucene index container recovery class.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public final class IndexRecovery {

	private static final String RECOVERY_REASON = "Index writer could not be created, index data might be corrupted."; //$NON-NLS-1$
	private static final String RECOVERY_STARTED = "Recovering index storage: {0}"; //$NON-NLS-1$
	private static final String RECOVERY_FAILED = "Failed to recover index storage: {0}"; //$NON-NLS-1$

	private IndexRecovery() {
		// No instance
	}

	/**
	 * <p>
	 * Tries to recover possibly corrupted Lucene index. Recovery process will
	 * try do do the following:
	 * </p>
	 * <ul>
	 * <li>Remove problematic index directory.</li>
	 * <li>Clean up time stamps data in corresponding index conatiner.</li>
	 * <li>Trigger index rebuilding to fill empty index directory.</li>
	 * </ul>
	 * 
	 * @param indexContainer
	 * @param indexPath
	 * @param exception
	 */
	static void tryRecover(IndexContainer indexContainer, Path indexPath,
			IOException exception) {
		Logger.logException(RECOVERY_REASON, exception);
		Logger.log(Logger.INFO,
				MessageFormat.format(RECOVERY_STARTED, indexPath.toString()));
		try {
			// Try to delete possibly corrupted index container
			Utils.delete(indexPath);
			// Clean time stamps to purge index state
			indexContainer.getTimestampsWriter()
					.deleteDocuments(new MatchAllDocsQuery());
		} catch (IOException e) {
			Logger.logException(
					MessageFormat.format(RECOVERY_FAILED, indexPath.toString()),
					e);
			return;
		}
		// Re-triggering indexing will fill purged container indexes.
		ProjectIndexerManager.startIndexing();
	}

}
