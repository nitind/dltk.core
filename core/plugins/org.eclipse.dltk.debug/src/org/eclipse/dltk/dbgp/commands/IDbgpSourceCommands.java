/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.dbgp.commands;

import java.net.URI;

import org.eclipse.dltk.dbgp.exceptions.DbgpException;

public interface IDbgpSourceCommands {
	String getSource(URI uri) throws DbgpException;

	String getSource(URI uri, int beginLine) throws DbgpException;

	String getSource(URI uri, int beginLine, int endLine) throws DbgpException;
}
