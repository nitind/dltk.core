/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.testing.model;

/**
 * Represents a test suite element.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * 
 * @since 3.3
 */
public interface ITestSuiteElement extends ITestElementContainer {
	
	/**
	 * Returns the qualified type name of the suite class
	 * 
	 * @return the qualified type name of the suite class
	 */
	public String getSuiteTypeName();
	
}
