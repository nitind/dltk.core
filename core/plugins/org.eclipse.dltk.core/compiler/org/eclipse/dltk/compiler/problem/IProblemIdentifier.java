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

/**
 * The problem identifier. Use <code>enum</code> to implement it and
 * {@link org.eclipse.dltk.utils.EnumNLS} to load localized messages from
 * <code>.properties</code> files.
 */
public interface IProblemIdentifier extends IValidationStatus {

	static IProblemIdentifier NULL = null;

	/**
	 * Returns the identifier of the bundle contributing this identifier
	 * 
	 * @return
	 */
	String contributor();

	/**
	 * Returns the short identifier of the problem
	 * 
	 * @return
	 */
	String name();

}
