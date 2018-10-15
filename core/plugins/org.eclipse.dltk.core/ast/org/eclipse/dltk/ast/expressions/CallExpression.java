/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ast.expressions;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.utils.CorePrinter;

public class CallExpression extends Expression {
	protected ASTNode receiver;
	private SimpleReference name;
	
	private CallArgumentsList args;
	
	public CallExpression(int start, int end, ASTNode receiver, String name, CallArgumentsList args) {
		super(start, end);
		if (name == null){
			throw new IllegalArgumentException();
		}
		
		if (args == null) {
//			throw new IllegalArgumentException();
			args = new CallArgumentsList();
		}
		
		this.receiver = receiver;
		this.name = new SimpleReference( start, end, name );
		this.args = args;
	}
	public CallExpression(int start, int end, ASTNode receiver, SimpleReference name, CallArgumentsList args) {
		super(start, end);
		if (name == null){
			throw new IllegalArgumentException();
		}
		
		if (args == null) {
//			throw new IllegalArgumentException();
			args = new CallArgumentsList();
		}
		
		this.receiver = receiver;
		this.name = name;
		this.args = args;
	}
	public CallExpression(ASTNode receiver, String name, CallArgumentsList args) {
		this(0, 0, receiver, name, args );
	}

	@Override
	public int getKind() {		
		return 0;
	}

	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if( pVisitor.visit( this ) ) {
			if( receiver != null ) {
				receiver.traverse( pVisitor );
			}
			
			if( args != null ) {
				args.traverse( pVisitor );
			}
			pVisitor.endvisit( this );
		}
	}
	
	public ASTNode getReceiver() {
		return receiver;
	}
	
	public String getName() {
		return name.getName();
	}
	public SimpleReference getCallName() {
		return this.name;
	}
	
	public CallArgumentsList getArgs () {
		return args;
	}
	
	@Override
	public void printNode(CorePrinter output) {
		output.formatPrint("CallExpression" + this.getSourceRange().toString() + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		if (this.receiver != null) {
			output.formatPrint("{"); //$NON-NLS-1$
			this.receiver.printNode(output);
			output.formatPrint("}."); //$NON-NLS-1$
		}
		output.formatPrint(this.getName() + "("); //$NON-NLS-1$
		if (this.getArgs() != null) {
			this.getArgs().printNode(output);
		}
		output.formatPrintLn(")"); //$NON-NLS-1$
	}

}
