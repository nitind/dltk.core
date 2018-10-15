/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.eclipse.dltk.ui.DLTKUIPlugin;


public class IOCloser {
	public static void perform(Reader reader, InputStream stream) {
		try {
			rethrows(reader, stream);
		} catch (IOException e) {
			DLTKUIPlugin.log(e);
		}
	}
	
	public static void rethrows(Reader reader, InputStream stream) throws IOException {
		if (reader != null) {
			reader.close();
			return;
		}
		if (stream != null) {
			stream.close();
			return;
		}
	}	
}

