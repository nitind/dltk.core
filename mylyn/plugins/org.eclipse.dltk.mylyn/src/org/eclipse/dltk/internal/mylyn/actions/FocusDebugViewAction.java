/*******************************************************************************
 * Copyright (c) 2004, 2017 Tasktop Technologies and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.dltk.internal.mylyn.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.internal.ui.views.launch.LaunchView;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.mylyn.context.ui.AbstractFocusViewAction;
import org.eclipse.mylyn.context.ui.InterestFilter;
import org.eclipse.ui.IViewPart;

/**
 * @author Mik Kersten
 */
public class FocusDebugViewAction extends AbstractFocusViewAction {

	public FocusDebugViewAction() {
		super(new InterestFilter(), true, true, false);
	}

	@Override
	public List<StructuredViewer> getViewers() {
		List<StructuredViewer> viewers = new ArrayList<>();
		IViewPart view = super.getPartForAction();
		if (view instanceof LaunchView) {
			LaunchView launchView = (LaunchView) view;
			viewers.add((StructuredViewer) launchView.getViewer());
		}
		return viewers;
	}

}
