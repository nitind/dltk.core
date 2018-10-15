/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
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
package org.eclipse.dltk.ui.coloring;

import org.eclipse.swt.graphics.RGB;

/**
 * @since 3.0
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IColoringPreferenceRequestor {

	void enterCategory(String category);

	void addPreference(String baseKey, String name, RGB color,
			FontStyle... styles);

	void addPreference(String baseKey, String name, RGB color,
			EnablementStyle enablementStyle, FontStyle... fontStyles);

	void addPreference(IColoringPreferenceKey key, String name, RGB color,
			FontStyle... styles);

	void addPreference(IColoringPreferenceKey key, String name, RGB color,
			EnablementStyle enablementStyle, FontStyle... fontStyles);

}
