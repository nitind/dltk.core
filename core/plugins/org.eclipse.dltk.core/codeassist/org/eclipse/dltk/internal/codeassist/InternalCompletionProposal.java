/*******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.codeassist;

import org.eclipse.dltk.core.CompletionProposal;

public class InternalCompletionProposal extends CompletionProposal {

	/**
	 * @param kind
	 * @param completionLocation
	 */
	public InternalCompletionProposal(int kind, int completionLocation) {
		super(kind, completionLocation);
	}

}
