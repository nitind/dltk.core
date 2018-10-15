/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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
package org.eclipse.dltk.ui.editor.highlighting;

import org.eclipse.jface.preference.IPreferenceStore;

public class SemanticHighlightingUtils {

	public static void initializeDefaultValues(IPreferenceStore store,
			SemanticHighlighting[] highlightings) {
		for (int i = 0; i < highlightings.length; ++i) {
			final SemanticHighlighting highlighting = highlightings[i];
			if (highlighting.isSemanticOnly()) {
				store.setDefault(highlighting.getEnabledPreferenceKey(),
						highlighting.isEnabledByDefault());
			}
		}
	}

}
