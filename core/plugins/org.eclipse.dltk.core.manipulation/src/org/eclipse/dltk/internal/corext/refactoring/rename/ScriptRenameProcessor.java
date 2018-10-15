/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring.rename;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.internal.corext.refactoring.tagging.ICommentProvider;
import org.eclipse.dltk.internal.corext.refactoring.tagging.INameUpdating;
import org.eclipse.dltk.internal.corext.refactoring.tagging.IScriptableRefactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.ltk.core.refactoring.participants.ValidateEditChecker;

public abstract class ScriptRenameProcessor extends RenameProcessor
		implements IScriptableRefactoring, INameUpdating, ICommentProvider {

	private String fNewElementName;
	private String fComment;
	private RenameModifications fRenameModifications;

	@Override
	public final RefactoringParticipant[] loadParticipants(RefactoringStatus status, SharableParticipants shared)
			throws CoreException {
		return getRenameModifications().loadParticipants(status, this, getAffectedProjectNatures(), shared);
	}

	@Override
	public final RefactoringStatus checkFinalConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws CoreException, OperationCanceledException {
		ResourceChangeChecker checker = context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = checker.getDeltaFactory();
		RefactoringStatus result = doCheckFinalConditions(pm, context);
		if (result.hasFatalError())
			return result;
		IFile[] changed = getChangedFiles();
		for (int i = 0; i < changed.length; i++) {
			deltaFactory.change(changed[i]);
		}
		RenameModifications renameModifications = getRenameModifications();
		renameModifications.buildDelta(deltaFactory);
		renameModifications.buildValidateEdits(context.getChecker(ValidateEditChecker.class));
		return result;
	}

	private RenameModifications getRenameModifications() throws CoreException {
		if (fRenameModifications == null)
			fRenameModifications = computeRenameModifications();
		return fRenameModifications;
	}

	protected abstract RenameModifications computeRenameModifications() throws CoreException;

	protected abstract RefactoringStatus doCheckFinalConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws CoreException, OperationCanceledException;

	protected abstract IFile[] getChangedFiles() throws CoreException;

	protected abstract String[] getAffectedProjectNatures() throws CoreException;

	@Override
	public void setNewElementName(String newName) {
		Assert.isNotNull(newName);
		fNewElementName = newName;
	}

	@Override
	public String getNewElementName() {
		return fNewElementName;
	}

	/**
	 * <code>true</code> by default, subclasses may override.
	 *
	 * @return <code>true</code> iff this refactoring needs all editors to be
	 *         saved, <code>false</code> otherwise
	 */
	public boolean needsSavedEditors() {
		return true;
	}

	@Override
	public final boolean canEnableComment() {
		return true;
	}

	@Override
	public final String getComment() {
		return fComment;
	}

	@Override
	public final void setComment(String comment) {
		fComment = comment;
	}
}
