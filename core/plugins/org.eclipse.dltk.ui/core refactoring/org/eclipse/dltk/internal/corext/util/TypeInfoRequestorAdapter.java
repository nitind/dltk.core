/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.util;

import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.ui.dialogs.ITypeInfoRequestor;


public class TypeInfoRequestorAdapter implements ITypeInfoRequestor {

	private TypeNameMatch fMatch;

	public void setMatch(TypeNameMatch type) {
		fMatch= type;
	}

	@Override
	public int getModifiers() {
		return fMatch.getModifiers();
	}

	@Override
	public String getPackageName() {
		return fMatch.getPackageName();
	}

	@Override
	public String getTypeName() {
		return fMatch.getSimpleTypeName();
	}


}
