/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.core;

import org.eclipse.dltk.compiler.env.INameEnvironment;
import org.eclipse.dltk.internal.core.NameLookup;

public interface ISearchableEnvironment extends INameEnvironment {
	public NameLookup getNameLookup();
}
