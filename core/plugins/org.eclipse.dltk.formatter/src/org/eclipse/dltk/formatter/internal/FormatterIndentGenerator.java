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

import java.util.Arrays;

import org.eclipse.dltk.formatter.IFormatterIndentGenerator;

/**
 * @since 2.0
 */
public class FormatterIndentGenerator implements IFormatterIndentGenerator {

	private final char[] chars;
	private final int indentationSize;
	private final int tabSize;

	public FormatterIndentGenerator(char ch, int indentationSize, int tabSize) {
		this.chars = new char[256];
		Arrays.fill(chars, ch);
		this.indentationSize = indentationSize;
		this.tabSize = tabSize;
	}

	@Override
	public void generateIndent(final int indentLevel, StringBuilder target) {
		if (indentLevel > 0) {
			int size = indentLevel * indentationSize;
			while (size > 0) {
				final int step = Math.min(size, chars.length);
				target.append(chars, 0, step);
				size -= step;
			}
		}
	}

	@Override
	public int getTabSize() {
		return tabSize;
	}

}
