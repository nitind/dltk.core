/*******************************************************************************
 * Copyright (c) 2010, 2017 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.internal.logconsole;

import org.eclipse.dltk.logconsole.ILogCategory;
import org.eclipse.dltk.logconsole.ILogConsoleStream;
import org.eclipse.dltk.logconsole.LogConsoleType;
import org.eclipse.dltk.logconsole.impl.AbstractLogConsole;

public class NopLogConsole extends AbstractLogConsole {

	protected NopLogConsole(LogConsoleType consoleType, Object identifier) {
		super(consoleType, identifier);
	}

	@Override
	public void println(ILogConsoleStream stream, Object message) {
	}

	@Override
	public void println(ILogCategory category, Object message) {
	}

}
