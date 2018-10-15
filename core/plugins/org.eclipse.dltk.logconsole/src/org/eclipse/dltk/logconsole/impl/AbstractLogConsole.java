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
package org.eclipse.dltk.logconsole.impl;

import org.eclipse.dltk.logconsole.ILogConsole;
import org.eclipse.dltk.logconsole.LogConsoleType;

/**
 * Abstract base class of the {@link ILogConsole} implementations.
 */
public abstract class AbstractLogConsole implements ILogConsole {

	private final LogConsoleType consoleType;
	private final Object identifier;

	protected AbstractLogConsole(LogConsoleType consoleType, Object identifier) {
		this.consoleType = consoleType;
		this.identifier = identifier;
	}

	@Override
	public LogConsoleType getConsoleType() {
		return consoleType;
	}

	@Override
	public Object getIdentifier() {
		return identifier;
	}

	@Override
	public void println(Object message) {
		println(STDOUT, message);
	}

	@Override
	public void activate() {
	}

}
