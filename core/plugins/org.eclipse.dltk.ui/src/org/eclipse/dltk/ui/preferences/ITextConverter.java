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
package org.eclipse.dltk.ui.preferences;

/**
 * Preference value converter. Should be used when value should be converted
 * between text field and preference store.
 */
public interface ITextConverter {

	/**
	 * Convert value from the internal format to the format suitable to display
	 * in the text box
	 * 
	 * @param value
	 * @return
	 */
	String convertPreference(String value);

	/**
	 * Convert value entered into the text box to the internal format.
	 * 
	 * @param input
	 * @return
	 */
	String convertInput(String input);

}
