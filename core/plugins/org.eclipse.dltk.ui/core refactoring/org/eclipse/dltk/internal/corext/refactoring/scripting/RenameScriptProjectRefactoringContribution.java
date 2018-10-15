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
import org.eclipse.dltk.internal.corext.refactoring.rename.RenameScriptProjectProcessor;
import org.eclipse.dltk.internal.corext.refactoring.rename.ScriptRenameRefactoring;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;

/**
 * Refactoring contribution for the renamescriptproject refactoring.
 *
 */
public final class RenameScriptProjectRefactoringContribution extends
		ScriptRefactoringContribution {

	@Override
	public Refactoring createRefactoring(final RefactoringDescriptor descriptor) {
		return new ScriptRenameRefactoring(new RenameScriptProjectProcessor(
				null));
	}
}
