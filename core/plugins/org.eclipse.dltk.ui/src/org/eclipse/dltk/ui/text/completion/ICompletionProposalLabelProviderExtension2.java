/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
