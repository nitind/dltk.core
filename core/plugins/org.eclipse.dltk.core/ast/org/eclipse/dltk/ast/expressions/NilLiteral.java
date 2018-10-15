/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ast.expressions;

public class NilLiteral extends Literal {

	public NilLiteral(int start, int end) {
		super(start, end);
	}

	@Override
	public String getValue() {
		return "nil"; //$NON-NLS-1$
	}

	@Override
	public int getKind() {
		return 0;
	}

}
