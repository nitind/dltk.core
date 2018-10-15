/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.dltk.ui.text.completion;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.jface.viewers.StyledString;

/**
 * @since 5.2
 */
public interface ICompletionProposalLabelProviderExtension {

	StyledString createStyledFieldProposalLabel(CompletionProposal proposal);

	StyledString createStyledLabel(CompletionProposal fProposal);

	StyledString createStyledKeywordLabel(CompletionProposal proposal);

	StyledString createStyledSimpleLabel(CompletionProposal proposal);

	StyledString createStyledTypeProposalLabel(CompletionProposal proposal);

	StyledString createStyledSimpleLabelWithType(CompletionProposal proposal);
}
