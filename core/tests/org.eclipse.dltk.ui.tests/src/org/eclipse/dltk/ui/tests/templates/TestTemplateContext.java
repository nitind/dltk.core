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
package org.eclipse.dltk.ui.tests.templates;

import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.jface.text.Document;

class TestTemplateContext extends ScriptTemplateContext {

	private TestTemplateContext() {
		super(null, null, 0, 0, null);
	}

	public static String calculateIndent(String line) {
		return calculateIndent(new Document(line), line.length());
	}

}
