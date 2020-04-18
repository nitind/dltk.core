/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.SearcherManager;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
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

	private final class ShutdownListener implements IShutdownListener {

		@Override
		public void shutdown() {
			// Shutdown manager
			LuceneManager.INSTANCE.shutdown();
		}

	}

	private final class IndexerThreadListener implements IIndexThreadListener {

		@Override
		public void aboutToBeIdle() {
			commit();
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
	private final Map<String, String> fContainerMappings;
	private final Map<String, IndexContainer> fIndexContainers;

	private void commit() {
		try {
			for (IndexContainer indexContainer : getDirtyContainers()) {
				indexContainer.commit();
				indexContainer.refresh();
			}

		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private LuceneManager() {
		fIndexProperties = new Properties();
		fContainerMappings = new HashMap<>();
		fIndexContainers = new HashMap<>();
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
		if (fContainerMappings.get(container) != null) {
			getIndexContainer(container).delete(sourceModule);
		}
	}

	private List<IndexContainer> getDirtyContainers() {
		List<IndexContainer> uncommittedContainers = new ArrayList<>();
		synchronized (fIndexContainers) {
			for (IndexContainer indexContainer : fIndexContainers.values()) {
				if (indexContainer.hasChanges()) {
					uncommittedContainers.add(indexContainer);
				}
			}
		}

		return uncommittedContainers;
	}

	private IndexContainer getIndexContainer(String container) {
		String containerId = fContainerMappings.get(container);
		if (containerId == null) {
			synchronized (fContainerMappings) {
				containerId = fContainerMappings.get(container);
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
			}
		}
		return fIndexContainers.get(containerId);
	}

	private void deleteIndexContainer(String container, boolean wait) {
		synchronized (fContainerMappings) {
			String containerId = fContainerMappings.remove(container);
			if (containerId != null) {
				IndexContainer containerEntry = fIndexContainers
						.remove(containerId);
				saveMappings();
				containerEntry.delete(wait);
			}
		}
	}

	private void startup() {
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
	}

	private synchronized void shutdown() {
		// Close all searchers & writers in all container entries
		for (IndexContainer entry : fIndexContainers.values()) {
			entry.close();
		}
		cleanup();
	}

	private void registerIndexContainers() {
		synchronized (fContainerMappings) {

			for (String containerId : fContainerMappings.values()) {
				fIndexContainers.put(containerId,
						new IndexContainer(fIndexRoot, containerId));
			}
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
		synchronized (fContainerMappings) {
			try (FileInputStream fis = new FileInputStream(file)) {
				Properties p = new Properties();
				p.load(fis);
				for (Entry<Object, Object> entry : p.entrySet()) {
					fContainerMappings.put((String) entry.getKey(),
							(String) entry.getValue());
				}
			} catch (IOException e) {
				Logger.logException(e);
			}
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
		synchronized (fContainerMappings) {
			try (FileOutputStream fos = new FileOutputStream(file)) {
				Properties p = new Properties();
				for (Entry<String, String> entry : fContainerMappings
						.entrySet()) {
					p.setProperty(entry.getKey(), entry.getValue());
				}

				p.store(fos, ""); //$NON-NLS-1$
			} catch (IOException e) {
				Logger.logException(e);
			}
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
		synchronized (fContainerMappings) {
			for (String mappedContainer : fContainerMappings.values()) {
				if (!containers.contains(mappedContainer)) {
					toRemove.add(mappedContainer);
				}
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
