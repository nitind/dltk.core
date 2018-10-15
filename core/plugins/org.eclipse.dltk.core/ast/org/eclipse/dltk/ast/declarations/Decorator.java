/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ast.declarations;

import org.eclipse.dltk.ast.DLTKToken;

public abstract class Decorator extends Declaration {

	protected Decorator(DLTKToken nameToken, int sourceStart, int sourceEnd) {
		super(nameToken, sourceStart, sourceEnd);
	}

	@Override
	public int getKind() {
		return Declaration.D_DECLARATOR;
	}
}
