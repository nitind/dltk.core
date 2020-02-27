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

import java.text.Collator;
import java.util.Comparator;

import org.eclipse.ui.IWorkingSet;

public class WorkingSetComparator implements Comparator {

	private Collator fCollator = Collator.getInstance();

	@Override
	public int compare(Object o1, Object o2) {
		String name1 = null;
		String name2 = null;

		if (o1 instanceof IWorkingSet)
			name1 = ((IWorkingSet) o1).getLabel();

		if (o2 instanceof IWorkingSet)
			name2 = ((IWorkingSet) o2).getLabel();

		return fCollator.compare(name1, name2);
	}
}
