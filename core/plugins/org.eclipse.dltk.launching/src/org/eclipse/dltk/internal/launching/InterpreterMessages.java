/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.launching;

import org.eclipse.osgi.util.NLS;

public class InterpreterMessages extends NLS {
	private InterpreterMessages() {
	}

	private static final String BUNDLE_NAME = "org.eclipse.dltk.internal.launching.InterpreterMessages";//$NON-NLS-1$

	static {
		NLS.initializeMessages(BUNDLE_NAME, InterpreterMessages.class);
	}

	public static String DebuggingEngineRunner_launching;
	public static String DebuggingEngineRunner_running;
	public static String errNonExistentOrInvalidInstallLocation;
	public static String errNoInterpreterExecutablesFound;
	public static String statusFetchingLibs;

	public static String errDbgpServiceNotAvailable;
	public static String errDebuggingEngineNotStarted;
	public static String errDebuggingEngineNotConnected;
	public static String errDebuggingEngineNotInitialized;
	public static String errDebuggingEnginePathNotSpecified;
	public static String errDebuggingEnginePathInvalid;
	public static String errDebuggingEngineWorkingDirectoryDoesntExist;
	public static String errDebuggingEngineScriptFileDoesntExist;
}
