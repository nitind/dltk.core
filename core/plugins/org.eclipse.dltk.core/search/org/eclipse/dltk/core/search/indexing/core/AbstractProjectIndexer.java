/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.core.search.indexing.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.index.Index;
import org.eclipse.dltk.core.search.indexing.IProjectIndexer;
import org.eclipse.dltk.core.search.indexing.IndexDocument;
import org.eclipse.dltk.core.search.indexing.IndexManager;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.search.processing.IJob;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractProjectIndexer implements IProjectIndexer,
		IProjectIndexer.Internal {

	private final IndexManager manager = ModelManager.getModelManager()
			.getIndexManager();

	@Override
	public void request(IJob job) {
		manager.request(job);
	}

	@Override
	public void requestIfNotWaiting(IJob job) {
		manager.requestIfNotWaiting(job);
	}

	@Override
	public IndexManager getIndexManager() {
		return manager;
	}

	@Override
	public void indexProject(IScriptProject project) {
		final ProjectRequest request = new ProjectRequest(this, project);
		requestIfNotWaiting(request);
	}

	@Override
	public void indexLibrary(IScriptProject project, IPath path) {
		try {
			final IProjectFragment fragment = project.findProjectFragment(path);
			if (fragment != null) {
				if (!path.segment(0).equals(IndexManager.SPECIAL_BUILTIN)) {
					final IndexRequest request;
					if (fragment.isArchive()) {
						request = new ArchiveProjectFragmentRequest(this,
								fragment,
								DLTKLanguageManager
										.getLanguageToolkit(fragment));
					} else {
						request = new ExternalProjectFragmentRequest(this,
								fragment,
								DLTKLanguageManager
										.getLanguageToolkit(fragment));
					}
					requestIfNotWaiting(request);
				}
			} else {
				DLTKCore.warn(NLS.bind(
						Messages.MixinIndexer_unknownProjectFragment, path));
			}
		} catch (Exception e) {
			DLTKCore.error(
					NLS.bind(Messages.MixinIndexer_indexLibraryError, path), e);
		}
	}

	@Override
	public void indexProjectFragment(IScriptProject project, IPath path) {
		IProjectFragment fragmentToIndex = null;
		try {
			IProjectFragment[] fragments = project.getProjectFragments();
			for (IProjectFragment fragment : fragments) {
				if (fragment.getPath().equals(path)) {
					fragmentToIndex = fragment;
					break;
				}
			}
		} catch (ModelException e) {
			DLTKCore.error("Failed to index fragment:" + path.toString(), e);
		}
		if (fragmentToIndex == null || !fragmentToIndex.isExternal()
				|| fragmentToIndex.isBuiltin()) {
			requestIfNotWaiting(new ProjectRequest(this, project));
			return;
		}
		final IndexRequest indexRequest;
		if (fragmentToIndex.isArchive()) {
			indexRequest = new ArchiveProjectFragmentRequest(this,
					fragmentToIndex,
					DLTKLanguageManager.getLanguageToolkit(project));
		} else {
			indexRequest = new ExternalProjectFragmentRequest(this,
					fragmentToIndex,
					DLTKLanguageManager.getLanguageToolkit(project));
		}
		requestIfNotWaiting(indexRequest);
	}

	@Override
	public void indexSourceModule(ISourceModule module,
			IDLTKLanguageToolkit toolkit) {
		request(new SourceModuleRequest(this, module, toolkit));
	}

	@Override
	public void reconciled(ISourceModule workingCopy,
			IDLTKLanguageToolkit toolkit) {
	}

	@Override
	public void removeProjectFragment(IScriptProject project, IPath sourceFolder) {
		// TODO optimize
		requestIfNotWaiting(new ProjectRequest(this, project));
	}

	@Override
	public void removeSourceModule(IScriptProject project, String path) {
		request(new SourceModuleRemoveRequest(this, project, path));
	}

	@Override
	public void removeProject(IPath projectPath) {
		requestIfNotWaiting(new RemoveIndexRequest(this, projectPath));
	}

	@Override
	public void removeLibrary(IScriptProject project, IPath path) {
		requestIfNotWaiting(new RemoveIndexRequest(this, path));
	}

	@Override
	public void startIndexing() {
		//
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean wantRefreshOnStart() {
		return true;
	}

	@Override
	public void indexSourceModule(Index index, IDLTKLanguageToolkit toolkit,
			ISourceModule module, IPath containerPath) {
		final IndexDocument document = new IndexDocument(toolkit, module,
				containerPath, index);
		index.remove(document.getContainerRelativePath());
		doIndexing(document);
	}

	public abstract void doIndexing(IndexDocument document);

	@Override
	public Index getProjectIndex(IScriptProject project) {
		return getIndexManager().getIndex(project.getProject().getFullPath(),
				true, true);
	}

	@Override
	public Index getProjectFragmentIndex(IProjectFragment fragment) {
		return getIndexManager().getIndex(fragment.getPath(), true, true);
	}

}
