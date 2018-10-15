/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/

package org.eclipse.dltk.internal.ui.search;

import org.eclipse.dltk.ui.search.IQueryParticipant;

/**
 */
public class SearchParticipantRecord {
	private SearchParticipantDescriptor fDescriptor;
	private IQueryParticipant fParticipant;

	public SearchParticipantRecord(SearchParticipantDescriptor descriptor, IQueryParticipant participant) {
		super();
		fDescriptor= descriptor;
		fParticipant= participant;
	}
	/**
	 * @return Returns the descriptor.
	 */
	public SearchParticipantDescriptor getDescriptor() {
		return fDescriptor;
	}
	/**
	 * @return Returns the participant.
	 */
	public IQueryParticipant getParticipant() {
		return fParticipant;
	}
}
