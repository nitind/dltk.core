/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.compiler;

/*
 * Handler policy is responsible to answer the 2 following
 * questions:
 * 1. should the handler stop on first problem which appears
 *	to be a real error (that is, not a warning),
 * 2. should it proceed once it has gathered all problems
 *
 * The intent is that one can supply its own policy to implement 
 * some interactive error handling strategy where some UI would 
 * display problems and ask user if he wants to proceed or not.
 */

public interface IErrorHandlingPolicy {
	boolean proceedOnErrors();
	boolean stopOnFirstError();
}
