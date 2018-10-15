/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ast.declarations;

import org.eclipse.dltk.ast.parser.IModuleDeclaration;

/**
 * Compatibility wrapper of the parser result.
 */
public class ModuleDeclarationWrapper extends ModuleDeclaration {

	private final IModuleDeclaration target;

	public ModuleDeclarationWrapper(IModuleDeclaration target) {
		super(0);
		this.target = target;
	}

	public IModuleDeclaration getTarget() {
		return target;
	}

}
