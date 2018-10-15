/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.debug.ui.handlers;

import org.eclipse.dltk.launching.AbstractScriptLaunchConfigurationDelegate;
import org.eclipse.dltk.ui.DLTKUILanguageManager;

public class NoDefaultDebuggingEngineStatusHandler
		extends AbstractOpenPreferencePageStatusHandler {

	@Override
	protected String getPreferencePageId(Object source) {
		if (source instanceof AbstractScriptLaunchConfigurationDelegate) {
			AbstractScriptLaunchConfigurationDelegate delegate = (AbstractScriptLaunchConfigurationDelegate) source;
			return DLTKUILanguageManager
					.getLanguageToolkit(delegate.getLanguageId())
					.getDebugPreferencePage();
		}

		return null;
	}

	@Override
	protected String getQuestion() {
		return HandlerMessages.NoDefaultDebuggingEngineQuestion;
	}

	@Override
	protected String getTitle() {
		return HandlerMessages.NoDefaultDebuggingEngineTitle;
	}
}
