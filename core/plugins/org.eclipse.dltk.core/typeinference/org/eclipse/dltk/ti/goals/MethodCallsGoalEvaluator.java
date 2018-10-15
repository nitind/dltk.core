/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ti.goals;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.SearchPattern;

public class MethodCallsGoalEvaluator extends SearchBasedGoalEvaluator {

	public MethodCallsGoalEvaluator(IGoal goal) {
		super(goal);
	}

	@Override
	protected SearchPattern createSearchPattern(IDLTKLanguageToolkit toolkit) {
		MethodCallsGoal goal = (MethodCallsGoal) getGoal();
		String name = goal.getName();
		return SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD,
				IDLTKSearchConstants.REFERENCES, SearchPattern.R_EXACT_MATCH,
				toolkit);
	}

	@Override
	protected IGoal createVerificationGoal(PossiblePosition pos) {
		MethodCallVerificationGoal g = new MethodCallVerificationGoal(this
				.getGoal().getContext(), (MethodCallsGoal) this.getGoal(), pos);
		return g;
	}

}
