/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.debug.dbgp.tests;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.eclipse.dltk.dbgp.IDbgpStackLevel;
import org.eclipse.dltk.dbgp.internal.DbgpStackLevel;
import org.junit.Before;
import org.junit.Test;

public class DbgpStackLevelTests {
	private static final String uri = "";

	private IDbgpStackLevel level;

	@Before
	public void setUp() throws Exception {
		level = new DbgpStackLevel(new URI(uri), "", 2, 56, 56, "", 0, 80);
	}
	@Test
	public void testEquals() throws Exception {
		IDbgpStackLevel l = new DbgpStackLevel(new URI(uri), "", 2, 56, 56, "", 0, 80);

		assertEquals(l, level);
	}
}
