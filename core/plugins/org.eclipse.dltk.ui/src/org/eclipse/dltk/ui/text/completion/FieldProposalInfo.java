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

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;

/**
 * Proposal info that computes the javadoc lazily when it is queried.
 * 
 */
public final class FieldProposalInfo extends MemberProposalInfo {

	/**
	 * Creates a new proposal info.
	 * 
	 * @param project
	 *            thescriptproject to reference when resolving types
	 * @param proposal
	 *            the proposal to generate information for
	 */
	public FieldProposalInfo(IScriptProject project, CompletionProposal proposal) {
		super(project, proposal);
	}
}
