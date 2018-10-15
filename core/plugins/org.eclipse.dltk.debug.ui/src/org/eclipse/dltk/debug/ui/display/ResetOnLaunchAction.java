/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.debug.ui.display;

import org.eclipse.jface.action.Action;

public class ResetOnLaunchAction extends Action {

	private final DebugConsolePage page;

	public ResetOnLaunchAction(DebugConsolePage page) {
		super(Messages.ResetOnLaunchAction_text, AS_CHECK_BOX);
		this.page = page;
	}

	@Override
	public void run() {
		page.setResetOnLaunch(isChecked());
	}

}
