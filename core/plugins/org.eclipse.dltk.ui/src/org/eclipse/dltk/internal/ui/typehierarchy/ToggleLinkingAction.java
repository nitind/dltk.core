/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.typehierarchy;

import org.eclipse.dltk.internal.ui.actions.AbstractToggleLinkingAction;

/**
 * This action toggles whether the type hierarchy links its selection to the active
 * editor.
 *
 * @since 2.1
 */
public class ToggleLinkingAction extends AbstractToggleLinkingAction {

	TypeHierarchyViewPart fHierarchyViewPart;

	/**
	 * Constructs a new action.
	 */
	public ToggleLinkingAction(TypeHierarchyViewPart part) {
		setChecked(part.isLinkingEnabled());
		fHierarchyViewPart= part;
	}

	/**
	 * Runs the action.
	 */
	@Override
	public void run() {
		fHierarchyViewPart.setLinkingEnabled(isChecked());
	}

}
