/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.utils.CorePrinter;

public class SourceField extends NamedMember implements IField {

	public SourceField(ModelElement parent, String name) {
		super(parent, name);
	}

	@Override
	public int getElementType() {
		return FIELD;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SourceField)) {
			return false;
		}
		return super.equals(o);
	}

	@Override
	public void printNode(CorePrinter output) {
		output.formatPrint("DLTK Source field:" + getElementName()); //$NON-NLS-1$
	}

	@Override
	protected char getHandleMementoDelimiter() {
		return JEM_FIELD;
	}

	@Override
	public String getFullyQualifiedName(String enclosingTypeSeparator) {
		try {
			return getFullyQualifiedName(enclosingTypeSeparator, false/*
																	 * don't
																	 * show
																	 * parameters
																	 */);
		} catch (ModelException e) {
			// exception thrown only when showing parameters
			return null;
		}
	}

	@Override
	public String getFullyQualifiedName() {
		return getFullyQualifiedName("$"); //$NON-NLS-1$
	}

	@Override
	public String getType() throws ModelException {
		SourceFieldElementInfo info = (SourceFieldElementInfo) getElementInfo();
		if (info != null) {
			return info.getType();
		}
		return null;
	}
}
