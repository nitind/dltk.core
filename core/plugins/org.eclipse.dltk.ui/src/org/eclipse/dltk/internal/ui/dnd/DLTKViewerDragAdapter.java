/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.dnd;

import org.eclipse.jface.util.DelegatingDragAdapter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceEvent;

public class DLTKViewerDragAdapter extends DelegatingDragAdapter {

	private StructuredViewer fViewer;

	public DLTKViewerDragAdapter(StructuredViewer viewer) {
		super();
		fViewer = viewer;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		IStructuredSelection selection = fViewer.getStructuredSelection();
		if (selection.isEmpty()) {
			event.doit = false;
			return;
		}
		super.dragStart(event);
	}
}
