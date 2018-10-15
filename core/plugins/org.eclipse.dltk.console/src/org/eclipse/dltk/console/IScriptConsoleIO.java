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

import java.io.IOException;
import java.io.InputStream;

public interface IScriptConsoleIO {
	String getId();

	InterpreterResponse execInterpreter(String command) throws IOException;

	ShellResponse execShell(String command, String[] args) throws IOException;

	void close() throws IOException;

	InputStream getInitialResponseStream();
}
