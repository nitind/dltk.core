/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ui.templates;

import org.eclipse.osgi.util.NLS;

public final class TemplateMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.ui.templates.messages"; //$NON-NLS-1$

	static {
		NLS.initializeMessages(BUNDLE_NAME, TemplateMessages.class);
	}
	
	public static String Variable_File_Description;
	public static String Variable_Language_Description;
	public static String Variable_Interpreter_Description;
	
	public static String Validation_SeveralCursorPositions;
	public static String ScriptTemplateAccess_unableToLoadTemplateStore;
}
