/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package org.eclipse.dltk.internal.ui;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.IScriptProject;


/**
 * An adapter factory for IScriptProjects.
 */
public class ScriptProjectAdapterFactory implements IAdapterFactory {
	
	private static Class<?>[] PROPERTIES = new Class[] {
		IProject.class,
	};
	
	@Override
	public Class<?>[] getAdapterList() {
		return PROPERTIES;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object element, Class<T> key) {
		if (IProject.class.equals(key)) {
			IScriptProject scriptProject= (IScriptProject)element;
			return (T) scriptProject.getProject();
		} 
		return null; 
	}
}
