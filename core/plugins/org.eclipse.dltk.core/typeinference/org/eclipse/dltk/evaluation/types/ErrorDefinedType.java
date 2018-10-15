/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.evaluation.types;

import org.eclipse.dltk.ti.types.IEvaluatedType;

public class ErrorDefinedType implements IEvaluatedType {

	public static final IEvaluatedType INSTANCE = new ErrorDefinedType();

	private ErrorDefinedType() {
	}

	@Override
	public String getTypeName() {
		return "error defined"; //$NON-NLS-1$
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		// TODO Auto-generated method stub
		return false;
	}
}
