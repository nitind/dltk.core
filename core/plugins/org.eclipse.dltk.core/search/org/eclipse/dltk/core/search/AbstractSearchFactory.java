/*******************************************************************************
 * Copyright (c) 2016 Jae Gangemi and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jae Gangemi - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.search;

import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.ISearchFactory;
import org.eclipse.dltk.core.ISearchPatternProcessor;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.search.indexing.SourceIndexerRequestor;
import org.eclipse.dltk.core.search.matching.MatchLocator;
import org.eclipse.dltk.core.search.matching.MatchLocatorParser;

public abstract class AbstractSearchFactory implements ISearchFactory {
	@Override
	public SourceIndexerRequestor createSourceRequestor() {
		return new SourceIndexerRequestor();
	}

	@Override
	public DLTKSearchParticipant createSearchParticipant() {
		return null;
	}

	@Deprecated
	public final MatchLocator createMatchLocator(SearchPattern pattern,
			SearchRequestor requestor, IDLTKSearchScope scope,
			SubProgressMonitor monitor) {
		return new MatchLocator();
	}

	@Override
	public ISearchPatternProcessor createSearchPatternProcessor() {
		return null;
	}

	public String getNormalizedTypeName(IType type) {
		return type.getElementName();
	}

	@Override
	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new MatchLocatorParser(locator) {
		};
	}
}
