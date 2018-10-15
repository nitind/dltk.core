/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.console.ui;

import org.eclipse.dltk.console.IScriptExecResult;

public interface IScriptConsoleListener {
	void userRequest(String text);

	void interpreterResponse(IScriptExecResult text);
}
