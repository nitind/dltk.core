/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptFileConfigurator;
import org.eclipse.dltk.utils.NatureExtensionManager;

/**
 * Manager of the {@link IScriptFileConfigurator} implementations.
 */
public class ScriptFileConfiguratorManager extends NatureExtensionManager {

	private static ScriptFileConfiguratorManager instance = null;

	private static final String EXT_POINT = DLTKCore.PLUGIN_ID
			+ ".scriptFileConfigurator"; //$NON-NLS-1$

	private ScriptFileConfiguratorManager() {
		super(EXT_POINT, IScriptFileConfigurator.class);
	}

	/**
	 * Returns array of the {@link IScriptFileConfigurator}s for the specified
	 * nature or <code>null</code>.
	 * 
	 * @param natureId
	 * @return
	 */
	public static IScriptFileConfigurator[] get(String natureId) {
		if (instance == null) {
			instance = new ScriptFileConfiguratorManager();
		}
		return (IScriptFileConfigurator[]) instance.getInstances(natureId);
	}

}
