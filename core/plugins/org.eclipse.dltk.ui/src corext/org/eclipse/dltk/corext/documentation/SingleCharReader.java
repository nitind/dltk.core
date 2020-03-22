/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.corext.documentation;

import java.io.IOException;
import java.io.Reader;

public abstract class SingleCharReader extends Reader {

	@Override
	public abstract int read() throws IOException;

	@Override
	public int read(char cbuf[], int off, int len) throws IOException {
		int end = off + len;
		for (int i = off; i < end; i++) {
			int ch = read();
			if (ch == -1) {
				if (i == off) {
					return -1;
				}
				return i - off;
			}
			cbuf[i] = (char) ch;
		}
		return len;
	}

	@Override
	public boolean ready() throws IOException {
		return true;
	}

	/**
	 * Gets the content as a String
	 */
	public String getString() throws IOException {
		StringBuilder buf = new StringBuilder();
		int ch;
		while ((ch = read()) != -1) {
			buf.append((char) ch);
		}
		return buf.toString();
	}
}
