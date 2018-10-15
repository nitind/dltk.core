/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ui.text.completion;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;

/**
 * Proposal info that computes the javadoc lazily when it is queried.
 * 
 */
public abstract class MemberProposalInfo extends ProposalInfo {
	// configuration
	protected final IScriptProject fScriptProject;
	protected final CompletionProposal fProposal;

	// cache filled lazily
//	private boolean fModelElementResolved = false;

	/**
	 * Creates a new proposal info.
	 * 
	 * @param project
	 *            the script project to reference when resolving types
	 * @param proposal
	 *            the proposal to generate information for
	 */
	public MemberProposalInfo(IScriptProject project, CompletionProposal proposal) {
		Assert.isNotNull(project);
		Assert.isNotNull(proposal);
		fScriptProject = project;
		fProposal = proposal;
	}

	/**
	 * Returns the script element that this computer corresponds to, possibly
	 * <code>null</code>.
	 * 
	 * @return the script element that this computer corresponds to, possibly
	 *         <code>null</code>
	 * @throws ModelException
	 */
	@Override
	public IModelElement getModelElement() throws ModelException {
//		if (!fModelElementResolved) {
//			fModelElementResolved = true;
//			//fElement = resolveMember();
//		}
		return fProposal.getModelElement();
	}

	@Override
	public Object getForeignElement() {
		return fProposal.getForeign();
	}
	
	public void setModelElement(IModelElement element) {
//		fModelElementResolved = true;
		fElement = element;
	}
}
