/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core.search;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.search.MethodNameMatch;

/**
 * DLTK Search concrete type for a type name match.
 */
public class DLTKSearchMethodNameMatch extends MethodNameMatch {

	private final IMethod method;
	private final int modifiers;

	/**
	 * Creates a new Java Search type name match.
	 */
	public DLTKSearchMethodNameMatch(IMethod method, int modifiers) {
		this.method = method;
		this.modifiers = modifiers;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true; // avoid unnecessary calls for identical objects
		if (obj instanceof MethodNameMatch) {
			MethodNameMatch match = (MethodNameMatch) obj;
			if (this.method == null) {
				return match.getMethod() == null
						&& match.getModifiers() == this.modifiers;
			}
			return this.method.equals(match.getMethod())
					&& match.getModifiers() == this.modifiers;
		}
		return false;
	}

	@Override
	public int getModifiers() {
		return this.modifiers;
	}

	/*
	 * (non-Javadoc) Note that returned handle exists as it matches a type
	 * accepted from up-to-date index file.
	 * 
	 * @see org.eclipse.jdt.core.search.TypeNameMatch#getType()
	 */
	@Override
	public IMethod getMethod() {
		return this.method;
	}

	@Override
	public int hashCode() {
		if (this.method == null)
			return this.modifiers;
		return this.method.hashCode();
	}

	@Override
	public String toString() {
		if (this.method == null)
			return super.toString();
		return this.method.toString();
	}
}
