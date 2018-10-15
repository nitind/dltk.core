/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ui.browsing;

import org.eclipse.dltk.internal.ui.actions.AbstractToggleLinkingAction;

/**
 * This action toggles whether this package explorer links its selection to the active
 * editor.
 *
 * @since 2.1
 */
public class ToggleLinkingAction extends AbstractToggleLinkingAction {

	ScriptBrowsingPart fJavaBrowsingPart;

	/**
	 * Constructs a new action.
	 */
	public ToggleLinkingAction(ScriptBrowsingPart part) {
		setChecked(part.isLinkingEnabled());
		fJavaBrowsingPart= part;
	}

	/**
	 * Runs the action.
	 */
	@Override
	public void run() {
		fJavaBrowsingPart.setLinkingEnabled(isChecked());
	}

}
