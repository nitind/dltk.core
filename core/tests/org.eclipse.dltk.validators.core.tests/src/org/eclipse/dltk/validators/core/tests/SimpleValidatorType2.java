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
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.validators.core.tests;

public class SimpleValidatorType2 extends SimpleValidatorType {

	public static final String NATURE = "SimpleNature";

	@Override
	public String getID() {
		return super.getID() + "2";
	}

	@Override
	public String getNature() {
		return NATURE;
	}

}
