package org.eclipse.dltk.ui.browsing.ext;

/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others. 
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: IBM Corporation - initial API and implementation
 ******************************************************************************/
class ColumnFormData {

	int width;

	String getName() {
		String string = getClass().getName();
		int index = string.lastIndexOf('.');
		if (index == -1)
			return string;
		return string.substring(index + 1, string.length());
	}

	/**
	 * Returns a string containing a concise, human-readable description of the
	 * receiver.
	 *
	 * @return a string representation of the event
	 */
	@Override
	public String toString() {
		return getName() + " {weight=" + width + "}";  //$NON-NLS-1$//$NON-NLS-2$
	}
}
