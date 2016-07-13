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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.SearcherManager;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IShutdownListener;
import org.eclipse.dltk.core.index.lucene.LucenePlugin;
import org.eclipse.dltk.core.search.indexing.IIndexThreadListener;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.search.DLTKWorkspaceScope;

/**
 * <p>
 * Apache Lucene indexes manager responsible for managing indexes model.
 * </p>
 * <p>
 * Indexes are stored in hierarchical directory structure as follows:
 * <code><pre>
 * index_root
 *   |_container_id
 *     |_declarations
 *       |_model_element_type_id (index data)
 *       ...
 *     |_references
 *       |_model_element_type_id (index data)
 *       ...
 *     |_timestamps (index data)
 * </pre></code>
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public enum LuceneManager {

	/**
	 * Manager Instance.
	 */
	INSTANCE;

	private final class Committer extends Job {

		private final static int DELAY = 5000;
		private boolean fClosed = false;

		public Committer() {
			super(""); //$NON-NLS-1$
			setUser(false);
			setSystem(true);
		}

		@Override
		public IStatus run(IProgressMonitor monitor) {
			// Get containers with uncommitted changes only
			List<IndexContainer> dirtyContainers = getDirtyContainers();
			if (dirtyContainers.isEmpty()) {
				return Status.CANCEL_STATUS;
			}
			int containersNumber = dirtyContainers.size();
			SubMonitor subMonitor = SubMonitor.convert(monitor,
					containersNumber);
			try {
				for (IndexContainer indexContainer : dirtyContainers) {
					if (!monitor.isCanceled()) {
						// Commit index data without merging deletions (better performance)
						indexContainer.commit(subMonitor.newChild(1), false);
					}
				}
				monitor.done();
			} catch (Exception e) {
				Logger.logException(e);
			}
			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object family) {
			return family == LucenePlugin.LUCENE_JOB_FAMILY;
		}

		synchronized void commit() {
			if (fClosed) {
				return;
			}
			int currentState = getState();
			if (currentState == NONE) {
				schedule(DELAY);
			} else if (currentState == SLEEPING) {
				wakeUp(DELAY);
			} else if (currentState == WAITING) {
				sleep();
				wakeUp(DELAY);
			} else {
				cancel();
				schedule(DELAY);
			}
		}

		synchronized void close() {
			if (!fClosed) {
				cancel();
				fClosed = true;
			}
		}

	}

	private final class ShutdownListener implements IShutdownListener {

		@Override
		public void shutdown() {
			// Close background committer if it is not already closed
			fCommitter.close();
			// Shutdown manager
			LuceneManager.INSTANCE.shutdown();
		}

	}

	private final class SaveParticipant implements ISaveParticipant {

		@Override
		public void saving(ISaveContext context) throws CoreException {
			if (context.getKind() != ISaveContext.FULL_SAVE)
				return;
			// Close background committer
			fCommitter.close();
			// Commit all indexes data, merge deletions
			for (IndexContainer indexContainer : fIndexContainers.values()) {
				indexContainer.commit(new NullProgressMonitor(), true);
			}
		}

		@Override
		public void doneSaving(ISaveContext context) {}

		@Override
		public void prepareToSave(ISaveContext context) throws CoreException {}

		@Override
		public void rollback(ISaveContext context) {}

	}

	private final class IndexerThreadListener implements IIndexThreadListener {

		@Override
		public void aboutToBeIdle() {
			fCommitter.commit();
		}

		@Override
		public void aboutToBeRun(long idlingTime) {
		}

	}

	private static final String INDEX_DIR = "index"; //$NON-NLS-1$
	private static final String PROPERTIES_FILE = ".properties"; //$NON-NLS-1$
	private static final String MAPPINGS_FILE = ".mappings"; //$NON-NLS-1$

	private final String fIndexRoot;
	private final Properties fIndexProperties;
	private final Properties fContainerMappings;
	private final Map<String, IndexContainer> fIndexContainers;
	private final Committer fCommitter;

	private LuceneManager() {
		fIndexProperties = new Properties();
		fContainerMappings = new Properties();
		fIndexContainers = new ConcurrentHashMap<>();
		fCommitter = new Committer();
		fIndexRoot = Platform
				.getStateLocation(LucenePlugin.getDefault().getBundle())
				.append(INDEX_DIR).toOSString();
		File indexRootDirectory = new File(fIndexRoot);
		if (!indexRootDirectory.exists()) {
			indexRootDirectory.mkdirs();
		}
		startup();
	}

	/**
	 * Finds and returns index writer for given container, data type and model
	 * element.
	 * 
	 * @param container
	 * @param dataType
	 * @param elementType
	 * @return index writer
	 */
	public final IndexWriter findIndexWriter(String container,
			IndexType dataType, int elementType) {
		return getIndexContainer(container).getIndexWriter(dataType,
				elementType);
	}

	/**
	 * Finds and returns index searcher for given container, data type and model
	 * element.
	 * 
	 * @param container
	 * @param dataType
	 * @param elementType
	 * @return index searcher
	 */
	public final SearcherManager findIndexSearcher(String container,
			IndexType dataType, int elementType) {
		return getIndexContainer(container).getIndexSearcher(dataType,
				elementType);
	}

	/**
	 * Finds and returns time stamps index writer for given container.
	 * 
	 * @param container
	 * @return time stamps index writer
	 */
	public final IndexWriter findTimestampsWriter(String container) {
		return getIndexContainer(container).getTimestampsWriter();
	}

	/**
	 * Finds and returns time stamps index searcher for given container.
	 * 
	 * @param container
	 * @return time stamps index searcher
	 */
	public final SearcherManager findTimestampsSearcher(String container) {
		return getIndexContainer(container).getTimestampsSearcher();
	}

	/**
	 * Deletes related container index entry (container entry is removed
	 * completely).
	 * 
	 * @param container
	 */
	public final void delete(final String container) {
		deleteIndexContainer(container, false);
	}

	/**
	 * Deletes given container's source module index data.
	 * 
	 * @param container
	 * @param sourceModule
	 */
	public final void delete(String container, String sourceModule) {
		if (fContainerMappings.getProperty(container) != null) {
			getIndexContainer(container).delete(sourceModule);
		}
	}

	private synchronized List<IndexContainer> getDirtyContainers() {
		List<IndexContainer> uncommittedContainers = new ArrayList<>();
		for (IndexContainer indexContainer : fIndexContainers.values()) {
			if (indexContainer.hasChanges()) {
				uncommittedContainers.add(indexContainer);
			}
		}
		return uncommittedContainers;
	}

	private synchronized IndexContainer getIndexContainer(String container) {
		String containerId = fContainerMappings.getProperty(container);
		if (containerId == null) {
			do {
				// Just to be sure that ID does not already exist
				containerId = UUID.randomUUID().toString();
			} while (fContainerMappings.containsValue(containerId));
			fContainerMappings.put(container, containerId);
			fIndexContainers.put(containerId,
					new IndexContainer(fIndexRoot, containerId));
			// Persist mapping
			saveMappings();
		}
		return fIndexContainers.get(containerId);
	}

	private synchronized void deleteIndexContainer(String container,
			boolean wait) {
		String containerId = (String) fContainerMappings.remove(container);
		if (containerId != null) {
			IndexContainer containerEntry = fIndexContainers
					.remove(containerId);
			saveMappings();
			containerEntry.delete(wait);
		}
	}

	private synchronized void startup() {
		loadProperties();
		boolean purgeIndexRoot = false;
		boolean resetProperties = false;
		String modelVersion = fIndexProperties
				.getProperty(IndexProperties.KEY_MODEL_VERSION);
		String luceneVersion = fIndexProperties
				.getProperty(IndexProperties.KEY_LUCENE_VERSION);
		if (!IndexProperties.MODEL_VERSION.equals(modelVersion)
				|| !IndexProperties.LUCENE_VERSION.equals(luceneVersion)) {
			purgeIndexRoot = true;
			resetProperties = true;
		}
		if (purgeIndexRoot) {
			purge();
		}
		if (resetProperties) {
			resetProperties();
			saveProperties();
		}
		loadMappings();
		registerIndexContainers();
		ModelManager.getModelManager().getIndexManager()
				.addIndexerThreadListener(new IndexerThreadListener());
		ModelManager.getModelManager().getIndexManager()
				.addShutdownListener(new ShutdownListener());
		try {
			ResourcesPlugin.getWorkspace().addSaveParticipant(LucenePlugin.ID,
					new SaveParticipant());
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	private synchronized void shutdown() {
		// Close all searchers & writers in all container entries
		for (IndexContainer entry : fIndexContainers.values()) {
			entry.close();
		}
		cleanup();
	}

	private void registerIndexContainers() {
		for (String container : fContainerMappings.stringPropertyNames()) {
			String containerId = fContainerMappings.getProperty(container);
			fIndexContainers.put(containerId,
					new IndexContainer(fIndexRoot, containerId));
		}
	}

	private void loadProperties() {
		File file = Paths.get(fIndexRoot, PROPERTIES_FILE).toFile();
		if (!file.exists()) {
			return;
		}
		try (FileInputStream fis = new FileInputStream(file)) {
			fIndexProperties.load(fis);
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	private void loadMappings() {
		File file = Paths.get(fIndexRoot, MAPPINGS_FILE).toFile();
		if (!file.exists()) {
			return;
		}
		try (FileInputStream fis = new FileInputStream(file)) {
			fContainerMappings.load(fis);
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	private void saveProperties() {
		File file = Paths.get(fIndexRoot, PROPERTIES_FILE).toFile();
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fIndexProperties.store(fos, ""); //$NON-NLS-1$
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	private void saveMappings() {
		File file = Paths.get(fIndexRoot, MAPPINGS_FILE).toFile();
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fContainerMappings.store(fos, ""); //$NON-NLS-1$
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	private void resetProperties() {
		fIndexProperties.clear();
		fIndexProperties.put(IndexProperties.KEY_MODEL_VERSION,
				IndexProperties.MODEL_VERSION);
		fIndexProperties.put(IndexProperties.KEY_LUCENE_VERSION,
				IndexProperties.LUCENE_VERSION);
	}

	private void cleanup() {
		List<String> containers = new ArrayList<>();
		for (IDLTKLanguageToolkit toolkit : DLTKLanguageManager
				.getLanguageToolkits()) {
			DLTKWorkspaceScope scope = ModelManager.getModelManager()
					.getWorkspaceScope(toolkit);
			for (IPath path : scope.enclosingProjectsAndZips()) {
				containers.add(path.toString());
			}
		}
		/*
		 * Some projects/libraries could be deleted outside the workspace, clean
		 * up the related mappings that might left.
		 */
		Set<String> toRemove = new HashSet<>();
		for (String mappedContainer : fContainerMappings
				.stringPropertyNames()) {
			if (!containers.contains(mappedContainer)) {
				toRemove.add(mappedContainer);
			}
		}
		if (!toRemove.isEmpty()) {
			for (String container : toRemove) {
				deleteIndexContainer(container, true);
			}
		}
		/*
		 * Some projects/libraries could be deleted outside the workspace,
		 * delete up the related index directories that might left.
		 */
		List<Path> toDelete = new ArrayList<>();
		Path indexRoot = Paths.get(fIndexRoot);
		for (File containerDir : indexRoot.toFile().listFiles()) {
			if (containerDir.isDirectory() && !fContainerMappings
					.containsValue(containerDir.getName())) {
				toDelete.add(Paths.get(containerDir.getAbsolutePath()));
			}
		}
		if (!toDelete.isEmpty()) {
			for (Path containerDir : toDelete) {
				try {
					Utils.delete(containerDir);
				} catch (IOException e) {
					Logger.logException(e);
				}
			}
		}
	}

	private void purge() {
		Path indexRoot = Paths.get(fIndexRoot);
		try {
			Utils.delete(indexRoot);
		} catch (IOException e) {
			Logger.logException(e);
		}
		indexRoot.toFile().mkdir();
	}

}
