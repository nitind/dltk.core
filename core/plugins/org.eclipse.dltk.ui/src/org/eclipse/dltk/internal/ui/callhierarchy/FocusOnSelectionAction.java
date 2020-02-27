/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.callhierarchy;

import java.text.MessageFormat;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.corext.callhierarchy.MethodWrapper;
import org.eclipse.dltk.internal.ui.util.SelectionUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;

class FocusOnSelectionAction extends Action {
	private CallHierarchyViewPart fPart;

	public FocusOnSelectionAction(CallHierarchyViewPart part) {
		super(CallHierarchyMessages.FocusOnSelectionAction_focusOnSelection_text);
		fPart = part;
		setDescription(CallHierarchyMessages.FocusOnSelectionAction_focusOnSelection_description);
		setToolTipText(CallHierarchyMessages.FocusOnSelectionAction_focusOnSelection_tooltip);
//        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.CALL_HIERARCHY_FOCUS_ON_SELECTION_ACTION);
		if (DLTKCore.DEBUG) {
			System.err.println("Add help support here..."); //$NON-NLS-1$
		}
	}

	public boolean canActionBeAdded() {
		Object element = SelectionUtil.getSingleElement(getSelection());

		IMethod method = getSelectedMethod(element);

		if (method != null) {
			setText(MessageFormat.format(CallHierarchyMessages.FocusOnSelectionAction_focusOn_text,
					method.getElementName()));

			return true;
		}

		return false;
	}

	private IMethod getSelectedMethod(Object element) {
		IMethod method = null;

		if (element instanceof IMethod) {
			method = (IMethod) element;
		} else if (element instanceof MethodWrapper) {
			IModelElement member = ((MethodWrapper) element).getMember();
			if (member.getElementType() == IModelElement.METHOD) {
				method = (IMethod) member;
			}
		}
		return method;
	}

	@Override
	public void run() {
		Object element = SelectionUtil.getSingleElement(getSelection());

		IMethod method = getSelectedMethod(element);
		if (method != null) {
			fPart.setMethod(method);
		}
	}

	private ISelection getSelection() {
		ISelectionProvider provider = fPart.getSite().getSelectionProvider();

		if (provider != null) {
			return provider.getSelection();
		}

		return null;
	}
}
