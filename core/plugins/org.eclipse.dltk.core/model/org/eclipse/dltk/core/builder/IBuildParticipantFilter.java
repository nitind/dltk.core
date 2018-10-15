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

/**
 * Predicate for checking if the specified {@link IBuildParticipant} should be
 * called for the specified file.
 * 
 * Created via {@link IBuildParticipantFilterFactory}
 * 
 * @since 4.0
 */
public interface IBuildParticipantFilter {

	/**
	 * Filters the array of {@link IBuildParticipant}s and returns only allowed
	 * ones.
	 * 
	 * @param participants
	 * @param context
	 * @return
	 */
	IBuildParticipant[] filter(IBuildParticipant[] participants,
			IBuildContext context);

}
