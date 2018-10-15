/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/

package org.eclipse.dltk.internal.ui.search;

import org.eclipse.dltk.core.IModelElement;

public class DLTKElementLine {
	private IModelElement fElement;
	private int fLine;
	private String fLineContents;
	
	/**
	 * @param element either an ISourceModule or an IClassFile
	 */
	public DLTKElementLine(IModelElement element, int line, String lineContents) {
		fElement= element;
		fLine= line;
		fLineContents= lineContents;
	}
	
	public IModelElement getModelElement() {
		return fElement;
	}
	
	public int getLine() {
		return fLine;
	}
	
	public String getLineContents() {
		return fLineContents;
	}
}
