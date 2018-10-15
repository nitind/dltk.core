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

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.Viewer;

public class ModelElementFlagsFilter extends AbstractModelElementFilter {
	private int fFlags;

	public ModelElementFlagsFilter(int flags) {
		this.fFlags = flags;
	}

	@Override
	public String getFilteringType() {
		return "ModelElementFlagsFilter:" + Integer.toString(fFlags); //$NON-NLS-1$
	}

	public boolean isFilterProperty(Object element, Object property) {
		return false;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IMember) {
			IMember member = (IMember) element;
			try {
				if ((member.getFlags() & this.fFlags) != 0) {
					return false;
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}
}
