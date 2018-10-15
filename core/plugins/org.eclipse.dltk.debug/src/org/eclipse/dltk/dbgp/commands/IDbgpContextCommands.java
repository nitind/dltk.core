/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.dbgp.commands;

import java.util.Map;

import org.eclipse.dltk.dbgp.IDbgpProperty;
import org.eclipse.dltk.dbgp.exceptions.DbgpException;

public interface IDbgpContextCommands {
	int LOCAL_CONTEXT_ID = 0;
	int GLOBAL_CONTEXT_ID = 1;
	int CLASS_CONTEXT_ID = 2;

	Map<Integer, String> getContextNames(int stackDepth) throws DbgpException;

	IDbgpProperty[] getContextProperties(int stackDepth) throws DbgpException;

	IDbgpProperty[] getContextProperties(int stackDepth, int contextId)
			throws DbgpException;
}
