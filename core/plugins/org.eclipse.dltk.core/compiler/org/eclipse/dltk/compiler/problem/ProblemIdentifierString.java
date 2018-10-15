/*******************************************************************************
 * Copyright (c) 2011, 2016 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.compiler.problem;

import org.eclipse.dltk.core.DLTKCore;

class ProblemIdentifierString implements IProblemIdentifier {

	private final String id;

	public ProblemIdentifierString(String id) {
		this.id = id;
	}

	@Override
	public String contributor() {
		return DLTKCore.PLUGIN_ID;
	}

	@Override
	public String name() {
		final int pos = id.indexOf('#');
		return pos >= 0 ? id.substring(pos + 1) : id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProblemIdentifierString) {
			final ProblemIdentifierString other = (ProblemIdentifierString) obj;
			return id.equals(other.id);
		}
		return false;
	}

}
