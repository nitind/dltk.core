/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.ui.text;

import org.eclipse.dltk.compiler.task.AbstractTodoTaskPreferences;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Implementation of the {@link IPreferenceStore} backed by
 * {@link IPreferenceStore}
 */
public class TodoTaskPreferencesOnPreferenceStore extends
		AbstractTodoTaskPreferences {

	private final IPreferenceStore store;

	public TodoTaskPreferencesOnPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}

	@Override
	protected String getRawTaskTags() {
		return store.getString(TAGS);
	}

	@Override
	public boolean isCaseSensitive() {
		return store.getBoolean(CASE_SENSITIVE);
	}

	@Override
	public boolean isEnabled() {
		return store.getBoolean(ENABLED);
	}

}
