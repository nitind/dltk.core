/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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
 * Implementation of the {@link IScriptTemplateIndenter} which passes original
 * lines without modifications.
 */
public class NopScriptTemplateIndenter implements IScriptTemplateIndenter {

	@Override
	public void indentLine(StringBuffer sb, String indent, String line) {
		sb.append(indent);
		sb.append(line);
	}
}
