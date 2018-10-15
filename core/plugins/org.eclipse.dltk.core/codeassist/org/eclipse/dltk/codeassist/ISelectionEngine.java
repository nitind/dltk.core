/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.codeassist;

import java.util.Map;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.IModelElement;

public interface ISelectionEngine {
	void setRequestor(ISelectionRequestor requestor);

	IModelElement[] select(IModuleSource module, int selectionStart,
			int selectionEnd);

	void setOptions(Map options);
}
