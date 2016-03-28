/*******************************************************************************
 * Copyright (c) 2002, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.ast.references;

import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.utils.CorePrinter;

public class SimpleReference extends Reference {

	protected String fName;

	public SimpleReference(int start, int end, String name) {
		super(start, end);
		this.fName = name;
	}

	public SimpleReference(DLTKToken token) {
		this.fName = token.getText();
		this.setStart(token.getColumn());
		if (fName != null) {
			this.setEnd(this.sourceStart() + this.fName.length());
		}
	}

	public String getName() {
		return fName;
	}

	public void setName(String name) {
		this.fName = name;
	}

	@Override
	public String getStringRepresentation() {
		return fName;
	}

	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn(this.fName + "(" + this.getSourceRange().toString() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String toString() {
		return this.fName;
	}
}
