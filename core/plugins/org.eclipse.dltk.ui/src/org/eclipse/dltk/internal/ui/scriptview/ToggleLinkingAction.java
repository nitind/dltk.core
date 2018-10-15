/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.scriptview;

import org.eclipse.dltk.internal.ui.actions.AbstractToggleLinkingAction;
import org.eclipse.dltk.ui.IScriptExplorerViewPart;


/**
 * This action toggles whether this package explorer links its selection to the
 * active editor.
 *
 *
 */
public class ToggleLinkingAction extends AbstractToggleLinkingAction {

	private IScriptExplorerViewPart fPackageExplorerPart;

	/**
	 * Constructs a new action.
	 */
	public ToggleLinkingAction(IScriptExplorerViewPart explorer) {
		setChecked(explorer.isLinkingEnabled());
		fPackageExplorerPart= explorer;
	}

	/**
	 * Runs the action.
	 */
	@Override
	public void run() {
		fPackageExplorerPart.setLinkingEnabled(isChecked());
	}

}
