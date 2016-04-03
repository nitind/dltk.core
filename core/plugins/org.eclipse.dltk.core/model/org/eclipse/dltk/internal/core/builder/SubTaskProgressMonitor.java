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
package org.eclipse.dltk.internal.core.builder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ProgressMonitorWrapper;

public class SubTaskProgressMonitor extends ProgressMonitorWrapper {

	private final String prefix;

	/**
	 * @param monitor
	 * @param prefix
	 */
	public SubTaskProgressMonitor(IProgressMonitor monitor, String prefix) {
		super(monitor);
		this.prefix = prefix;
	}

	@Override
	public void subTask(String name) {
		super.subTask(prefix + name);
	}

	@Override
	public void beginTask(String name, int totalWork) {
		// empty
	}

	@Override
	public void done() {
		// empty
	}

	@Override
	public void internalWorked(double work) {
		// empty
	}

	@Override
	public void worked(int work) {
		// empty
	}
}
