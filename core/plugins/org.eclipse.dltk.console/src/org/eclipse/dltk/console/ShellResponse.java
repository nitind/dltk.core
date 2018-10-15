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

import java.util.List;

public class ShellResponse {
	private List completions;

	private String description;

	public ShellResponse() {

	}

	public ShellResponse(String description) {
		this.description = description;
	}

	public ShellResponse(List completions) {
		this.completions = completions;
	}

	public List getCompletions() {
		return completions;
	}

	public String getDescription() {
		return this.description;
	}
}
