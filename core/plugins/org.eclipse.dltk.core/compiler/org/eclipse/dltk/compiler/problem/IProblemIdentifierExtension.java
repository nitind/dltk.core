/*******************************************************************************
 * Copyright (c) 2011 NumberFour AG
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.compiler.problem;

import org.eclipse.dltk.annotations.Nullable;

/**
 * Optional interface to be implemented by {@link IProblemIdentifier problem
 * identifiers}.
 */
public interface IProblemIdentifierExtension {

	/**
	 * Returns the marker type which should be used for this problem identifier.
	 * {@link DefaultProblemFactory} check if this interface is implemented and
	 * delegates to this function.
	 */
	@Nullable
	String getMarkerType();

}
