/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.compiler.task;

import java.util.List;

import org.eclipse.core.runtime.Preferences;

/**
 * Implementation of the {@link ITodoTaskPreferences} backed by
 * {@link Preferences}
 */
public class TodoTaskPreferences extends AbstractTodoTaskPreferences implements
		ITodoTaskPreferences {

	private Preferences store;

	public TodoTaskPreferences(Preferences store) {
		this.store = store;
	}

	@Override
	public boolean isEnabled() {
		return store.getBoolean(ENABLED);
	}

	@Override
	public boolean isCaseSensitive() {
		return store.getBoolean(CASE_SENSITIVE);
	}

	@Override
	protected String getRawTaskTags() {
		return store.getString(TAGS);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public void setTaskTags(List<TodoTask> elements) {
		store.setValue(TAGS, TaskTagUtils.encodeTaskTags(elements));
	}

	/**
	 * @deprecated use {@link TaskTagUtils#initializeDefaultValues(Preferences)}
	 */
	@Deprecated
	public static void initializeDefaultValues(Preferences store) {
		TaskTagUtils.initializeDefaultValues(store);
	}
}
