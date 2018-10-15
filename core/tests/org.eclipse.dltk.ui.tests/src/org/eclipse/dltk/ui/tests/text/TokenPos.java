/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Sam Faktorovich)
 *******************************************************************************/
package org.eclipse.dltk.ui.tests.text;

class TokenPos {
	private final int begin;
	private final int length;

	public TokenPos(int start, int len) {
		begin = start;
		length = len;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof TokenPos) {
			TokenPos other = (TokenPos) arg0;
			return other.begin == begin && other.length == length;
		}
		return false;
	}

	@Override
	public String toString() {
		return "TokenPos[" + begin + "+" + length + "]";
	}
}
