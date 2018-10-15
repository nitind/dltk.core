/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.compiler.task;

import java.util.List;

public abstract class AbstractTodoTaskPreferences implements
		ITodoTaskPreferences {

	protected abstract String getRawTaskTags();

	@Override
	public final List<TodoTask> getTaskTags() {
		return TaskTagUtils.decodeTaskTags(getRawTaskTags());
	}

	@Override
	public final String[] getTagNames() {
		final List<TodoTask> taskTags = getTaskTags();
		final int size = taskTags.size();
		final String[] result = new String[size];
		for (int i = 0; i < size; ++i) {
			result[i] = taskTags.get(i).name;
		}
		return result;
	}

}
