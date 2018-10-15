/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.dbgp.internal;

import org.eclipse.dltk.dbgp.IDbgpNotification;
import org.w3c.dom.Element;

public class DbgpNotification implements IDbgpNotification {
	private final Element body;

	private final String name;

	public DbgpNotification(String name, Element body) {
		this.body = body;
		this.name = name;
	}

	@Override
	public Element getBody() {
		return body;
	}

	@Override
	public String getName() {
		return name;
	}

}
