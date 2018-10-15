/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.core.model.binary;

class BinaryFieldElementInfo extends BinaryMemberInfo {
	private String enumInitializerValue;
	private boolean isEnumValue;
	private String type;

	public boolean isEnumValue() {
		return isEnumValue;
	}

	public void setEnumValue(boolean isEnumValue) {
		this.isEnumValue = isEnumValue;
	}

	public String getEnumInitializerValue() {
		return enumInitializerValue;
	}

	public void setEnumInitializerValue(String enumInitializerValue) {
		this.enumInitializerValue = enumInitializerValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
