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
package org.eclipse.dltk.formatter;

import org.eclipse.jface.text.IRegion;

public class FormatterIndentDetector implements IFormatterWriter {

	private final int offset;
	private boolean indentDetected = false;
	private int level;

	/**
	 * @param offset
	 */
	public FormatterIndentDetector(int offset) {
		this.offset = offset;
	}

	@Override
	public void addNewLineCallback(IFormatterCallback callback) {
		// empty
	}

	@Override
	public void excludeRegion(IRegion region) {
		// empty

	}

	@Override
	public void ensureLineStarted(IFormatterContext context) {
		// empty
	}

	@Override
	public void write(IFormatterContext context, int startOffset, int endOffset) {
		if (!indentDetected && endOffset >= offset) {
			level = context.getIndent();
			indentDetected = true;
		}
	}

	@Override
	public void writeText(IFormatterContext context, String text) {
		// empty
	}

	@Override
	public void writeLineBreak(IFormatterContext context) {
		// empty
	}

	@Override
	public void skipNextLineBreaks(IFormatterContext context) {
		// empty
	}

	@Override
	public void skipNextLineBreaks(IFormatterContext context, boolean value) {
		// empty
	}

	@Override
	public void appendToPreviousLine(IFormatterContext context, String text) {
		// empty
	}

	@Override
	public void disableAppendToPreviousLine() {
		// empty
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

}
