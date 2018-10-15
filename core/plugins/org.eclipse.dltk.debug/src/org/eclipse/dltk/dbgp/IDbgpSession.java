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

import org.eclipse.dltk.dbgp.commands.IDbgpCommands;
import org.eclipse.dltk.dbgp.internal.IDbgpTermination;
import org.eclipse.dltk.dbgp.internal.managers.IDbgpStreamManager;
import org.eclipse.dltk.debug.core.IDebugConfigurable;

public interface IDbgpSession extends IDbgpCommands, IDbgpTermination,
		IDebugConfigurable {
	IDbgpSessionInfo getInfo();

	IDbgpStreamManager getStreamManager();

	IDbgpNotificationManager getNotificationManager();

	// Listeners
	void addRawListener(IDbgpRawListener listener);

	void removeRawListenr(IDbgpRawListener listener);

	IDbgpCommunicator getCommunicator();
}
