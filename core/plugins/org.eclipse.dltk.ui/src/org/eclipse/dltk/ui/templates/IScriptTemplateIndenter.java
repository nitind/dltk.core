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
package org.eclipse.dltk.ui.templates;

/**
 * This interface is responsible to expand tabs in the templates to whatever is
 * configured in the editor.
 * 
 * The idea is that internally templates are created with tabs and those tabs
 * are expanded to spaces if needed in
 * {@link ScriptTemplateContext#evaluate(org.eclipse.jface.text.templates.Template)}
 */
public interface IScriptTemplateIndenter {

	/**
	 * @param sb
	 *            target buffer
	 * @param indent
	 *            indent of the current line
	 * @param line
	 *            next line from the template
	 */
	void indentLine(StringBuffer sb, String indent, String line);

}
