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
package org.eclipse.dltk.ui.dialogs;

/**
 * Generic interface to call edit dialogs
 */
public interface IModifyDialog {

	void setTitle(String title);

	/**
	 * Opens the dialog
	 * 
	 * @return <code>true</code> on OK, <code>false</code> otherwise
	 */
	boolean execute();

}
