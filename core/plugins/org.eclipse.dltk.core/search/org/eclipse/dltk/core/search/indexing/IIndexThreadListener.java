/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
