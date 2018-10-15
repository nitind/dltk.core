/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core;

import org.eclipse.core.resources.IProject;

/**
 * Provides an interface to select between multiple implementations of a
 * contributed extension point.
 */
public interface IDLTKContributionSelector {

	/**
	 * Select a contribution implementation
	 * 
	 * <p>
	 * To select a project specific resource, pass an instance of the desired
	 * project, otherwise, specific <code>null</code>.
	 * 
	 * @param contributions
	 *            list of contribution implementations
	 * 
	 * @param project
	 *            project reference or <code>null</code>
	 * 
	 * @return contribution
	 */
	IDLTKContributedExtension select(IDLTKContributedExtension[] contributions,
			IProject project);
}
