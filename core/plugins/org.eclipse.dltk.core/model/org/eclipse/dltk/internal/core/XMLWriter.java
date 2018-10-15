/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.core;
import java.io.Writer;

import org.eclipse.dltk.compiler.util.GenericXMLWriter;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.util.Util;


class XMLWriter extends GenericXMLWriter {

	public XMLWriter(Writer writer, IScriptProject project, boolean printXmlVersion) {
		super(writer, Util.getLineSeparator((String) null, project), printXmlVersion);
	}
}
