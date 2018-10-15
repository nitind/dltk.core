/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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

import org.eclipse.dltk.testing.model.ITestCategoryElement;

public class TestCategoryElement extends TestContainerElement implements
		ITestCategoryElement {

	/**
	 * @param parent
	 * @param id
	 * @param testName
	 */
	public TestCategoryElement(TestContainerElement parent, String id,
			String testName) {
		super(parent, id, testName);
	}

	@Override
	public String getCategoryName() {
		return getTestName();
	}

}
