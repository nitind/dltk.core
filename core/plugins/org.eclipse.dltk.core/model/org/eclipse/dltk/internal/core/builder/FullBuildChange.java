/*******************************************************************************
 * Copyright (c) 2010, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.internal.core.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.builder.IBuildChange;
import org.eclipse.dltk.core.builder.IProjectChange;
import org.eclipse.dltk.core.builder.IRenameChange;
import org.eclipse.dltk.core.builder.IScriptBuilder;

public class FullBuildChange extends AbstractBuildChange
		implements IBuildChange, IResourceVisitor {

	public FullBuildChange(IProject project, IProgressMonitor monitor) {
		super(project, monitor);
	}

	@Override
	public IResourceDelta getResourceDelta() {
		return null;
	}

	@Override
	public List<IPath> getDeletes(int options) {
		validateFlags(options, NO_RENAMES);
		return Collections.emptyList();
	}

	@Override
	public List<IRenameChange> getRenames() {
		return Collections.emptyList();
	}

	@Override
	public List<IFile> getResources(int options) throws CoreException {
		validateFlags(options, ALL | NO_RENAMES | ADDED | CHANGED);
		if (checkFlag(options, ALL)) {
			loadProjectResources();
			return Collections.unmodifiableList(projectResources);
		} else {
			loadSourceModules();
			return Collections.unmodifiableList(realResources);
		}
	}

	private void loadProjectResources() throws CoreException {
		if (projectResources == null) {
			projectResources = new ArrayList<>();
			project.accept(this);
		}
	}

	private List<ISourceModule> projectModules = null;
	private List<IFile> realResources = null;

	@Override
	public List<ISourceModule> getSourceModules(int options)
			throws CoreException {
		validateFlags(options, ADDED | CHANGED);
		loadSourceModules();
		return Collections.unmodifiableList(projectModules);
	}

	private void loadSourceModules() throws CoreException {
		if (projectModules == null) {
			loadProjectResources();
			projectModules = new ArrayList<>();
			realResources = new ArrayList<>();
			locateSourceModules(projectResources, projectModules,
					realResources);
		}
	}

	@Override
	public int getBuildType() {
		return IScriptBuilder.FULL_BUILD;
	}

	@Override
	public void setBuildType(int buildType) {
		if (buildType != IScriptBuilder.FULL_BUILD) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean isDependencyBuild() {
		return false;
	}

	@Override
	public IProjectChange[] getRequiredProjectChanges() {
		return NO_PROJECT_CHANGES;
	}

	private List<IFile> projectResources = null;

	@Override
	public boolean visit(IResource resource) throws CoreException {
		checkCanceled();
		if (resource.getType() == IResource.FOLDER) {
			monitor.subTask(Messages.ScriptBuilder_scanningProjectFolder
					+ resource.getProjectRelativePath().toString());
		}
		if (resource.getType() == IResource.FILE) {
			projectResources.add((IFile) resource);
			return false;
		}
		return true;
	}

	@Override
	public boolean addChangedResource(IFile file) {
		return false;
	}

	@Override
	public boolean addChangedResources(Collection<IFile> files)
			throws CoreException {
		return false;
	}

	private List<IPath> externalPaths = null;
	private Collection<IProjectFragment> externalFragments = null;

	private void loadExternalPaths() throws CoreException {
		if (externalPaths == null) {
			externalPaths = new ArrayList<>();
			externalFragments = new ArrayList<>();
			final IProjectFragment[] allFragments = getScriptProject()
					.getAllProjectFragments();
			for (int i = 0; i < allFragments.length; i++) {
				final IProjectFragment fragment = allFragments[i];
				if (fragment.isExternal()) {
					final IPath path = fragment.getPath();
					externalPaths.add(path);
					externalFragments.add(fragment);
				}
			}
		}
	}

	@Override
	public List<IPath> getExternalPaths(int options) throws CoreException {
		validateFlags(options, BEFORE);
		if (options == BEFORE) {
			return Collections.emptyList();
		} else {
			loadExternalPaths();
			return Collections.unmodifiableList(externalPaths);
		}
	}

	private List<ISourceModule> externalModules = null;

	@Override
	public List<ISourceModule> getExternalModules(int options)
			throws CoreException {
		validateFlags(options, DEFAULT);
		if (externalModules == null) {
			loadExternalPaths();
			final ExternalModuleCollector moduleCollector = new ExternalModuleCollector(
					monitor);
			for (IProjectFragment fragment : externalFragments) {
				fragment.accept(moduleCollector);
			}
			externalModules = unmodifiableList(moduleCollector.elements);
		}
		return externalModules;
	}

}
