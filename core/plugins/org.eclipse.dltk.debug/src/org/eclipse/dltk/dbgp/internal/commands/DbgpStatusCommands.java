/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.dbgp.internal.commands;

import org.eclipse.dltk.dbgp.DbgpBaseCommands;
import org.eclipse.dltk.dbgp.IDbgpCommunicator;
import org.eclipse.dltk.dbgp.IDbgpStatus;
import org.eclipse.dltk.dbgp.commands.IDbgpStatusCommands;
import org.eclipse.dltk.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.dbgp.internal.utils.DbgpXmlEntityParser;

public class DbgpStatusCommands extends DbgpBaseCommands
		implements IDbgpStatusCommands {
	private static final String STATUS_COMMAND = "status"; //$NON-NLS-1$

	public DbgpStatusCommands(IDbgpCommunicator communicator) {
		super(communicator);
	}

	@Override
	public IDbgpStatus getStatus() throws DbgpException {
		return DbgpXmlEntityParser
				.parseStatus(communicate(createAsyncRequest(STATUS_COMMAND)));
	}
}
