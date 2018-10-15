/*******************************************************************************
 * Copyright (c) 2009, 2016 xored software, Inc.
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
package org.eclipse.dltk.core.environment;

public abstract class EnvironmentChangedListener implements
		IEnvironmentChangedListener {

	@Override
	public void environmentAdded(IEnvironment environment) {
	}

	@Override
	public void environmentChanged(IEnvironment environment) {
	}

	@Override
	public void environmentRemoved(IEnvironment environment) {
	}

	@Override
	public void environmentsModified() {
	}

}
