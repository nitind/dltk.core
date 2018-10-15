/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
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
package org.eclipse.dltk.ui.text.templates;

import org.eclipse.dltk.ui.templates.ScriptTemplateVariables;

public class SourceModuleTemplateContextType extends FileTemplateContextType {

	public SourceModuleTemplateContextType() {
		super();
		addScriptvariables();
	}

	/**
	 * Adds script template variable resolvers
	 */
	private void addScriptvariables() {
		addResolver(new ScriptTemplateVariables.Language());
		addResolver(new ScriptTemplateVariables.Interpreter());
	}

}
