/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
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
package org.eclipse.dltk.launching;

import java.util.List;

import org.eclipse.dltk.core.IBuildpathEntry;

/**
 * @since 2.0
 */
public interface IInterpreterContainerExtension2 extends
		IInterpreterContainerExtension {

	/**
	 * This method is called to customize {@link IBuildpathEntry
	 * IBuildpathEntries} for the {@link IInterpreterInstall}.
	 * 
	 * It's called once per interpreter.
	 * 
	 * Entries are initialized from the {@link LibraryLocation}s returned by the
	 * interpreter.
	 */
	void preProcessEntries(IInterpreterInstall interpreter,
			List<IBuildpathEntry> entries);
}
