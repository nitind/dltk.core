/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.debug.core;

public abstract class AbstractDLTKDebugToolkit implements IDLTKDebugToolkit2 {

	@Override
	public boolean isAccessWatchpointSupported() {
		return false;
	}

	@Override
	public boolean isWatchpointComplexSupported() {
		return false;
	}

}
