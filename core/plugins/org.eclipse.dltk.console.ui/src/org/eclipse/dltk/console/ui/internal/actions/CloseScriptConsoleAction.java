/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.console.ui.internal.actions;

import org.eclipse.dltk.console.ui.ScriptConsole;
import org.eclipse.dltk.console.ui.ScriptConsoleManager;
import org.eclipse.dltk.console.ui.ScriptConsoleUIConstants;
import org.eclipse.dltk.console.ui.ScriptConsoleUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class CloseScriptConsoleAction extends Action {

	private ScriptConsole console;

	public CloseScriptConsoleAction(ScriptConsole console, String text,
			String tooltip) {
		this.console = console;

		setText(text);
		setToolTipText(tooltip);
	}

	@Override
	public void run() {
		ScriptConsoleManager.getInstance().close(console);
	}

	public void update() {
		setEnabled(true);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ScriptConsoleUIPlugin.getDefault()
				.getImageDescriptor(ScriptConsoleUIConstants.TERMINATE_ICON);
	}
}
