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

import java.math.BigInteger;

import org.eclipse.dltk.utils.CorePrinter;


public class BigNumericLiteral extends Literal {

	private BigInteger bigValue;
	

	public BigNumericLiteral(int start, int end, BigInteger value) {
		super(start, end);
		this.bigValue = value;
	}
	
	public BigNumericLiteral(int start, int end, String value, int radix) {
		super(start, end);
		this.bigValue = new BigInteger(value, radix);
	}

	
	@Override
	public String getValue() {
		return bigValue.toString();
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
