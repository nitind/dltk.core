/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.core.model.binary;

import org.eclipse.dltk.compiler.env.IGenericMethod;
import org.eclipse.dltk.core.IParameter;
import org.eclipse.dltk.internal.core.SourceMethodUtils;

class BinaryMethodElementInfo extends BinaryMemberInfo implements
		IGenericMethod {

	private IParameter[] arguments;
	private boolean isConstructor;
	private String returnType;

	protected void setArguments(IParameter[] arguments) {
		this.arguments = arguments;
	}

	public IParameter[] getArguments() {
		return this.arguments;
	}

	@Override
	public int getModifiers() {
		return getFlags();
	}

	@Override
	public String[] getArgumentNames() {
		return SourceMethodUtils.getParameterNames(arguments);
	}

	public void setIsConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	@Override
	public boolean isConstructor() {
		return isConstructor;
	}

	public String getReturnTypeName() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
