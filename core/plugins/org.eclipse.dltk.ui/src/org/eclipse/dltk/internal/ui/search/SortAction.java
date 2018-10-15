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

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;


public class SortAction extends Action {
	private int fSortOrder;
	private DLTKSearchResultPage fPage;

	public SortAction(String label, DLTKSearchResultPage page, int sortOrder) {
		super(label);
		fPage= page;
		fSortOrder= sortOrder;
	}

	@Override
	public void run() {
		BusyIndicator.showWhile(fPage.getViewer().getControl().getDisplay(),
				() -> fPage.setSortOrder(fSortOrder));
	}

	public int getSortOrder() {
		return fSortOrder;
	}
}
