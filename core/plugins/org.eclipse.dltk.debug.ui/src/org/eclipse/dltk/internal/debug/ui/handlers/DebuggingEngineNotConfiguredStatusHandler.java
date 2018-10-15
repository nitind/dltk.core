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

import org.eclipse.dltk.launching.DebuggingEngineRunner;
import org.eclipse.dltk.launching.debug.IDebuggingEngine;

/**
 * Debugging engine configuration problem that prevents debugging engine from
 * starting
 *
 * @author kds
 *
 */
public class DebuggingEngineNotConfiguredStatusHandler
		extends AbstractOpenPreferencePageStatusHandler {

	@Override
	protected String getPreferencePageId(Object source) {
		if (source instanceof DebuggingEngineRunner) {
			final DebuggingEngineRunner runner = (DebuggingEngineRunner) source;
			final IDebuggingEngine engine = runner.getDebuggingEngine();
			if (engine != null) {
				return engine.getPreferencePageId();
			}
		}

		return null;
	}

	@Override
	public String getTitle() {
		return HandlerMessages.DebuggingEngineNotConfiguredTitle;
	}

	@Override
	protected String getQuestion() {
		return HandlerMessages.DebuggingEngineNotConfiguredQuestion;
	}
}
