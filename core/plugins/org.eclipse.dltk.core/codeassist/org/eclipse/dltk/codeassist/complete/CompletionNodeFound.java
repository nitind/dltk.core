/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.codeassist.complete;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.compiler.env.lookup.Scope;

public class CompletionNodeFound extends RuntimeException {
	private static final long serialVersionUID = 8556836876798770199L;
	public transient ASTNode astNode;

	public transient Scope scope;
	public boolean insideTypeAnnotation = false;

	// compatible
	public CompletionNodeFound() {
		// we found a problem in the completion
		this(null, null, false);
	}

	public CompletionNodeFound(ASTNode astNode, Scope scope) {
		this(astNode, scope, false);
	}

	public CompletionNodeFound(ASTNode astNode, Scope scope,
			boolean insideTypeAnnotation) {
		this.astNode = astNode;
		this.scope = scope;
		this.insideTypeAnnotation = insideTypeAnnotation;
	}
}
