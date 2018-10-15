/*******************************************************************************
 * Copyright (c) 2012 NumberFour AG
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
package org.eclipse.dltk.core.tests;

import java.io.File;

/**
 * Source workspace, containing initial project contents for the tests.
 * 
 * @see WorkspaceSetup
 */
public interface IWorkspaceSetup {

	void before() throws Throwable;

	/**
	 * Returns the bundle name this source workspace is contained in.
	 */
	String getBundleName();

	/**
	 * Returns the file system path to this source workspace.
	 */
	File getSourceWorkspaceDirectory();

	void after();

}
