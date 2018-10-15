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


/**
 * @since 2.0
 */
class BinaryTypeElementInfo extends BinaryMemberInfo {
	/**
	 * The name of the superclasses for this type.
	 */
	protected String[] superclassNames;

	protected void setSuperclassNames(String[] superclassNames) {
		this.superclassNames = superclassNames;
	}

	public String[] getSuperclassNames() {
		return superclassNames;
	}
}
