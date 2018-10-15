/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.task.ITaskReporter;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IFileHandle;

public class ExternalModuleBuildContext extends AbstractBuildContext implements
		IProblemReporter, ITaskReporter, IAdaptable {

	/**
	 * @param module
	 */
	protected ExternalModuleBuildContext(ISourceModule module, int buildType) {
		super(module, buildType);
	}

	/*
	 * @see org.eclipse.dltk.core.builder.IBuildContext#getFileHandle()
	 */
	@Override
	public IFileHandle getFileHandle() {
		// TODO test!!
		return EnvironmentPathUtils.getFile(module.getPath());
	}

	@Override
	public IProblemReporter getProblemReporter() {
		return this;
	}

	@Override
	public ITaskReporter getTaskReporter() {
		return this;
	}

	@Override
	public void reportTask(String message, int lineNumber, int priority,
			int charStart, int charEnd) {
		// NOP
	}

	@Override
	public void reportProblem(IProblem problem) {
		// NOP
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public void recordDependency(IPath dependency, int flags) {
		// NOP
	}
}
