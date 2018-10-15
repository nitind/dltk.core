/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.model.binary;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IParameter;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * @since 2.0
 */
public class BinaryMethod extends BinaryMember implements IMethod {

	public BinaryMethod(ModelElement parent, String name) {
		super(parent, name);
	}

	@Override
	public void printNode(CorePrinter output) {
	}

	@Override
	public int getElementType() {
		return METHOD;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BinaryMethod)) {
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
	public IParameter[] getParameters() throws ModelException {
		return ((BinaryMethodElementInfo) getElementInfo()).getArguments();
	}

	@Override
	public String[] getParameterNames() throws ModelException {
		return ((BinaryMethodElementInfo) getElementInfo()).getArgumentNames();
	}

	@Override
	public boolean isConstructor() throws ModelException {
		return ((BinaryMethodElementInfo) getElementInfo()).isConstructor();
	}

	@Override
	public String getType() throws ModelException {
		return ((BinaryMethodElementInfo) getElementInfo()).getReturnTypeName();
	}
}
