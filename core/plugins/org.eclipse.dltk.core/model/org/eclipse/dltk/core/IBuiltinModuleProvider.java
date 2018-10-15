/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.core;

/**
 * Used to provide builtin information into model.
 * 
 * @author Haiodo
 */
public interface IBuiltinModuleProvider {
	/**
	 * Used to builtin model contributions.
	 * 
	 * @return
	 */
	String[] getBuiltinModules();

	String getBuiltinModuleContent(String name);

	/**
	 * Returns the time that the content denoted by this provider was last
	 * modified.
	 * 
	 * @return
	 */
	long lastModified();
}
