/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring.scripting;

import org.eclipse.dltk.internal.corext.refactoring.ScriptRefactoringContribution;
import org.eclipse.dltk.internal.corext.refactoring.rename.RenameSourceFolderProcessor;
import org.eclipse.dltk.internal.corext.refactoring.rename.ScriptRenameRefactoring;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;


/**
 * Refactoring contribution for the rename source folder refactoring.
 *
	 *
 */
public final class RenameSourceFolderRefactoringContribution extends ScriptRefactoringContribution {

	@Override
	public Refactoring createRefactoring(final RefactoringDescriptor descriptor) {
		return new ScriptRenameRefactoring(new RenameSourceFolderProcessor(null));
	}
}
