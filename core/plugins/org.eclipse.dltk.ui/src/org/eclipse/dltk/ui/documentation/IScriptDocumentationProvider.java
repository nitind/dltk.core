/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ui.documentation;

import java.io.Reader;

import org.eclipse.dltk.core.IMember;

/**
 * Interface for org.eclipse.dltk.ui.scriptDocumentationProviders extension
 * point.
 */
public interface IScriptDocumentationProvider {

	/**
	 * Fetches info for a member like proc or variable. Also it may look into
	 * parents, if child contains no documentaton.
	 * 
	 * @param element
	 *            Element to get info for.
	 * @param lookIntoParents
	 *            Allows to look into parents for a doc.
	 * @param lookIntoExternal
	 *            Allows to look into external resources.
	 * @return Reader object, providing doc.
	 */
	public Reader getInfo(IMember element, boolean lookIntoParents,
			boolean lookIntoExternal);

	/**
	 * Fetches some info about provided keyword.
	 * 
	 * @param content
	 *            Keyword to get info for.
	 * @return Reader object, providing doc.
	 */
	public Reader getInfo(String content);

}
