/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core.search;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.internal.compiler.env.AccessRuleSet;

/**
 * Collects the resource paths reported by a client to this search requestor.
 */
public class PathCollector extends IndexQueryRequestor {

	/* a set of resource paths */
	private final Set<String> paths = new HashSet<>(5);

	@Override
	public boolean acceptIndexMatch(String documentPath,
			SearchPattern indexRecord, SearchParticipant participant,
			AccessRuleSet access) {
		paths.add(documentPath);
		return true;
	}

	/**
	 * Returns the paths that have been collected or <code>null</code> if there
	 * are no paths
	 */
	public String[] getPaths() {
		if (paths.isEmpty())
			return null;
		else
			return paths.toArray(new String[paths.size()]);
	}
}
