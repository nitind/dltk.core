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
package org.eclipse.dltk.compiler.task;

import org.eclipse.core.resources.IResource;

/**
 * The interface to create <i>tasks</i> for the attached resource. At any given
 * moment this object operates on single {@link IResource}.
 */
public interface ITaskReporter {

	/**
	 * Creates new task for the attached resource.
	 * 
	 * @param message
	 * @param lineNumber
	 * @param priority
	 * @param charStart
	 * @param charEnd
	 */
	void reportTask(String message, int lineNumber, int priority,
			int charStart, int charEnd);

}
