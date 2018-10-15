/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.dbgp.internal.managers;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.dltk.dbgp.IDbgpNotification;
import org.eclipse.dltk.dbgp.IDbgpNotificationListener;
import org.eclipse.dltk.dbgp.IDbgpNotificationManager;
import org.eclipse.dltk.dbgp.internal.DbgpNotification;
import org.eclipse.dltk.dbgp.internal.DbgpWorkingThread;
import org.eclipse.dltk.dbgp.internal.IDbgpDebugingEngine;
import org.eclipse.dltk.dbgp.internal.packets.DbgpNotifyPacket;

public class DbgpNotificationManager extends DbgpWorkingThread
		implements IDbgpNotificationManager {
	private final ListenerList<IDbgpNotificationListener> listeners = new ListenerList<>();

	private final IDbgpDebugingEngine engine;

	protected void fireDbgpNotify(IDbgpNotification notification) {
		for (IDbgpNotificationListener listener : listeners) {
			listener.dbgpNotify(notification);
		}
	}

	@Override
	protected void workingCycle() throws Exception {
		try {
			while (!Thread.interrupted()) {
				DbgpNotifyPacket packet = engine.getNotifyPacket();

				fireDbgpNotify(new DbgpNotification(packet.getName(),
						packet.getContent()));
			}
		} catch (InterruptedException e) {
			// OK, interrupted
		}
	}

	public DbgpNotificationManager(IDbgpDebugingEngine engine) {
		super("DBGP - Notification Manager"); //$NON-NLS-1$
		if (engine == null) {
			throw new IllegalArgumentException();
		}

		this.engine = engine;
	}

	@Override
	public void addNotificationListener(IDbgpNotificationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeNotificationListener(IDbgpNotificationListener listener) {
		listeners.remove(listener);
	}
}
