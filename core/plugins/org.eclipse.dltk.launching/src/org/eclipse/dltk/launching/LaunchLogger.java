/*******************************************************************************
 * Copyright (c) 2009, 2016 xored software, Inc.  and others.
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
package org.eclipse.dltk.launching;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.eclipse.dltk.core.environment.IExecutionLogger;

import com.ibm.icu.text.SimpleDateFormat;

class LaunchLogger implements IExecutionLogger {

	private final String fileName;

	public LaunchLogger() {
		fileName = new SimpleDateFormat("yyyy-MM-dd-HHmm").format(new Date())
				+ ".log";
	}

	@Override
	public void logLine(String line) {
		final File file = new File(System.getProperty("user.home"), fileName);
		try (final FileWriter writer = new FileWriter(file, true)) {
			writer.write(line);
			writer.write("\n");
		} catch (IOException e) {
			// ignore?
		}
	}
}
