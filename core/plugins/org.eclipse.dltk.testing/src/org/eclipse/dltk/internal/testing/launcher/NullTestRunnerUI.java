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
package org.eclipse.dltk.internal.testing.launcher;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.testing.AbstractTestRunnerUI;
import org.eclipse.dltk.testing.ITestRunnerUI;
import org.eclipse.dltk.testing.ITestingEngine;

public class NullTestRunnerUI extends AbstractTestRunnerUI {

	private NullTestRunnerUI() {
		// hidden constructor
	}

	private static ITestRunnerUI instance;

	public static ITestRunnerUI getInstance() {
		if (instance == null) {
			instance = new NullTestRunnerUI();
		}
		return instance;
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public IScriptProject getProject() {
		return null;
	}

	@Override
	public ITestingEngine getTestingEngine() {
		return null;
	}

}
