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


public class GroupAction extends Action {
	private int fGrouping;
	private DLTKSearchResultPage fPage;

	public GroupAction(String label, String tooltip, DLTKSearchResultPage page, int grouping) {
		super(label);
		setToolTipText(tooltip);
		fPage= page;
		fGrouping= grouping;
	}

	@Override
	public void run() {
		fPage.setGrouping(fGrouping);
	}

	public int getGrouping() {
		return fGrouping;
	}
}
