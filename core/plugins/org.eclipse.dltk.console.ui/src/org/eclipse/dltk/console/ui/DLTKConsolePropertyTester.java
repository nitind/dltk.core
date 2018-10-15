/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.console.ui;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.launching.ScriptLaunchConfigurationConstants;
import org.eclipse.ui.console.TextConsole;

public class DLTKConsolePropertyTester extends PropertyTester {

	private static final String IS_DLTK_CONSOLE_PROPERTY = "isDLTKConsole"; //$NON-NLS-1$

	public DLTKConsolePropertyTester() {
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (IS_DLTK_CONSOLE_PROPERTY.equals(property)) {
			if (receiver instanceof ScriptConsole) {
				return true;
			} else if (receiver instanceof TextConsole) {
				final TextConsole textConsole = (TextConsole) receiver;
				final Object process = textConsole
						.getAttribute(IDebugUIConstants.ATTR_CONSOLE_PROCESS);
				if (process != null && process instanceof IProcess) {
					final String nature = getProcessNature((IProcess) process);
					return nature != null && DLTKLanguageManager
							.getLanguageToolkit(nature) != null;
				}
			}
		}
		return false;
	}

	private static String getProcessNature(IProcess process) {
		final ILaunch launch = process.getLaunch();
		final ILaunchConfiguration configuration = launch
				.getLaunchConfiguration();
		if (configuration != null) {
			try {
				return configuration.getAttribute(
						ScriptLaunchConfigurationConstants.ATTR_SCRIPT_NATURE,
						(String) null);
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
