/*******************************************************************************
 * Copyright (c) 2002, 2016 IBM Corporation and others.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.internal.compiler.lookup.MethodScope;
import org.eclipse.dltk.utils.CorePrinter;

public class MethodDeclaration extends Declaration {
	public MethodScope scope;

	protected List arguments = new ArrayList();

	private Block body = new Block();

	private List decorators;

	private String declaringTypeName;

	public MethodDeclaration(DLTKToken function_t, DLTKToken name) {

		super(name, function_t.getColumn(), name.getColumn()
				+ name.getText().length());
	}

	public MethodDeclaration(String name, int nameStart, int nameEnd,
			int declStart, int declEnd) {
		super(declStart, declEnd);
		this.setName(name);
		this.setNameStart(nameStart);
		this.setNameEnd(nameEnd);
	}

	public MethodDeclaration(int start, int end) {
		super(start, end);
	}

	public void setDecorators(List decorators) {
		this.decorators = decorators;
	}

	public List getDecorators() {
		return this.decorators;
	}

	@Override
	public int getKind() {
		return D_METHOD;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {

		if (visitor.visit(this)) {
			traverseChildNodes(visitor);
			visitor.endvisit(this);
		}
	}

	protected void traverseChildNodes(ASTVisitor visitor) throws Exception {
		// Decorators
		if (this.decorators != null) {
			Iterator it = this.decorators.iterator();
			while (it.hasNext()) {
				Decorator dec = (Decorator) it.next();
				dec.traverse(visitor);
			}
		}

		// Arguments
		if (this.arguments != null) {
			Iterator it = this.arguments.iterator();
			while (it.hasNext()) {
				Argument arg = (Argument) it.next();
				arg.traverse(visitor);
			}
		}

		// Body
		if (this.body != null) {
			this.body.traverse(visitor);
		}
	}

	public List getArguments() {
		return this.arguments;
	}

	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}

	public void acceptArguments(List arguments) {
		this.arguments = arguments;
	}

	public void acceptBody(Block block) {
		this.acceptBody(block, true);
	}

	public void setBody(ASTListNode statement) {
		Block b = new Block(statement.sourceStart(), statement.sourceEnd());
		b.acceptStatements(statement.getChilds());
		this.acceptBody(b, true);
	}

	public void acceptBody(Block block, boolean replace) {
		this.body = block;

		if (block != null) {
			if (replace) {
				this.setEnd(block.sourceEnd());
			}
		}
	}

	public List getStatements() {
		if (this.body == null) {
			this.body = new Block(this.sourceStart(), this.sourceEnd());
		}
		return this.body.getStatements();
	}

	public Block getBody() {
		return this.body;
	}

	@Override
	public void printNode(CorePrinter output) {
		if (this.decorators != null) {
			Iterator i = this.decorators.iterator();
			while (i.hasNext()) {
				((Decorator) i.next()).printNode(output);
			}
		}
		output.formatPrintLn("Method" + this.getSourceRange().toString() //$NON-NLS-1$
				+ this.getNameSourceRange().toString() + ": " //$NON-NLS-1$
				+ super.toString());
		output.formatPrintLn("("); //$NON-NLS-1$
		if (this.arguments != null && this.arguments.size() > 0) {
			boolean first = true;
			Iterator i = this.arguments.iterator();
			while (i.hasNext()) {
				Argument argument = (Argument) i.next();
				if (!first) {
					output.formatPrintLn(", "); //$NON-NLS-1$
				} else {
					first = false;
				}
				argument.printNode(output);
			}
		}
		output.formatPrintLn(")"); //$NON-NLS-1$
		if (this.body != null) {
			this.body.printNode(output);
		}
	}

	public void setDeclaringTypeName(String name) {
		if (name != null && name.length() > 0) {
			this.declaringTypeName = name;
		}
	}

	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}

	@Override
	public int matchStart() {
		return getNameStart();
	}

	@Override
	public int matchLength() {
		return getNameEnd() - getNameStart();
	}
}
