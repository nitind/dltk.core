/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.dbgp.internal;

public class DbgpTransactionManager {
	private static DbgpTransactionManager instance = new DbgpTransactionManager();

	public static DbgpTransactionManager getInstance() {
		return instance;
	}

	private final Object lock = new Object();

	private int id;

	private DbgpTransactionManager() {
		this.id = 0;
	}

	public int generateId() {
		synchronized (lock) {
			return id++;
		}
	}
}
