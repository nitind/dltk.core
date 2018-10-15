/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IType;

/**
 * The SingleTypeRequestor is an IModelElementRequestor that only accepts one
 * result element and then cancels.
 */
/* package */class SingleTypeRequestor implements IModelElementRequestor {
	/**
	 * The single accepted element
	 */
	protected IType fElement = null;

	/**
	 * @see IModelElementRequestor
	 */
	@Override
	public void acceptField(IField field) {
		// implements interface method
	}

	/**
	 * @see IModelElementRequestor
	 */
	@Override
	public void acceptMemberType(IType type) {
		fElement = type;
	}

	/**
	 * @see IModelElementRequestor
	 */
	@Override
	public void acceptMethod(IMethod method) {
		// implements interface method
	}

	/**
	 * @see IModelElementRequestor
	 */
	@Override
	public void acceptScriptFolder(IScriptFolder ScriptFolder) {
		// implements interface method
	}

	/**
	 * @see IModelElementRequestor
	 */
	@Override
	public void acceptType(IType type) {
		fElement = type;
	}

	/**
	 * Returns the type accepted by this requestor, or <code>null</code> if no
	 * type has been accepted.
	 */
	public IType getType() {
		return fElement;
	}

	/**
	 * @see IModelElementRequestor
	 */
	@Override
	public boolean isCanceled() {
		return fElement != null;
	}

	/**
	 * Reset the state of this requestor
	 */
	public void reset() {
		fElement = null;
	}
}
