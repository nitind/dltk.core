/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core.search.matching;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.search.index.EntryResult;
import org.eclipse.dltk.core.search.index.Index;
import org.eclipse.dltk.core.search.indexing.IIndexConstants;

public class PackageDeclarationPattern extends DLTKSearchPattern implements
		IIndexConstants {

	protected char[] pkgName;

	public PackageDeclarationPattern(char[] pkgName, int matchRule,
			IDLTKLanguageToolkit toolkit) {
		super(PKG_DECL_PATTERN, matchRule, toolkit);
		this.pkgName = pkgName;
	}

	@Override
	public EntryResult[] queryIn(Index index) {
		// package declarations are not indexed
		return null;
	}

	@Override
	protected StringBuffer print(StringBuffer output) {
		output.append("PackageDeclarationPattern: <"); //$NON-NLS-1$
		if (this.pkgName != null)
			output.append(this.pkgName);
		else
			output.append("*"); //$NON-NLS-1$
		output.append(">"); //$NON-NLS-1$
		return super.print(output);
	}
}