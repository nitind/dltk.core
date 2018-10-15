/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.formatter.internal;

import org.eclipse.dltk.formatter.IFormatterIndentGenerator;

/**
 * @since 2.0
 */
public class FormatterMixedIndentGenerator implements IFormatterIndentGenerator {

	private final int indentSize;
	private final int tabSize;

	/**
	 * @param indentSize
	 * @param tabSize
	 */
	public FormatterMixedIndentGenerator(int indentSize, int tabSize) {
		this.indentSize = Math.max(indentSize, 1);
		this.tabSize = Math.max(tabSize, 1);
	}

	@Override
	public void generateIndent(int indentLevel, StringBuilder target) {
		final int indent = indentLevel * indentSize;
		final int tabCount = indent / tabSize;
		for (int i = 0; i < tabCount; ++i) {
			target.append('\t');
		}
		final int spaceCount = indent % tabSize;
		for (int i = 0; i < spaceCount; ++i) {
			target.append(' ');
		}
	}

	@Override
	public int getTabSize() {
		return tabSize;
	}

}
