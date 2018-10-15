/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.dbgp.internal.breakpoints;

public class DbgpReturnBreakpoint extends DbgpBreakpoint {

	private final String function;

	public DbgpReturnBreakpoint(String id, boolean enabled, int hitValue,
			int hitCount, String hitCondition, String function) {
		super(id, enabled, hitValue, hitCount, hitCondition);
		this.function = function;
	}

	public String getFunction() {
		return function;
	}
}
