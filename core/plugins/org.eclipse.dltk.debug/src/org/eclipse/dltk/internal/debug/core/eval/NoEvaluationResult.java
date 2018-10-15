/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.internal.debug.core.eval;

import org.eclipse.debug.core.DebugException;
import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.debug.core.eval.IScriptEvaluationResult;
import org.eclipse.dltk.debug.core.model.IScriptThread;
import org.eclipse.dltk.debug.core.model.IScriptValue;

public class NoEvaluationResult implements IScriptEvaluationResult {

	private final String snippet;
	private final IScriptThread thread;

	/**
	 * @param snippet
	 * @param thread
	 */
	public NoEvaluationResult(String snippet, IScriptThread thread) {
		this.snippet = snippet;
		this.thread = thread;
	}

	@Override
	public String[] getErrorMessages() {
		return CharOperation.NO_STRINGS;
	}

	@Override
	public DebugException getException() {
		return null;
	}

	@Override
	public String getSnippet() {
		return snippet;
	}

	@Override
	public IScriptThread getThread() {
		return thread;
	}

	@Override
	public IScriptValue getValue() {
		return null;
	}

	@Override
	public boolean hasErrors() {
		return false;
	}

}
