/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.core.mixin;

import org.eclipse.dltk.core.ISourceModule;

public interface IMixinParser {
	/**
	 * Module could be null if signature are false. If signature are set to
	 * true, then module could not be null and user are able to use search. If
	 * not, user could not use search.
	 * 
	 * @param contents
	 * @param signature
	 * @param module
	 */
	public void parserSourceModule(boolean signature, ISourceModule module);

	void setRequirestor(IMixinRequestor requestor);
}
