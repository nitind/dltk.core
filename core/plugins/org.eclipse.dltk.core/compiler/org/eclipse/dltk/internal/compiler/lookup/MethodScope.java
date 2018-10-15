/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.compiler.lookup;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.compiler.env.lookup.Scope;

public class MethodScope extends BlockScope {
	MethodDeclaration referenceMethod;
	public MethodScope(Scope parent, MethodDeclaration referenceMethod ) {
		super(METHOD_SCOPE, parent);
	}
}
