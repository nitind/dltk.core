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
 *     Jae Gangemi - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.debug.core.model;

import org.eclipse.dltk.debug.core.model.IScriptBreakpointPathMapper;

public interface IScriptBreakpointPathMapperExtension extends
		IScriptBreakpointPathMapper {

	void clearCache();

}
