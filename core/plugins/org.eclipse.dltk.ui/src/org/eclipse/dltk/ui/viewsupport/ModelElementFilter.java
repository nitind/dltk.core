/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ui.viewsupport;

import org.eclipse.dltk.core.IMember;
import org.eclipse.jface.viewers.Viewer;

public class ModelElementFilter extends AbstractModelElementFilter {
	private int fElementType;

	public ModelElementFilter(int type) {
		fElementType = type;
	}

	@Override
	public String getFilteringType() {
		return "ModelElementFilter:" + Integer.toString(fElementType); //$NON-NLS-1$
	}

	public boolean isFilterProperty(Object element, Object property) {
		return false;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IMember) {
			IMember member = (IMember) element;
			if (member.getElementType() == fElementType) {
				return false;
			}
		}

		return true;
	}
}
