/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.scriptview;

import org.eclipse.dltk.internal.ui.dnd.BasicSelectionTransferDragAdapter;
import org.eclipse.jface.viewers.ISelectionProvider;


public class SelectionTransferDragAdapter extends BasicSelectionTransferDragAdapter {
		
	public SelectionTransferDragAdapter(ISelectionProvider provider) {
		super(provider);
	}
}
