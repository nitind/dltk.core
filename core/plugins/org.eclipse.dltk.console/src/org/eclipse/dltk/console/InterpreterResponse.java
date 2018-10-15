/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.console;

public class InterpreterResponse {
	private final int state;
	private final String content;
	private final boolean isError;

	public InterpreterResponse(int state, boolean isError, String content) {
		this.state = state;
		this.isError = isError;
		this.content = content;
	}

	public int getState() {
		return state;
	}

	public boolean isError() {
		return isError;
	}

	public String getContent() {
		return content;
	}
}
