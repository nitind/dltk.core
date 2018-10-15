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
package org.eclipse.dltk.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;

/**
 * Contributed via <code>org.eclipse.dltk.core.buildParticipant/predicate</code>
 * extension point.
 * 
 * @since 4.0
 */
public interface IBuildParticipantFilterFactory {

	/**
	 * Creates the predicate for checking if each {@link IBuildParticipant}
	 * should be called for the file or not. Returns <code>null</code> if no
	 * predicate is required for the project.
	 * 
	 * @param project
	 * @param context
	 * @return
	 * @throws CoreException
	 */
	IBuildParticipantFilter createPredicate(IScriptProject project,
			Object context) throws CoreException;

}
