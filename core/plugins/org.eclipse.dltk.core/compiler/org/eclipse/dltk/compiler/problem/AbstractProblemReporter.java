/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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
package org.eclipse.dltk.compiler.problem;

import org.eclipse.core.runtime.IAdaptable;

/**
 * The abstract implementation of {@link IProblemReporter} to simplify code in
 * tests, etc.
 */
public abstract class AbstractProblemReporter implements IProblemReporter,
		IAdaptable {

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

}
