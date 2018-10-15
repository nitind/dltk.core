/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.dltk.internal.testing.model;

import org.eclipse.core.runtime.Assert;

import org.eclipse.dltk.testing.model.ITestCaseElement;

public class TestCaseElement extends TestElement implements ITestCaseElement {

	private boolean fIgnored;

	public TestCaseElement(TestContainerElement parent, String id, String testName) {
		super(parent, id, testName);
		Assert.isNotNull(parent);
	}

	public void setIgnored(boolean ignored) {
		fIgnored = ignored;
	}

	public boolean isIgnored() {
		return fIgnored;
	}

	@Override
	public String toString() {
		return "TestCase: " + getTestName() + " : " + super.toString(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @param value
	 */
	@Override
	public void setTestName(String value) {
		super.setTestName(value);
	}
}
