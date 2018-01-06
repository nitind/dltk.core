/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ui.tests.navigator;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

public class TestScriptNavigatorLabelProvider extends
		ScriptExplorerLabelProvider {

	public TestScriptNavigatorLabelProvider(ScriptExplorerContentProvider cp,
			IPreferenceStore store) {
		super(cp, store);
	}

	protected IPreferenceStore getPreferenceStare() {
		return DLTKUIPlugin.getDefault().getPreferenceStore();
	}

	protected ScriptExplorerLabelProvider createLabelProvider() {
		{
			return new ScriptExplorerLabelProvider(fContentProvider,
					getPreferenceStare());
		}
	}
}
