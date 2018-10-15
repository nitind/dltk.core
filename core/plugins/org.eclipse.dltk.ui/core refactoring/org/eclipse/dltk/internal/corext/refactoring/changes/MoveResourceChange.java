/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring.changes;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class MoveResourceChange extends ResourceReorgChange {

	public MoveResourceChange(IResource res, IContainer dest) {
		super(res, dest, null);
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException {
		// We already present a dialog to the user if he
		// moves read-only resources. Since moving a resource
		// doesn't do a validate edit (it actually doesn't
		// change the content we can't check for READ only
		// here.
		return super.isValid(pm, DIRTY);
	}

	@Override
	protected Change doPerformReorg(IPath path, IProgressMonitor pm) throws CoreException {
		getResource().move(path, getReorgFlags(), pm);
		return null;
	}

	@Override
	public String getName() {
		return Messages.format(RefactoringCoreMessages.MoveResourceChange_move, getResource().getFullPath().toString(),
				getDestination().getName());
	}
}
