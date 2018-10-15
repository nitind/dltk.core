/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.search;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.ui.search.ScriptSearchPage;
import org.eclipse.search.ui.ISearchPageScoreComputer;


public class DLTKSearchPageScoreComputer implements ISearchPageScoreComputer {

	@Override
	public int computeScore(String id, Object element) {
		if (!ScriptSearchPage.EXTENSION_POINT_ID.equals(id))
			// Can't decide
			return ISearchPageScoreComputer.UNKNOWN;

		if (element instanceof IModelElement || element instanceof ExternalStorageEditorInput )
			return 90;

		return ISearchPageScoreComputer.LOWEST;
	}
}
