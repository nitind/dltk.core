/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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

public interface IDLTKDebugToolkit {

	/**
	 * Tests if access watch points are supported. In the DBGP only modification
	 * watch points are defined. Access watch points are proprietary extension
	 * used in DLTK javascript.
	 * 
	 * @return
	 */
	boolean isAccessWatchpointSupported();

}
