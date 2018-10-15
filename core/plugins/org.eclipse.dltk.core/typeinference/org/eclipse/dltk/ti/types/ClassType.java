/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ti.types;

import org.eclipse.dltk.core.search.indexing.IIndexConstants;

/**
 * Represents type as some user class Each such class should be presented inside
 * a DLTK MixinModel.
 */
public abstract class ClassType implements IEvaluatedType {

	@Override
	public String getTypeName() {
		String typeName = getModelKey().replaceAll(
				"\\" + String.valueOf(IIndexConstants.SEPARATOR), "::"); //$NON-NLS-1$ //$NON-NLS-2$
		if (typeName.endsWith("%") == true) { //$NON-NLS-1$
			typeName = typeName.substring(0, (typeName.length() - 1));
		}
		return typeName;
	}

	public abstract String getModelKey();

	@Override
	public String toString() {
		return getModelKey();
	}

}
