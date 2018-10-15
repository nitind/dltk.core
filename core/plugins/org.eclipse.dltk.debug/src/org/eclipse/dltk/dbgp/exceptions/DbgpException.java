/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.dbgp.exceptions;

public class DbgpException extends Exception {

	private static final long serialVersionUID = 1L;

	public DbgpException() {
		super();
	}

	public DbgpException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbgpException(String message) {
		super(message);
	}

	public DbgpException(Throwable cause) {
		super(cause);
	}
}
