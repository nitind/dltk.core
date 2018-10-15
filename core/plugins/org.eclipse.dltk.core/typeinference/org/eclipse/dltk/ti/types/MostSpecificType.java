/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ti.types;

/**
 * Represents most specific type in language. This class is just a symbol, so
 * user algorithms should convert it to contrete type himself if needed.
 */
public final class MostSpecificType implements IEvaluatedType {

	private static MostSpecificType instance;

	private MostSpecificType() {
	}

	@Override
	public String getTypeName() {
		return null;
	}

	public static MostSpecificType getInstance() {
		if (instance == null) {
			instance = new MostSpecificType();
		}
		return instance;
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		return false;
	}

}