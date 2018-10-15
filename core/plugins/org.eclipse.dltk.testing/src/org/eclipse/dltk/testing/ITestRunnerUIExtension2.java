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
package org.eclipse.dltk.testing;

/**
 * Optional interface to be implemented by {@link ITestRunnerUI} if it wants to
 * take control over the stack traces copied to the clipboard.
 */
public interface ITestRunnerUIExtension2 {

	/**
	 * Performs filtering or whatever transformation is required with the stack
	 * trace before copying to clipboard.
	 */
	String prepareStackTraceCopy(String trace);

}
