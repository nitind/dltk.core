/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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
package org.eclipse.dltk.ui.formatter;

import java.util.Map;

public interface IFormatterModifyDialog {

	void setProfileManager(IProfileManager manager);

	/**
	 * Sets the preferences the dialog should use
	 * 
	 * @param prefs
	 */
	void setPreferences(Map<String, String> prefs);

	/**
	 * Opens the modal dialog and returns only after the dialog was completed.
	 * The return value should be {@link org.eclipse.jface.window.Window#OK} or
	 * {@link org.eclipse.jface.window.Window#CANCEL}
	 */
	int open();

	/**
	 * Returns the preferences modified by the dialog
	 * 
	 * @return
	 */
	Map<String, String> getPreferences();

	String getProfileName();

	IFormatterModifyDialogOwner getOwner();

	IScriptFormatterFactory getFormatterFactory();

}
