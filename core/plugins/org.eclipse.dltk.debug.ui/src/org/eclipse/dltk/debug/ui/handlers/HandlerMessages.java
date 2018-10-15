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
package org.eclipse.dltk.debug.ui.handlers;

import org.eclipse.osgi.util.NLS;

public class HandlerMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.debug.ui.handlers.HandlerMessages"; //$NON-NLS-1$
	public static String LaunchStatusDialog_commandLinePrompt;
	public static String LaunchStatusDialog_elapsedTimePrompt;
	public static String LaunchStatusDialog_message;
	/**
	 * @since 2.0
	 */
	public static String LaunchStatusDialog_message0;
	public static String LaunchStatusDialog_title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, HandlerMessages.class);
	}

	private HandlerMessages() {
	}
}
