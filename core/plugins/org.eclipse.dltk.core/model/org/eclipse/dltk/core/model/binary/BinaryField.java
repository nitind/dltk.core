/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc.- initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.model.binary;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * @since 2.0
 */
public class BinaryField extends BinaryMember implements IField {

	public BinaryField(ModelElement parent, String name) {
		super(parent, name);
	}

	@Override
	public void printNode(CorePrinter output) {
	}

	@Override
	public int getElementType() {
		return FIELD;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BinaryField)) {
			return false;
		}
		return super.equals(o);
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
		BinaryFieldElementInfo info = (BinaryFieldElementInfo) getElementInfo();
		if (info != null) {
			return info.getType();
		}
		return null;
	}
}
