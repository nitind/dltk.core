/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ui.tests.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.tests.ScriptProjectHelper;
import org.eclipse.dltk.ui.tests.TestOptions;

import junit.extensions.TestSetup;
import junit.framework.Test;


public class ProjectTestSetup extends TestSetup {

	public static final String PROJECT_NAME= "TestSetupProject";
	
	public static IScriptProject getProject() {
		IProject project= ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		return  DLTKCore.create(project);
	}
		
	private IScriptProject fJProject;

	private boolean fAutobuilding;
	
	public ProjectTestSetup(Test test) {
		super(test);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		IScriptProject project= getProject();
		if (project.exists()) { // allow nesting of ProjectTestSetups
			return;
		}
		
		fAutobuilding = ScriptProjectHelper.setAutoBuilding(false);
		
		fJProject= ScriptProjectHelper.createScriptProject(PROJECT_NAME);		
		
		TestOptions.initializeProjectOptions(fJProject);
		
		DLTKCore.setOptions(TestOptions.getDefaultOptions());
		TestOptions.initializeCodeGenerationOptions();
		//DLTKUIPlugin.getDefault().getCodeTemplateStore().load();		
	}

	@Override
	protected void tearDown() throws Exception {
		if (fJProject != null) {
			ScriptProjectHelper.delete(fJProject);
			ScriptProjectHelper.setAutoBuilding(fAutobuilding);
		}
	}

}
