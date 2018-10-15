/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
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
 * Selects a contributed extension implementation based upon the
 * <code>priority</code> it was registered with when the plugin containing its
 * extension definition was loaded.
 */
public class DLTKPriorityContributionSelector implements
		IDLTKContributionSelector {

	@Override
	public IDLTKContributedExtension select(
			IDLTKContributedExtension[] contributions, IProject project) {
		int maxPriority = Integer.MIN_VALUE;
		IDLTKContributedExtension selected = null;

		for (int i = 0; i < contributions.length; i++) {
			IDLTKContributedExtension contrib = contributions[i];

			/*
			 * if more then one contribution has the same priority, the first
			 * one found in the array wins
			 */
			if (contrib.getPriority() > maxPriority) {
				selected = contrib;
				maxPriority = contrib.getPriority();
			}
		}

		return selected;
	}
}
