/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.internal.testing.launcher;

import org.eclipse.dltk.testing.AbstractTestingEngine;
import org.eclipse.dltk.testing.ITestingEngine;

public class NullTestingEngine extends AbstractTestingEngine {

	private NullTestingEngine() {
		// hidden constructor
	}

	private static ITestingEngine instance = null;

	public static ITestingEngine getInstance() {
		if (instance == null) {
			instance = new NullTestingEngine();
		}
		return instance;
	}

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public String getName() {
		final String fullName = getClass().getName();
		final int pos = fullName.lastIndexOf('.');
		return pos > 0 ? fullName.substring(pos + 1) : fullName;
	}

}
