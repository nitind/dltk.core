/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.workingsets;

import org.eclipse.jface.action.IMenuManager;

public interface IWorkingSetActionGroup {

	public static final String ACTION_GROUP= "working_set_action_group"; //$NON-NLS-1$
	
	public void fillViewMenu(IMenuManager mm);
	
	public void cleanViewMenu(IMenuManager menuManager);

}
