/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.console.ui;

import org.eclipse.ui.console.IConsoleFactory;

public abstract class ScriptConsoleFactoryBase implements IConsoleFactory {
	public ScriptConsoleFactoryBase() {
	}

	/**
	 * @since 2.0
	 */
	protected void registerAndOpenConsole(IScriptConsole console) {
		ScriptConsoleManager manager = ScriptConsoleManager.getInstance();
		manager.add(console);
		manager.showConsole(console);
	}

	@Override
	public void openConsole() {
		IScriptConsole console = createConsoleInstance();
		if (console != null) {
			registerAndOpenConsole(console);
		}
	}

	/**
	 * @since 2.0
	 */
	protected abstract IScriptConsole createConsoleInstance();
}
