/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.editor.selectionaction;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 *
	 *
 */
final class SelectionActionMessages extends NLS {

	private static final String BUNDLE_NAME= SelectionActionMessages.class.getName();

	private SelectionActionMessages() {
		// Do not instantiate
	}

	public static String StructureSelect_error_title;
	public static String StructureSelect_error_message;
	public static String StructureSelectEnclosing_label;
	public static String StructureSelectEnclosing_tooltip;
	public static String StructureSelectEnclosing_description;
	public static String StructureSelectHistory_label;
	public static String StructureSelectHistory_tooltip;
	public static String StructureSelectHistory_description;

	public static String GotoNextMember_label;
	public static String GotoPreviousMember_label;

	static {
		NLS.initializeMessages(BUNDLE_NAME, SelectionActionMessages.class);
	}
}
