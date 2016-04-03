/*******************************************************************************
 * Copyright (c) 2011, 2016 NumberFour AG and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.internal.core.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.builder.IBuildChange;
import org.eclipse.dltk.core.builder.IProjectChange;
import org.eclipse.dltk.core.builder.IRenameChange;
import org.eclipse.dltk.core.builder.IScriptBuilder;

public class BuildChange extends AbstractBuildChange implements IBuildChange {

	private final IResourceDelta resourceDelta;
	private final List<IFile> files;
	private List<ISourceModule> modules;
	private List<IFile> realResources;

	public BuildChange(IProject project, IResourceDelta resourceDelta,
			List<IFile> files, IProgressMonitor monitor) {
		super(project, monitor);
		this.resourceDelta = resourceDelta;
		this.files = files;
	}

	@Override
	public IResourceDelta getResourceDelta() {
		return resourceDelta;
	}

	@Override
	public List<IPath> getDeletes(int options) throws CoreException {
		return Collections.emptyList();
	}

	@Override
	public List<IRenameChange> getRenames() throws CoreException {
		return Collections.emptyList();
	}

	@Override
	public List<IFile> getResources(int options) throws CoreException {
		options = validateFlags(options, ALL | NO_RENAMES | ADDED | CHANGED);
		if ((options & (ADDED | CHANGED | NO_RENAMES)) == (CHANGED | NO_RENAMES)) {
			throw new IllegalArgumentException();
		}
		if (checkFlag(options, ALL)) {
			return Collections.unmodifiableList(files);
		} else {
			loadSourceModules();
			return Collections.unmodifiableList(realResources);
		}
	}

	@Override
	public List<ISourceModule> getSourceModules(int options)
			throws CoreException {
		validateFlags(options, ADDED | CHANGED);
		loadSourceModules();
		return Collections.unmodifiableList(modules);
	}

	private void loadSourceModules() {
		if (modules == null) {
			modules = new ArrayList<ISourceModule>();
			realResources = new ArrayList<IFile>();
			locateSourceModules(files, modules, realResources);
		}
	}

	private int buildType = IScriptBuilder.INCREMENTAL_BUILD;

	@Override
	public int getBuildType() {
		return buildType;
	}

	@Override
	public void setBuildType(int buildType) {
		this.buildType = buildType;
	}

	@Override
	public boolean isDependencyBuild() {
		return false;
	}

	@Override
	public boolean addChangedResource(IFile file) throws CoreException {
		return false;
	}

	@Override
	public boolean addChangedResources(Collection<IFile> files)
			throws CoreException {
		return false;
	}

	@Override
	public List<IPath> getExternalPaths(int options) throws CoreException {
		return Collections.emptyList();
	}

	@Override
	public List<ISourceModule> getExternalModules(int options)
			throws CoreException {
		return Collections.emptyList();
	}

	@Override
	public IProjectChange[] getRequiredProjectChanges() {
		return NO_PROJECT_CHANGES;
	}

}
