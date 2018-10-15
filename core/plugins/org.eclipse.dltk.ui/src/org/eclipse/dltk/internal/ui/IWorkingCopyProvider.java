/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.ui;

/**
 * Interface used for model element content providers to indicate that
 * the content provider can return working copy elements for members
 * below compilation units.
 * 
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 *
 */
public interface IWorkingCopyProvider {
	
	/**
	 * Returns <code>true</code> if the content provider returns working 
	 * copy elements; otherwise <code>false</code> is returned.
	 * 
	 * @return whether working copy elements are provided.
	 */
	public boolean providesWorkingCopies();
}
