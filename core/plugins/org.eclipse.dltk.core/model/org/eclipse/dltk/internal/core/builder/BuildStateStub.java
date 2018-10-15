/*******************************************************************************
 * Copyright (c) 2011, 2016 NumberFour AG
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
package org.eclipse.dltk.internal.core.builder;

import org.eclipse.core.runtime.IPath;

public class BuildStateStub extends AbstractBuildState {
	public BuildStateStub(String projectName) {
		super(projectName);
	}

	@Override
	public void recordImportProblem(IPath path) {
	}

	@Override
	public void recordDependency(IPath path, IPath dependency, int flags) {
	}
}
