/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;

public class SourceModuleElementInfo extends OpenableElementInfo {

	/**
	 * The length of this source module's source code <code>String</code>
	 */
	private int sourceLength;
	
	/** 
	 * Timestamp of original resource at the time this element
	 * was opened or last updated.
	 */
	protected long timestamp;
	
	/**
	 * Returns the length of the source string.
	 */
	public int getSourceLength() {
		return this.sourceLength;
	}
	
	protected ISourceRange getSourceRange() {
		return new SourceRange(0, this.sourceLength);
	}
	
	/**
	 * Sets the length of the source string.
	 */
	public void setSourceLength(int newSourceLength) {
		this.sourceLength = newSourceLength;
	}
	
}
