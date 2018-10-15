/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ast.parser;

import org.eclipse.dltk.annotations.ExtensionPoint;

@ExtensionPoint(point = SourceParserManager.SOURCE_PARSER_EXT_POINT, element = {
		SourceParserManager.PARSER_CONTRIBUTION_TAG,
		SourceParserManager.PARSER_TAG }, attribute = "class")
public interface ISourceParserFactory {

	/**
	 * Creates a new instance of an <code>ISourceParser</code> implementation
	 * 
	 * @return source parser
	 */
	ISourceParser createSourceParser();	
}
