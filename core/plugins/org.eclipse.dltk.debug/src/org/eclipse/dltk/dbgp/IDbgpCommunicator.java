/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.dbgp;

import org.eclipse.dltk.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.debug.core.IDebugConfigurable;
import org.w3c.dom.Element;

public interface IDbgpCommunicator extends IDebugConfigurable {
	Element communicate(DbgpRequest request) throws DbgpException;

	void send(DbgpRequest request) throws DbgpException;
}
