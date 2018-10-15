/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.editor;

import org.eclipse.dltk.core.ISourceModule;


public interface ISavePolicy {

	/**
	 *
	 */
	void preSave(ISourceModule unit);

	/**
	 * Returns the compilation unit in which the argument
	 * has been changed. If the argument is not changed, the
	 * returned result is <code>null</code>.
	 */
	ISourceModule postSave(ISourceModule unit);
}

