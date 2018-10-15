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

import org.eclipse.dltk.core.IModelElementDelta;
import org.eclipse.dltk.core.search.IDLTKSearchScope;

public abstract class AbstractSearchScope implements IDLTKSearchScope {

	/*
	 * (non-Javadoc) Process the given delta and refresh its internal state if
	 * needed. Returns whether the internal state was refreshed.
	 */
	public abstract void processDelta(IModelElementDelta delta);

}
