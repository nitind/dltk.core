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

import org.eclipse.dltk.dbgp.breakpoints.IDbgpExceptionBreakpoint;

public class DbgpExceptionBreakpoint extends DbgpBreakpoint
		implements IDbgpExceptionBreakpoint {

	private final String exception;

	public DbgpExceptionBreakpoint(String id, boolean enabled, int hitValue,
			int hitCount, String hitCondition, String exception) {
		super(id, enabled, hitValue, hitCount, hitCondition);
		this.exception = exception;
	}

	@Override
	public String getException() {
		return exception;
	}
}
