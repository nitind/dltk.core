/*******************************************************************************
 * Copyright (c) 2008, 2017 Jae Gangemi and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.dltk.ui.tests.core;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.tests.model.TestLanguageToolkit;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.tests.DLTKUITestsPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

public class DLTKUITestLanguageToolkit extends AbstractDLTKUILanguageToolkit {

	@Override
	public IPreferenceStore getPreferenceStore() {
		return DLTKUITestsPlugin.getDefault().getPreferenceStore();
	}

	@Override
	public IDLTKLanguageToolkit getCoreToolkit() {
		return TestLanguageToolkit.getDefault();
	}

}
