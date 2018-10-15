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
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.core.tests;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.mixin.IMixinParser;
import org.eclipse.dltk.core.mixin.IMixinRequestor;
import org.eclipse.dltk.core.mixin.IMixinRequestor.ElementInfo;

public class TestMixinParser implements IMixinParser {

	private IMixinRequestor requestor;

	@Override
	public void parserSourceModule(boolean signature, ISourceModule module) {
		if (module.getElementName().equals("X.txt")) {
			requestor.reportElement(key("{foo"));
			requestor.reportElement(key("{foo{$a"));
			requestor.reportElement(key("{foo{$b"));
		}
		requestor.reportElement(key("Module"));
		requestor.reportElement(key("Module{gamma"));
	}

	private ElementInfo key(String key) {
		ElementInfo info = new ElementInfo();
		info.key = key;
		return info;
	}

	@Override
	public void setRequirestor(IMixinRequestor requestor) {
		this.requestor = requestor;
	}
}
