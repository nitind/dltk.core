/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.formatter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.DLTKCore;

public class FormatterContext implements IFormatterContext, Cloneable {

	private static class PathEntry {
		final IFormatterNode node;
		int childIndex = 0;

		/**
		 * @param node
		 */
		public PathEntry(IFormatterNode node) {
			this.node = node;
		}

	}

	private int indent;
	private boolean indenting = true;
	private boolean comment = false;
	private boolean wrapping = false;
	private int blankLines = 0;
	private final List<PathEntry> path = new ArrayList<PathEntry>();

	public FormatterContext(int indent) {
		this.indent = indent;
	}

	@Override
	public IFormatterContext copy() {
		try {
			return (IFormatterContext) clone();
		} catch (CloneNotSupportedException e) {
			DLTKCore.error("FormatterContext.copy() error", e); //$NON-NLS-1$
			throw new IllegalStateException();
		}
	}

	@Override
	public void decIndent() {
		--indent;
		// TODO assert indent >= 0;
	}

	@Override
	public void incIndent() {
		++indent;
	}

	@Override
	public void resetIndent() {
		indent = 0;
	}

	@Override
	public int getIndent() {
		return indent;
	}

	@Override
	public boolean isIndenting() {
		return indenting;
	}

	@Override
	public void setIndenting(boolean value) {
		this.indenting = value;
	}

	@Override
	public boolean isComment() {
		return comment;
	}

	@Override
	public void setComment(boolean value) {
		this.comment = value;
	}

	@Override
	public int getBlankLines() {
		return blankLines;
	}

	@Override
	public void resetBlankLines() {
		blankLines = -1;
	}

	@Override
	public void setBlankLines(int value) {
		if (value >= 0 && value > blankLines) {
			blankLines = value;
		}
	}

	@Override
	public void enter(IFormatterNode node) {
		path.add(new PathEntry(node));
	}

	@Override
	public void leave(IFormatterNode node) {
		final PathEntry entry = path.remove(path.size() - 1);
		if (entry.node != node) {
			throw new IllegalStateException("leave() - node mismatch"); //$NON-NLS-1$
		}
		if (!path.isEmpty() && isCountable(node)) {
			final PathEntry parent = path.get(path.size() - 1);
			++parent.childIndex;
		}
	}

	protected boolean isCountable(IFormatterNode node) {
		return true;
	}

	@Override
	public IFormatterNode getParent() {
		if (path.size() > 1) {
			final PathEntry entry = path.get(path.size() - 2);
			return entry.node;
		} else {
			return null;
		}
	}

	@Override
	public int getChildIndex() {
		if (path.size() > 1) {
			final PathEntry entry = path.get(path.size() - 2);
			return entry.childIndex;
		} else {
			return -1;
		}
	}

	@Override
	public boolean isWrapping() {
		return wrapping;
	}

	@Override
	public void setWrapping(boolean value) {
		this.wrapping = value;
	}

}
