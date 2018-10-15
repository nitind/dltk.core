/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.  
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
package org.eclipse.dltk.console.ui;

import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.ui.console.IConsole;

/**
 * @since 2.0
 */
public interface IScriptConsole extends IConsole {

	void connect(IStreamsProxy proxy);

	void insertText(String line);

	void terminate();

}
