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

public class DbgpProtocolException extends DbgpException {

	private static final long serialVersionUID = 1L;

	public DbgpProtocolException() {
		super();
	}

	public DbgpProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbgpProtocolException(String message) {
		super(message);
	}

	public DbgpProtocolException(Throwable cause) {
		super(cause);
	}
}
