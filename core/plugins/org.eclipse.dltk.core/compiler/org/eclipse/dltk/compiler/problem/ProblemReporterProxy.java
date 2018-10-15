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
package org.eclipse.dltk.compiler.problem;

import org.eclipse.core.runtime.IAdaptable;

/**
 * The {@link IProblemReporter} implementation which forwards all methods call
 * to the instance passed in the constructor.
 */
public class ProblemReporterProxy implements IProblemReporter, IAdaptable {

	private final IProblemReporter original;

	/**
	 * @param original
	 */
	protected ProblemReporterProxy(IProblemReporter original) {
		this.original = original;
	}

	@Override
	public void reportProblem(IProblem problem) {
		if (original != null) {
			original.reportProblem(problem);
		}
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (original != null && original instanceof IAdaptable) {
			return ((IAdaptable) original).getAdapter(adapter);
		}
		return null;
	}

}
