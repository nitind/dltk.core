/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.core.mixin;

import org.eclipse.dltk.core.ISourceModule;

public interface IMixinElement {
	/**
	 * Return all children elements.
	 * If not builded then builded.
	 * @return children elements.
	 */
	IMixinElement[] getChildren();
	
	/**
	 * Add name to current key and return child.
	 * @param name
	 * @return
	 */
	IMixinElement getChildren( String name );
	
	/**
	 * Return key value.
	 * @return key value
	 */
	String getKey();
	
	/**
	 * Return last key segment.
	 * @return
	 */
	String getLastKeySegment();
	
	/**
	 * Return sepecified objects.
	 * If not builded then builded.
	 * @param module
	 * @return
	 */
	Object[] getObjects(ISourceModule module);
	
	/**
	 * Returns all not equal objects.
	 * If not builded then builded.
	 * @return
	 */
	Object[] getAllObjects();
	
	/**
	 * Return parent mixin. Parent mixin aren't builded. 
	 * @return
	 */
	IMixinElement getParent();
	
	/**
	 * If not builded then builded.
	 * @return
	 */
	ISourceModule[] getSourceModules();
}
