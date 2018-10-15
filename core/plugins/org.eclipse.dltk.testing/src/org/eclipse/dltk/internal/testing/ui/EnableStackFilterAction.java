/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.testing.ui;


import org.eclipse.jface.action.Action;

import org.eclipse.ui.PlatformUI;

import org.eclipse.dltk.testing.DLTKTestingMessages;
import org.eclipse.dltk.testing.DLTKTestingPlugin;

/**
 * Action to enable/disable stack trace filtering.
 */
public class EnableStackFilterAction extends Action {

	private FailureTrace fView;	
	
	public EnableStackFilterAction(FailureTrace view) {
		super(DLTKTestingMessages.EnableStackFilterAction_action_label);  
		setDescription(DLTKTestingMessages.EnableStackFilterAction_action_description);  
		setToolTipText(DLTKTestingMessages.EnableStackFilterAction_action_tooltip); 
		
		setDisabledImageDescriptor(DLTKTestingPlugin.getImageDescriptor("dlcl16/cfilter.gif")); //$NON-NLS-1$
		setHoverImageDescriptor(DLTKTestingPlugin.getImageDescriptor("elcl16/cfilter.gif")); //$NON-NLS-1$
		setImageDescriptor(DLTKTestingPlugin.getImageDescriptor("elcl16/cfilter.gif")); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IDLTKTestingHelpContextIds.ENABLEFILTER_ACTION);

		fView= view;
		setChecked(false);
		setEnabled(false);
	}

	@Override
	public void run() {
		fView.getTestRunnerUI().setFilterStack(isChecked());
		fView.refresh();
	}
}
