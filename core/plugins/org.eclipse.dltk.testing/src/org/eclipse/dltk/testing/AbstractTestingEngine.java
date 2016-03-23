/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.testing;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.dltk.core.DLTKContributedExtension;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.testing.launcher.NullTestRunnerUI;
import org.eclipse.dltk.launching.InterpreterConfig;

public abstract class AbstractTestingEngine extends DLTKContributedExtension
		implements ITestingEngine {

	@Override
	public void configureLaunch(InterpreterConfig config,
			ILaunchConfiguration configuration, ILaunch launch)
			throws CoreException {
		// empty
	}

	@Override
	public String getMainScriptPath(ILaunchConfiguration configuration,
			IEnvironment scriptEnvironment) throws CoreException {
		return null;
	}

	@Override
	public IStatus validateContainer(IModelElement element) {
		return Status.CANCEL_STATUS;
	}

	@Override
	public IStatus validateSourceModule(ISourceModule module) {
		return Status.CANCEL_STATUS;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public ITestRunnerUI getTestRunnerUI(IScriptProject project,
			ILaunchConfiguration configuration) {
		return NullTestRunnerUI.getInstance();
	}

}
