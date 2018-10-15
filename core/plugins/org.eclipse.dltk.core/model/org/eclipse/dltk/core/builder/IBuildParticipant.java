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
package org.eclipse.dltk.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.core.ISourceModule;

public interface IBuildParticipant {

	/**
	 * Validates the specified <code>ISourceModule</code> or its AST and reports
	 * any problems.
	 * 
	 * <p>
	 * The {@link ISourceModule}, {@link IProblemReporter} and
	 * {@link ISourceLineTracker} are all available via the build context.
	 * </p>
	 * 
	 * @param context
	 *            build context
	 * 
	 * @see org.eclipse.dltk.utils.TextUtils#trimWhitespace(String,
	 *      org.eclipse.dltk.core.ISourceRange)
	 */
	void build(IBuildContext context) throws CoreException;

}
