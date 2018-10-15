/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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
package org.eclipse.dltk.compiler.env;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.IModelElement;

@Deprecated
public class CompilerSourceCode implements IModuleSource {

	private String string;
	private char[] charArray;

	public CompilerSourceCode(String content) {
		Assert.isNotNull(content);
		this.string = content;
	}

	public CompilerSourceCode(char[] content) {
		Assert.isNotNull(content);
		this.charArray = content;
	}

	@Override
	public char[] getContentsAsCharArray() {
		if (charArray == null) {
			charArray = string.toCharArray();
		}
		return charArray;
	}

	@Override
	public String getSourceContents() {
		if (string == null) {
			string = new String(charArray);
		}
		return string;
	}

	@Override
	public IModelElement getModelElement() {
		return null;
	}

	@Override
	public String getFileName() {
		return Util.EMPTY_STRING;
	}

}
