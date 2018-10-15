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

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;

/**
 * Action enable / disable member filtering
 */
public class EnableMemberFilterAction extends Action {

	private TypeHierarchyViewPart fView;

	public EnableMemberFilterAction(TypeHierarchyViewPart v, boolean initValue) {
		super(TypeHierarchyMessages.EnableMemberFilterAction_label);
		setDescription(TypeHierarchyMessages.EnableMemberFilterAction_description);
		setToolTipText(TypeHierarchyMessages.EnableMemberFilterAction_tooltip);

		DLTKPluginImages.setLocalImageDescriptors(this, "impl_co.png"); //$NON-NLS-1$

		fView= v;
		setChecked(initValue);

		//PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.ENABLE_METHODFILTER_ACTION);

	}

	@Override
	public void run() {
		BusyIndicator.showWhile(fView.getSite().getShell().getDisplay(),
				() -> fView.enableMemberFilter(isChecked()));
	}
}
