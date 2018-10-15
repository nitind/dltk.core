/*******************************************************************************
 * Copyright (c) 2010, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.internal.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.builder.IRenameChange;

public class RenameChange implements IRenameChange {

	private final IPath source;
	private final IFile target;

	public RenameChange(IPath source, IFile target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public IPath getSource() {
		return source;
	}

	@Override
	public IFile getTarget() {
		return target;
	}

}
