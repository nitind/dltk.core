/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.environment;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.core.environment.messages"; //$NON-NLS-1$
	public static String EnvironmentManager_initializingOperationName;
	public static String EnvironmentManager_initializingTaskName;
	/**
	 * @since 2.0
	 */
	public static String EnvironmentManager_RefreshProjectInterpreter;
	public static String EnvironmentPathUtils_invalidPath;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
