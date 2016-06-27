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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.SearcherManager;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IShutdownListener;
import org.eclipse.dltk.core.index.lucene.LucenePlugin;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.search.DLTKWorkspaceScope;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;

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

	private final class ShutdownListener
			implements IShutdownListener, IWorkbenchListener, ISaveParticipant {

		private final class Committer extends Job {

			private List<IndexContainer> fContainers;

			public Committer() {
				super(Messages.LuceneManager_Committer_saving_indexes);
				setUser(false);
				setSystem(false);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				int containersNumber = fContainers.size();
				monitor.beginTask("", containersNumber); //$NON-NLS-1$
				SubMonitor subMonitor = SubMonitor.convert(monitor,
						containersNumber);
				int counter = 0;
				try {
					for (IndexContainer indexContainer : fContainers) {
						if (!monitor.isCanceled()) {
							counter++;
							monitor.subTask(MessageFormat.format(
									Messages.LuceneManager_Committer_flushing_index_data,
									counter, containersNumber));
							// Commit index data to file system storage
							indexContainer.commit(subMonitor.newChild(1));
						}
					}
					monitor.done();
				} catch (Exception e) {
					Logger.logException(e);
				} finally {
					// Whatever happens semaphore must be released.
					fSemaphore.release();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return family == LucenePlugin.LUCENE_JOB_FAMILY;
			}

			void committ() {
				fContainers = getUncommittedContainers();
				schedule();
			}

		}

		private final Committer fCommitter = new Committer();
		private final Semaphore fSemaphore = new Semaphore(0);

		@Override
		public boolean preShutdown(IWorkbench workbench, boolean forced) {
			// Check if there is anything to commit first
			if (getUncommittedContainers().isEmpty())
				return true;
			/*
			 * Trigger this hidden job that will use workspace root as
			 * scheduling rule just to show the committer job progress in the
			 * details pane of "Saving Workspace" dialog.
			 */
			Job job = new Job("") { //$NON-NLS-1$
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						fSemaphore.acquire();
					} catch (InterruptedException e) {
						// ignore
					}
					return Status.OK_STATUS;
				}
			};
			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.setSystem(true);
			job.setUser(false);
			job.schedule();
			// Trigger committer job
			fCommitter.committ();
			return true;
		}

		@Override
		public void saving(ISaveContext context) throws CoreException {
			joinCommitter();
		}

		@Override
		public void shutdown() {
			joinCommitter();
			// Shutdown manager and close all the writers and searchers
			LuceneManager.INSTANCE.shutdown();
		}

		@Override
		public void postShutdown(IWorkbench workbench) {
			// ignore
		}

		@Override
		public void doneSaving(ISaveContext context) {
			// ignore
		}

		@Override
		public void prepareToSave(ISaveContext context) throws CoreException {
			// ignore
		}

		@Override
		public void rollback(ISaveContext context) {
			// ignore
		}

		private void joinCommitter() {
			try {
				fCommitter.join();
			} catch (InterruptedException e) {
				// ignore
			}
		}

	}

	private static final String INDEX_DIR = "index"; //$NON-NLS-1$
	private static final String PROPERTIES_FILE = ".properties"; //$NON-NLS-1$
	private static final String MAPPINGS_FILE = ".mappings"; //$NON-NLS-1$

	private final String fIndexRoot;
	private final Properties fIndexProperties;
	private final Properties fContainerMappings;
	private final Map<String, IndexContainer> fIndexContainers;

	private LuceneManager() {
		fIndexProperties = new Properties();
		fContainerMappings = new Properties();
		fIndexContainers = new ConcurrentHashMap<>();
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

	synchronized String getContainerPath(String containerId) {
		for (Object key : fContainerMappings.keySet()) {
			String container = (String) key;
			if (containerId.equals(fContainerMappings.getProperty(container))) {
				return container;
			}
		}
		return null;
	}

	synchronized List<IndexContainer> getUncommittedContainers() {
		List<IndexContainer> uncommittedContainers = new ArrayList<>();
		for (IndexContainer indexContainer : fIndexContainers.values()) {
			if (indexContainer.hasUncommittedChanges()) {
				uncommittedContainers.add(indexContainer);
			}
		}
		return uncommittedContainers;
	}

	synchronized IndexContainer getIndexContainer(String container) {
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

	synchronized void deleteIndexContainer(String container, boolean wait) {
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
		ShutdownListener shutdownListener = new ShutdownListener();
		ModelManager.getModelManager().getIndexManager()
				.addShutdownListener(shutdownListener);
		PlatformUI.getWorkbench().addWorkbenchListener(shutdownListener);
		try {
			ResourcesPlugin.getWorkspace().addSaveParticipant(LucenePlugin.ID,
					shutdownListener);
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
