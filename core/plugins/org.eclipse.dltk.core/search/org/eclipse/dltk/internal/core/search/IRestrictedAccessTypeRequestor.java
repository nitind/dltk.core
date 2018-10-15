/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.core.search;

import org.eclipse.dltk.internal.compiler.env.AccessRestriction;

/**
 * A <code>IRestrictedAccessTypeRequestor</code> collects search results from a
 * <code>searchAllTypeNames</code> query to a <code>SearchBasicEngine</code>
 * providing restricted access information when a type is accepted.
 */
public interface IRestrictedAccessTypeRequestor {

	public void acceptType(int modifiers, char[] packageName,
			char[] simpleTypeName, char[][] enclosingTypeNames,
			char[][] superTypes, String path, AccessRestriction access);

}
