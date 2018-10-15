/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ui.text.folding;

import org.eclipse.dltk.core.IModelElement;

/**
 * Extends {@link IFoldingStructureProvider} with the following functions:
 * <ul>
 * <li>collapsing of comments and members</li>
 * <li>expanding and collapsing of certainscriptelements</li>
 * </ul>
 * 
 */
public interface IFoldingStructureProviderExtension {
	/**
	 * Collapses all members except for top level types.
	 */
	void collapseMembers();

	/**
	 * Collapses all comments.
	 */
	void collapseComments();

	void expandElements(IModelElement[] array);

	void collapseElements(IModelElement[] modelElements);
}
