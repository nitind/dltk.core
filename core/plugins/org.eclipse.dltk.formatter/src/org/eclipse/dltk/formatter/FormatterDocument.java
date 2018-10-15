/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.text.IRegion;

public class FormatterDocument implements IFormatterDocument {

	private final String text;
	private final Map<String, Boolean> booleans = new HashMap<>();
	private final Map<String, String> strings = new HashMap<>();
	private final Map<String, Integer> ints = new HashMap<>();

	/**
	 * @param text
	 */
	public FormatterDocument(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public int getLength() {
		return text.length();
	}

	@Override
	public String get(int startOffset, int endOffset) {
		return text.substring(startOffset, endOffset);
	}

	@Override
	public String get(IRegion region) {
		return get(region.getOffset(), region.getOffset() + region.getLength());
	}

	public void setBoolean(String key, boolean value) {
		booleans.put(key, Boolean.valueOf(value));
	}

	@Override
	public boolean getBoolean(String key) {
		final Boolean value = booleans.get(key);
		return value != null && value.booleanValue();
	}

	public void setString(String key, String value) {
		strings.put(key, value);
	}

	@Override
	public String getString(String key) {
		return strings.get(key);
	}

	public void setInt(String key, int value) {
		ints.put(key, Integer.valueOf(value));
	}

	@Override
	public int getInt(String key) {
		final Integer value = ints.get(key);
		return value != null ? value.intValue() : 0;
	}

	@Override
	public char charAt(int index) {
		return text.charAt(index);
	}

}
