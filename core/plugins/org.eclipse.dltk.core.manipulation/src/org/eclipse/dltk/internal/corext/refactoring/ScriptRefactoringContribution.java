/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring;

import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;

/**
 * Partial implementation of a script refactoring contribution.
 */
public abstract class ScriptRefactoringContribution extends RefactoringContribution {

	@Override
	public final RefactoringDescriptor createDescriptor(final String id, final String project, final String description,
			final String comment, final Map<String, String> arguments, final int flags) {
		return new ScriptRefactoringDescriptor(this, id, project, description, comment, arguments, flags);
	}

	/**
	 * Creates the a new refactoring instance.
	 *
	 * @param descriptor
	 *            the refactoring descriptor
	 * @return the refactoring, or <code>null</code>
	 * @throws CoreException
	 *             if an error occurs while creating the refactoring
	 */
	public abstract Refactoring createRefactoring(RefactoringDescriptor descriptor) throws CoreException;

	@Override
	public final Map<String, String> retrieveArgumentMap(final RefactoringDescriptor descriptor) {
		Assert.isNotNull(descriptor);
		if (descriptor instanceof ScriptRefactoringDescriptor)
			return ((ScriptRefactoringDescriptor) descriptor).getArguments();
		return super.retrieveArgumentMap(descriptor);
	}
}
