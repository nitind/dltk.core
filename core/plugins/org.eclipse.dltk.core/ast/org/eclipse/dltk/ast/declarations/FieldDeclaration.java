/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ast.declarations;


public class FieldDeclaration extends Declaration {

	
	public FieldDeclaration(String name, int nameStart, int nameEnd,
			int declStart, int declEnd) {
		super(declStart, declEnd);
		this.setName(name);
		this.setNameStart(nameStart);
		this.setNameEnd(nameEnd);
	}
}
