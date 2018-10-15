/*******************************************************************************
 * Copyright (c) 2010, 2016 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.core.search.matching2;

public class MatchingCollector<E> implements IMatchingCollector<E> {
	private final IMatchingPredicate<E> predicate;
	private final IMatchingNodeSet<E> nodeSet;

	public MatchingCollector(IMatchingPredicate<E> predicate,
			IMatchingNodeSet<E> nodeSet) {
		this.predicate = predicate;
		this.nodeSet = nodeSet;
	}

	@Override
	public void report(E node) {
		MatchLevel level = predicate.match(node);
		if (level != null) {
			nodeSet.addMatch(node, level);
		}
	}
}
