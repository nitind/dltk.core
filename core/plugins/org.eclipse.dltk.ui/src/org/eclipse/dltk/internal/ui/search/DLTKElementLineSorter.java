/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.search;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class DLTKElementLineSorter extends ViewerComparator {
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		DLTKElementLine jel1 = (DLTKElementLine) e1;
		DLTKElementLine jel2 = (DLTKElementLine) e2;
		return jel1.getLine() - jel2.getLine();
	}
}
