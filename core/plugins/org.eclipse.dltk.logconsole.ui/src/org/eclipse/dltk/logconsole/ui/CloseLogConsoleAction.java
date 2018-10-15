/*******************************************************************************
 * Copyright (c) 2010, 2017 xored software, Inc.
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
package org.eclipse.dltk.logconsole.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;

public class CloseLogConsoleAction extends Action {

	private final IConsole console;

	public CloseLogConsoleAction(IConsole console) {
		this.console = console;
		setText("Close console");
		setToolTipText("Close console");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ELCL_REMOVE));
	}

	@Override
	public void run() {
		ConsolePlugin.getDefault().getConsoleManager()
				.removeConsoles(new IConsole[] { this.console });
	}

}
