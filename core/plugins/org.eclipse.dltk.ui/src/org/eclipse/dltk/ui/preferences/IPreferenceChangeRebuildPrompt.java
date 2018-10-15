/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
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
 * Returns the prompt that should be used in the popup box that indicates a
 * build needs to occur.
 */
public interface IPreferenceChangeRebuildPrompt {

	/**
	 * Returns the title
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * Returns the message
	 * 
	 * @return
	 */
	String getMessage();

}
