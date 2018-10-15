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
package org.eclipse.dltk.formatter;

import org.eclipse.jface.text.IRegion;

public interface IFormatterDocument {

	String getText();

	int getLength();

	/**
	 * @param startOffset
	 * @param endOffset
	 * @return
	 */
	String get(int startOffset, int endOffset);

	/**
	 * @param region
	 * @return
	 */
	String get(IRegion region);

	/**
	 * @param key
	 * @return
	 */
	boolean getBoolean(String key);

	String getString(String key);

	int getInt(String key);

	char charAt(int start);

}
