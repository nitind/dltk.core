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
 * This interface can be optionally implemented by {@link IBuildParticipant} to
 * be notified about dependencies - other {@link IBuildParticipant}s depending
 * on this one. Notification happens before the build, but after
 * {@link IBuildParticipantExtension#beginBuild(int)}.
 */
public interface IBuildParticipantExtension4 extends IBuildParticipant {

	/**
	 * Notifies this participant about dependents.
	 * 
	 * @param dependents
	 *            not null, not empty
	 */
	void notifyDependents(IBuildParticipant[] dependents);

	/**
	 * Is called after all participants were called for the module.
	 * 
	 * @param context
	 */
	void afterBuild(IBuildContext context);

}
