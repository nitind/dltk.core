/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core.search;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.compiler.util.SimpleSet;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.index.Index;

public class SubTypeSearchJob extends PatternSearchJob {

	SimpleSet indexes = new SimpleSet(5);

	public SubTypeSearchJob(SearchPattern pattern,
			SearchParticipant participant, IDLTKSearchScope scope,
			IndexQueryRequestor requestor) {
		super(pattern, participant, scope, requestor);
	}

	public void finished() {
		Object[] values = this.indexes.values;
		for (int i = 0, l = values.length; i < l; i++)
			if (values[i] != null)
				((Index) values[i]).stopQuery();
	}

	@Override
	public boolean search(Index index, IProgressMonitor progressMonitor) {
		if (index == null)
			return COMPLETE;
		if (indexes.addIfNotIncluded(index) == index)
			index.startQuery();
		return super.search(index, progressMonitor);
	}
}
