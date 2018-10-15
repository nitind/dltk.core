/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ast.statements;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;

/**
 * Base class for all statements.
 * 
 */
public abstract class Statement extends ASTNode implements StatementConstants {
	protected Statement(int start, int end) {
		super(start, end);
	}

	protected Statement() {
		super();
	}

	protected Statement(DLTKToken token) {
		super(token);
	}

	public abstract int getKind();

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
	}
}
