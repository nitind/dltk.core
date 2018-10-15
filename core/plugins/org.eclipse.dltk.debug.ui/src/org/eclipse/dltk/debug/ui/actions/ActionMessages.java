/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.debug.ui.actions;

import org.eclipse.osgi.util.NLS;

public class ActionMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.debug.ui.actions.ActionMessages";//$NON-NLS-1$

	static {
		NLS.initializeMessages(BUNDLE_NAME, ActionMessages.class);
	}

	public static String BreakpointAction_Breakpoint_configuration_1;

	public static String BreakpointAction_Exceptions_occurred_attempting_to_modify_breakpoint__2;

	// ScriptBreakpointPropertiesRulerAction
	public static String ScriptBreakpointPropertiesRulerAction_BreakpointProperties;
	public static String ToggleBreakpointAdapter_10;
	public static String ToggleBreakpointAdapter_2;
}
