/*******************************************************************************
 * Copyright (c) 2002, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation 
 *******************************************************************************/
package org.eclipse.dltk.ast.expressions;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * 
 * Literal expression. Base class for various literals such as Number or String.
 */
public abstract class Literal extends Expression {

	/**
	 * Value.
	 */
	protected String fLiteralValue;

	/**
	 * Construct with position bindings.
	 * 
	 * @param start -
	 *            start position in associated file.
	 * @param end -
	 *            end position in associated file.
	 */
	protected Literal(int start, int end) {
		super(start, end);
	}

	/**
	 * Construcs from ANTLR token with position bindings. Token holds value.
	 * 
	 * @param token -
	 *            ANTLR token.
	 */
	protected Literal(DLTKToken token) {
		super(token);
		this.fLiteralValue = token.getText();
	}

	/**
	 * Traverse to this node.
	 */
	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			pVisitor.endvisit(this);
		}
	}

	/**
	 * Return value of this literal.
	 */
	@Override
	public String toString() {
		return getValue();
	}

	/**
	 * Return value of this literal.
	 */
	public String getValue() {
		return fLiteralValue;
	}

	/**
	 * Testing purposes only. Print literal.
	 */
	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn("Literal" + this.getSourceRange().toString() + ":" + this.getValue()); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
