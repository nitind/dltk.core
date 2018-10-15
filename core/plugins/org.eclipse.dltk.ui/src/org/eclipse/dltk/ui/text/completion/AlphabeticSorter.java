/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ui.text.completion;

import org.eclipse.jface.text.contentassist.ICompletionProposal;

/**
 * A alphabetic proposal based sorter.
 */
public final class AlphabeticSorter extends AbstractProposalSorter {

	private final CompletionProposalComparator fComparator= new CompletionProposalComparator();
	
	public AlphabeticSorter() {
		fComparator.setOrderAlphabetically(true);
	}
	
	@Override
	public int compare(ICompletionProposal p1, ICompletionProposal p2) {
		return fComparator.compare(p1, p2);
	}

}
