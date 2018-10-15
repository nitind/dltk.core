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
package org.eclipse.dltk.launching.process;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IProcessFactory;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.RuntimeProcess;
import org.eclipse.dltk.launching.ScriptLaunchConfigurationConstants;

public class ScriptRuntimeProcessFactory implements IProcessFactory {

	public static final String PROCESS_FACTORY_ID = "org.eclipse.dltk.launching.scriptProcessFactory"; //$NON-NLS-1$

	private static boolean isDebugConsole(ILaunchConfiguration config) {
		try {
			return config.getAttribute(
					ScriptLaunchConfigurationConstants.ATTR_DEBUG_CONSOLE,
					true);
		} catch (CoreException e) {
			return true;
		}
	}

	private static boolean isInteractiveConsole(ILaunchConfiguration config) {
		try {
			return config.getAttribute(
					ScriptLaunchConfigurationConstants.ATTR_USE_INTERACTIVE_CONSOLE,
					false);
		} catch (CoreException e) {
			return false;
		}
	}

	/**
	 * @since 2.0
	 */
	public static boolean isSupported(ILaunch launch) {
		final ILaunchConfiguration config = launch.getLaunchConfiguration();
		if (ILaunchManager.DEBUG_MODE.equals(launch.getLaunchMode())) {
			if (config != null && isDebugConsole(config)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (config != null && isInteractiveConsole(config)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public IProcess newProcess(ILaunch launch, Process process, String label,
			Map<String, String> attributes) {
		if (isSupported(launch)) {
			return new ScriptRuntimeProcess(launch, process, label, attributes);
		} else {
			return new RuntimeProcess(launch, process, label, attributes);
		}
	}
}
