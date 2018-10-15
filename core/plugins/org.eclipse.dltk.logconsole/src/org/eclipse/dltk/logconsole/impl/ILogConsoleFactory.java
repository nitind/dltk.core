/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
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
 * The factory interface to instantiate {@link ILogConsole}s
 */
public interface ILogConsoleFactory {

	ILogConsole create(LogConsoleType consoleType, Object identifier);

}
