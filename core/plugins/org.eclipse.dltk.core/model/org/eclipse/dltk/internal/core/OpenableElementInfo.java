/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.IModelElement;


/** Element info for IOpenable elements. */
public class OpenableElementInfo extends ModelElementInfo {

	/**
	 * Is the structure of this element known
	 * @see IModelElement#isStructureKnown()
	 */
	protected boolean isStructureKnown = false;

	/**
	 * @see IModelElement#isStructureKnown()
	 */
	public boolean isStructureKnown() {
		return this.isStructureKnown;
	}
	
	/**
	 * Sets whether the structure of this element known
	 * @see IModelElement#isStructureKnown()
	 */
	public void setIsStructureKnown(boolean newIsStructureKnown) {
		this.isStructureKnown = newIsStructureKnown;
	}
}
