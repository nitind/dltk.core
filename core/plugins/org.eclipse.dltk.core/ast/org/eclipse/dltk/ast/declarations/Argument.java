/*******************************************************************************
 * Copyright (c) 2002, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.ast.declarations;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.utils.CorePrinter;

public class Argument extends Declaration {

	protected ASTNode initialization;

	public Argument(DLTKToken name, int start, int end, ASTNode init) {
		super(name, start, end);
		this.initialization = init;
	}

	public Argument(SimpleReference name, int start, ASTNode init, int mods) {
		super(start, 0);

		if (name != null) {
			this.setName(name.getName());
			this.setEnd(start + name.getName().length());
		}
		this.modifiers = mods;
		this.initialization = init;
		if (init != null) {
			this.setEnd(init.sourceEnd());
		}
	}
	
	public Argument(SimpleReference name, int start, int end, ASTNode init, int mods) {
		super(start, 0);

		if (name != null) {
			this.setName(name.getName());
			this.setEnd(start + name.getName().length());
		}
		this.modifiers = mods;
		this.initialization = init;
		if (init != null) {
			this.setEnd(init.sourceEnd());
		}
	}

	public Argument() {
		super();
		this.setStart(0);
		this.setEnd(-1);
	}

	@Override
	public int getKind() {
		return D_ARGUMENT;
	}

	/**
	 * Please don't use this function. Helper method for initializing Argument
	 * 
	 */
	public final void set(SimpleReference mn, ASTNode initialization) {
		this.initialization = initialization;
//		this.setName(mn.getName());
		this.ref = mn;
		this.setStart(mn.sourceStart());
		this.setEnd(mn.sourceEnd());
	}

	public final ASTNode getInitialization() {
		return initialization;
	}

	public final void setInitializationExpression(ASTNode initialization) {
		this.initialization = initialization;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (initialization != null) {
				initialization.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(getName());
		if (initialization != null) {
			sb.append('=');
			sb.append(initialization);
		}
		return sb.toString();
	}

	@Override
	public void printNode(CorePrinter output) {
		output.formatPrint("Argument" + this.getSourceRange().toString() + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		output.formatPrintLn(super.toString());
	}

	public void setArgumentName(String name) {
		this.setName(name);
	}
}
