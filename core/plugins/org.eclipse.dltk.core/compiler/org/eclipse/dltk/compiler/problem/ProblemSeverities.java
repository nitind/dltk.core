/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.compiler.problem;

public final class ProblemSeverities {

	private ProblemSeverities() {
	}

	public static final ProblemSeverity Ignore = ProblemSeverity.IGNORE;
	public static final ProblemSeverity Warning = ProblemSeverity.WARNING;
	public static final ProblemSeverity Error = ProblemSeverity.ERROR;
	public static final ProblemSeverity Info = ProblemSeverity.INFO;
}
