/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.console.ui.internal;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.IPatternMatchListenerDelegate;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;

public class HTTPConsolePatternMatcher
		implements IPatternMatchListenerDelegate {
	private TextConsole textConsole;

	@Override
	public void connect(TextConsole console) {
		this.textConsole = console;
	}

	@Override
	public void disconnect() {
	}

	@Override
	public void matchFound(PatternMatchEvent event) {
		IHyperlink link = new HTTPConsoleHyperlink(textConsole);
		try {
			textConsole.addHyperlink(link, event.getOffset(),
					event.getLength());
		} catch (BadLocationException e) {
		}
	}
}
