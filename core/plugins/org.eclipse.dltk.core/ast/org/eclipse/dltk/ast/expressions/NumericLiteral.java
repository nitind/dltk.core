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

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * 
 * Numeric literal. Used to hold ints, floats and complex numbers.
 * 
 */
public class NumericLiteral extends Literal {

	private long intValue;
	
	/**
	 * Construct from ANTLR token. With appropriate position information.
	 * 
	 * @param number
	 */
	public NumericLiteral(DLTKToken number) {
		super(number);
	}

	public NumericLiteral(int start, int end, long value) {
		super(start, end);
		this.intValue = value;
	}
	
	public long getIntValue () {
		return intValue;
	}

	
	@Override
	public String getValue() {
		return String.valueOf(intValue);
	}

	/**
	 * Return kind.
	 */
	@Override
	public int getKind() {
		return NUMBER_LITERAL;
	}

	/**
	 * Testing purposes only. Used to print number.
	 */
	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn(this.getValue());
	}
}
