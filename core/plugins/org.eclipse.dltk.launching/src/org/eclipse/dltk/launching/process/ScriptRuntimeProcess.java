/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.launching.process;

import java.util.Map;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.core.model.RuntimeProcess;

public class ScriptRuntimeProcess extends RuntimeProcess
		implements IScriptProcess {

	/**
	 * @param launch
	 * @param process
	 * @param name
	 * @param attributes
	 */
	public ScriptRuntimeProcess(ILaunch launch, Process process, String name,
			Map<String, String> attributes) {
		super(new LaunchProxy(launch), process, name, attributes);
		setLaunch(launch);
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return null;
	}

	@Override
	public IStreamsProxy getScriptStreamsProxy() {
		return super.getStreamsProxy();
	}

}
