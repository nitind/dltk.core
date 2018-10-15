/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ui.text.completion;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.jface.viewers.StyledString;

/**
 * @since 5.5
 */
public interface ICompletionProposalLabelProviderExtension2 {
	StyledString createStyledOverrideMethodProposalLabel(
			CompletionProposal proposal);
}
