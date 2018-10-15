/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ui.text.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ui.CodeFormatterConstants;

public enum TabStyle {

	TAB(CodeFormatterConstants.TAB),

	SPACES(CodeFormatterConstants.SPACE),

	MIXED(CodeFormatterConstants.MIXED);

	private final String name;

	private TabStyle(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	private static final Map<String, TabStyle> byName = new HashMap<>();

	static {
		byName.put(TAB.getName(), TAB);
		byName.put(SPACES.getName(), SPACES);
		byName.put(MIXED.getName(), MIXED);
	}

	public static TabStyle forName(String name) {
		return byName.get(name);
	}

	public static TabStyle forName(String name, TabStyle deflt) {
		final TabStyle result = forName(name);
		return result != null ? result : deflt;
	}

}
