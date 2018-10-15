/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.debug.ui.display;

import org.eclipse.dltk.console.IScriptInterpreter;
import org.eclipse.dltk.console.ui.ScriptConsole;
import org.eclipse.dltk.console.ui.internal.ScriptConsolePage;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.console.IConsoleView;

public class DebugConsole extends ScriptConsole implements IEvaluateConsole {

	/**
	 * @param consoleName
	 * @param consoleType
	 */
	public DebugConsole(String consoleName, String consoleType,
			IScriptInterpreter interpreter) {
		super(consoleName, consoleType);
		setInterpreter(interpreter);
		setContentAssistProcessor(new DebugConsoleContentAssistProcessor());
	}

	@Override
	protected ScriptConsolePage createPage(IConsoleView view,
			SourceViewerConfiguration cfg) {
		return new DebugConsolePage(this, view, cfg);
	}

}
