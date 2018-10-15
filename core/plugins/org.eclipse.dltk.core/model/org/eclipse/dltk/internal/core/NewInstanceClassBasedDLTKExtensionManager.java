/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.PriorityClassDLTKExtensionManager;

public class NewInstanceClassBasedDLTKExtensionManager extends
		PriorityClassDLTKExtensionManager {

	public NewInstanceClassBasedDLTKExtensionManager(String extensionPoint) {
		super(extensionPoint);
	}

	public NewInstanceClassBasedDLTKExtensionManager(String extensionPoint,
			boolean initializeAtStartup) {
		super(extensionPoint);
		if( initializeAtStartup ) {
			initialize();
		}
	}

	@Override
	public Object getInitObject(ElementInfo ext) {
		try {
			if (ext != null) {
				IConfigurationElement cfg = ext.config;
				Object object = createObject(cfg);
				return object;
			}
		} catch (CoreException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
