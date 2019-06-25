/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ui.tests;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;


public class TestOptions {
	
	public static Hashtable getDefaultOptions() {
		Hashtable result= DLTKCore.getDefaultOptions();	
		// should cover all compiler settings
		result.putAll(TestFormatterOptions.getSettings());
		return result;
	}
	
	public static void initializeCodeGenerationOptions() {
		IPreferenceStore store= DLTKUIPlugin.getDefault().getPreferenceStore();		
	}
	
	public static void initializeProjectOptions(IScriptProject project) {
		Map options= new HashMap();		
		project.setOptions(options);
	}
	
}

class TestFormatterOptions {
	
	public static Hashtable getSettings() {
		TestFormatterOptions options = new TestFormatterOptions();
		options.setDefaultSettings();
		return options.getMap();
	}

	public int tab_size;
	public final char filling_space = ' ';
	public int page_width;
	public boolean use_tab;

	public int initial_indentation_level;
	public String line_separator;
	
	private TestFormatterOptions() {
		// cannot be instantiated
	}

	private Hashtable getMap() {
		Hashtable options = new Hashtable();		
		return options;
	}

	private void setDefaultSettings() {

		this.tab_size = 4;
		this.page_width = 80;
		this.use_tab = true; // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=49081
	}
}

