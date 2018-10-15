/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.dbgp.internal.breakpoints;

import org.eclipse.dltk.dbgp.breakpoints.IDbgpLineBreakpoint;

public class DbgpLineBreakpoint extends DbgpBreakpoint
		implements IDbgpLineBreakpoint {
	private final String fileName;

	private final int lineNumber;

	public DbgpLineBreakpoint(String id, boolean enabled, int hitValue,
			int hitCount, String hitCondition, String fileName,
			int lineNumber) {
		super(id, enabled, hitValue, hitCount, hitCondition);

		this.fileName = fileName;
		this.lineNumber = lineNumber;
	}

	@Override
	public String getFilename() {
		return fileName;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}
}
