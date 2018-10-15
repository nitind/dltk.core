/*******************************************************************************
 * Copyright (c) 2011 xored software, Inc.
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

import org.eclipse.core.resources.IMarker;

public enum ProblemSeverity {
	DEFAULT(-1), IGNORE(-1), INFO(IMarker.SEVERITY_INFO), WARNING(
			IMarker.SEVERITY_WARNING), ERROR(IMarker.SEVERITY_ERROR);

	public final int value;

	private ProblemSeverity(int value) {
		this.value = value;
	}
}
