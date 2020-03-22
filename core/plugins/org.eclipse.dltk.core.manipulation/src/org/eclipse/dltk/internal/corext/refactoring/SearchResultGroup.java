/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.internal.corext.util.SearchUtils;

public class SearchResultGroup {

	private final IResource fResource;
	private final List<SearchMatch> fSearchMatches;

	public SearchResultGroup(IResource res, SearchMatch[] matches) {
		Assert.isNotNull(matches);
		fResource = res;
		fSearchMatches = new ArrayList<>(Arrays.asList(matches));
	}

	public void add(SearchMatch match) {
		Assert.isNotNull(match);
		fSearchMatches.add(match);
	}

	public IResource getResource() {
		return fResource;
	}

	public List<SearchMatch> getSearchResults() {
		return Collections.unmodifiableList(fSearchMatches);
	}

	public static IResource[] getResources(SearchResultGroup[] searchResultGroups) {
		Set<IResource> resourceSet = new HashSet<>(searchResultGroups.length);
		for (int i = 0; i < searchResultGroups.length; i++) {
			resourceSet.add(searchResultGroups[i].getResource());
		}
		return resourceSet.toArray(new IResource[resourceSet.size()]);
	}

	public ISourceModule getSourceModule() {
		if (getSearchResults() == null || getSearchResults().size() == 0)
			return null;
		return SearchUtils.getSourceModule(getSearchResults().get(0));
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder(fResource.getFullPath().toString());
		buf.append('\n');
		for (int i = 0; i < fSearchMatches.size(); i++) {
			SearchMatch match = fSearchMatches.get(i);
			buf.append("  ").append(match.getOffset()).append(", ").append(match.getLength()); //$NON-NLS-1$//$NON-NLS-2$
			buf.append(match.getAccuracy() == SearchMatch.A_ACCURATE ? "; acc" : "; inacc"); //$NON-NLS-1$//$NON-NLS-2$
			if (match.isInsideDocComment())
				buf.append("; inDoc"); //$NON-NLS-1$
			if (match.getElement() instanceof IModelElement)
				buf.append("; in: ").append(((IModelElement) match.getElement()).getElementName()); //$NON-NLS-1$
			buf.append('\n');
		}
		return buf.toString();
	}
}
