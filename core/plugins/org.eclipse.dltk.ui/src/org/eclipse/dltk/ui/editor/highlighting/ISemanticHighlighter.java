/*******************************************************************************
 * Copyright (c) 2011 xored software, Inc.
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
package org.eclipse.dltk.ui.editor.highlighting;

import org.eclipse.dltk.compiler.env.IModuleSource;

public interface ISemanticHighlighter {

	/**
	 * Returns highlighting keys used by this highlighter.
	 * 
	 * @return
	 */
	String[] getHighlightingKeys();

	/**
	 * Performs the highlighting of the specified <code>code</code> and reports
	 * positions to be highlighted to the specified <code>requestor</code>.
	 * 
	 * @param code
	 * @param requestor
	 * @throws AbortSemanticHighlightingException
	 */
	void process(IModuleSource code, ISemanticHighlightingRequestor requestor);

}
