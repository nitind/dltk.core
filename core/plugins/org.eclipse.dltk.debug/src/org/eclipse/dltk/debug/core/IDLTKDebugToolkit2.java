/*******************************************************************************
 * Copyright (c) 2015 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrey Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.debug.core;

public interface IDLTKDebugToolkit2 extends IDLTKDebugToolkit {

	/**
	 * Construct a complex expression in format fieldName|expression.
	 * 
	 * @return
	 */
	boolean isWatchpointComplexSupported();

}
