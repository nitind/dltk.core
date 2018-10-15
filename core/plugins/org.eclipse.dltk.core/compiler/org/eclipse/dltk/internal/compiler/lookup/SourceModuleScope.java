/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *     Erling Ellingsen -  patch for bug 125570
 *******************************************************************************/
package org.eclipse.dltk.internal.compiler.lookup;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.lookup.Scope;

public class SourceModuleScope extends Scope {
	public LookupEnvironment environment;

	public ModuleDeclaration referenceContext;

	public SourceModuleScope(ModuleDeclaration unit,
			LookupEnvironment environment) {
		super(COMPILATION_UNIT_SCOPE, null);
		
		this.referenceContext = unit;
		this.environment = environment;
		
		unit.scope = this;
	}
}
