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

class ProblemIdentifierInt implements IProblemIdentifier {

	private final int value;

	public ProblemIdentifierInt(int value) {
		this.value = value;
	}

	@Override
	public String contributor() {
		return DLTKCore.PLUGIN_ID;
	}

	@Override
	public String name() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProblemIdentifierInt) {
			final ProblemIdentifierInt other = (ProblemIdentifierInt) obj;
			return value == other.value;
		}
		return false;
	}

}
