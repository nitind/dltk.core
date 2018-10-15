/*******************************************************************************
 * Copyright (c) 2011, 2017 NumberFour AG
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.logconsole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompoundMessage {
	private final Object header;

	private final List<Object> contents = new ArrayList<>();

	public CompoundMessage(Object header) {
		this.header = header;
	}

	public CompoundMessage(Object header, Object[] messages) {
		this(header);
		addAll(messages);
	}

	public void add(Object message) {
		contents.add(message);
	}

	public void addAll(Object[] messages) {
		if (messages != null && messages.length != 0) {
			Collections.addAll(this.contents, messages);
		}
	}

	public Object getHeader() {
		return header;
	}

	public List<Object> getContents() {
		return contents;
	}

}
