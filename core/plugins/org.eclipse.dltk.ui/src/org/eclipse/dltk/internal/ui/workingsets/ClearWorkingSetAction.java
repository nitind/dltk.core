/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.workingsets;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.jface.action.Action;

/**
 * Clears the selected working set in the action group's view.
 *
 *
 */
public class ClearWorkingSetAction extends Action {

	private WorkingSetFilterActionGroup fActionGroup;

	public ClearWorkingSetAction(WorkingSetFilterActionGroup actionGroup) {
		super(WorkingSetMessages.ClearWorkingSetAction_text);
		setToolTipText(WorkingSetMessages.ClearWorkingSetAction_toolTip);
		setEnabled(actionGroup.getWorkingSet() != null);
		if (DLTKCore.DEBUG) {
			System.err.println("Add help support here..."); //$NON-NLS-1$
		}

		//PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IScriptHelpContextIds.CLEAR_WORKING_SET_ACTION);
		fActionGroup= actionGroup;
	}

	@Override
	public void run() {
		fActionGroup.setWorkingSet(null, true);
	}
}
