/*******************************************************************************
 * Copyright (c) 2011, 2017 NumberFour AG and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.text.spelling;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.ui.DLTKUIPlugin;

public enum SpellingProblems implements IProblemIdentifier {
	SPELLING_PROBLEM;

	@Override
	public String contributor() {
		return DLTKUIPlugin.PLUGIN_ID;
	}

}
