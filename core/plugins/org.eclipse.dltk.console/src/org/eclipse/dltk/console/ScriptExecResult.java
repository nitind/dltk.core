/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.console;

public class ScriptExecResult implements IScriptExecResult {

	private final String output;
	private final boolean error;

	public ScriptExecResult(String output) {
		this(output, false);
	}

	/**
	 * @param output
	 * @param error
	 */
	public ScriptExecResult(String output, boolean error) {
		this.output = output;
		this.error = error;
	}

	@Override
	public String getOutput() {
		return output;
	}

	@Override
	public boolean isError() {
		return error;
	}

	@Override
	public String toString() {
		return output;
	}

}
