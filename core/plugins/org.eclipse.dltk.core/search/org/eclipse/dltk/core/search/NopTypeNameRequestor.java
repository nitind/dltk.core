/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.core.search;

/**
 * This class is a default implementation of type name requestor. It's useful
 * when we don't need to do anything with search results, like when warming up
 * the search engine.
 */
public final class NopTypeNameRequestor extends TypeNameRequestor {

	@Override
	public void acceptType(int modifiers, char[] packageName,
			char[] simpleTypeName, char[][] enclosingTypeNames,
			char[][] superTypes, String path) {
	}

}
