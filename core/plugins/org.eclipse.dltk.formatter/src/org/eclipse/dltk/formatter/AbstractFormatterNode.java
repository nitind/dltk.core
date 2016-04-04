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

public abstract class AbstractFormatterNode implements IFormatterNode {

	private final IFormatterDocument document;

	/**
	 * @param document
	 */
	public AbstractFormatterNode(IFormatterDocument document) {
		this.document = document;
	}

	@Override
	public IFormatterDocument getDocument() {
		return document;
	}

	protected String getShortClassName() {
		final String name = getClass().getName();
		int index = name.lastIndexOf('.');
		return index > 0 ? name.substring(index + 1) : name;
	}

	@Override
	public String toString() {
		return getShortClassName();
	}

	protected int getInt(String key) {
		return document.getInt(key);
	}
}
