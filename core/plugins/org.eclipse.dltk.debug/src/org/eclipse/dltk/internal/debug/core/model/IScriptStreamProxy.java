/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.debug.core.model;

import java.io.InputStream;
import java.io.OutputStream;

public interface IScriptStreamProxy {
	InputStream getStdin();

	OutputStream getStdout();

	OutputStream getStderr();

	void close();

	void writeStdout(String value);

	void writeStderr(String value);

	String getEncoding();

	void setEncoding(String encoding);
}
