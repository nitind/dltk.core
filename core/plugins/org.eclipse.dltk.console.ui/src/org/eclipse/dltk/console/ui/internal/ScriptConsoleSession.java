/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.console.ui.internal;

import org.eclipse.dltk.console.IScriptExecResult;
import org.eclipse.dltk.console.ui.IScriptConsoleListener;
import org.eclipse.dltk.console.ui.IScriptConsoleSession;

public class ScriptConsoleSession
		implements IScriptConsoleListener, IScriptConsoleSession {
	private StringBuffer session;

	public ScriptConsoleSession() {
		this.session = new StringBuffer();
	}

	@Override
	public void interpreterResponse(IScriptExecResult text) {
		if (text != null) {
			session.append("> "); //$NON-NLS-1$
			session.append(text.getOutput());
		}
	}

	@Override
	public void userRequest(String text) {
		session.append("< "); //$NON-NLS-1$
		session.append(text);
		session.append('\n');
	}

	@Override
	public String toString() {
		return session.toString();
	}
}
