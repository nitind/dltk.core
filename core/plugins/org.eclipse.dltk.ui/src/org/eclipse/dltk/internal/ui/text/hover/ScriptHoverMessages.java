/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.text.hover;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 */
public final class ScriptHoverMessages extends NLS {

	private static final String BUNDLE_NAME = ScriptHoverMessages.class
			.getName();

	private ScriptHoverMessages() {
		// Do not instantiate
	}

	public static String ScriptdocHover_back;
	public static String ScriptdocHover_forward;

	public static String ScriptdocHover_openDeclaration;

	public static String ScriptdocHover_noAttachedInformation;
	public static String ScriptTextHover_makeStickyHint;
	public static String NoBreakpointAnnotation_addBreakpoint;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ScriptHoverMessages.class);
	}
}
