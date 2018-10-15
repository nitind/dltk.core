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

public interface IScriptConsoleInterpreter {
	int WAIT_NEW_COMMAND = 0;
	int WAIT_CONTINUE_COMMAND = 1;
	int WAIT_USER_INPUT = 2;

	IScriptExecResult exec(String command) throws IOException;

	int getState();
}
