/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.search.indexing;

/**
 * Implementors of this interface are notified about the index manager thread
 * state changes.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IIndexThreadListener {

	/**
	 * Index manager thread is about to be put in an idle state.
	 */
	public void aboutToBeIdle();

	/**
	 * Index manager thread was awaken and is about to be run.
	 *
	 * @param idlingTime
	 *            The time of being in idle state before being awaken
	 */
	public void aboutToBeRun(long idlingTime);

}
