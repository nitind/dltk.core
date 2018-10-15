/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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
package org.eclipse.dltk.internal.testing.model;

import org.eclipse.dltk.testing.model.ITestSuiteElement;

public class TestSuiteElement extends TestContainerElement implements
		ITestSuiteElement {

	/**
	 * @param parent
	 * @param id
	 * @param testName
	 * @param childrenCount
	 */
	public TestSuiteElement(TestContainerElement parent, String id,
			String testName, int childrenCount) {
		super(parent, id, testName, childrenCount);
	}

}
