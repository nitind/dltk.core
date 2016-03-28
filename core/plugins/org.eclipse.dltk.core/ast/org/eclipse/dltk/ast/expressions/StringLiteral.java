/*******************************************************************************
 * Copyright (c) 2002, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.ast.expressions;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * String representation.
 */
public class StringLiteral extends Literal {

	/**
	 * Construct from ATRL token.
	 * 
	 * @param t
	 */
	public StringLiteral(DLTKToken t) {
		super(t);
	}

	/**
	 * Construct from position information and value.
	 * 
	 * @param start -
	 *            start position.
	 * @param end -
	 *            end position.
	 * @param value -
	 *            value.
	 */
	public StringLiteral(int start, int end, String value) {
		super(start, end);
		this.fLiteralValue = value;
	}

	public StringLiteral(int sourceStart, DLTKToken dltk, String string) {
		this(sourceStart, dltk.getColumn(), string);
	}

	/**
	 * Return kind.
	 */
	@Override
	public int getKind() {
		return STRING_LITERAL;
	}

	/**
	 * Testing purposes only. Used to print value.
	 */
	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn(this.getValue());
	}
}
