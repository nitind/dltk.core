/*******************************************************************************
 * Copyright (c) 2009, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.utils;

import org.eclipse.core.runtime.NullProgressMonitor;

public class ExecutionContexts {

	private static IExecutionContextManager fManager;

	private static IExecutionContextManager fDefaultManager = new IExecutionContextManager() {

		@Override
		public void executeInBackground(IExecutableOperation operation) {
			operation.execute(new NullProgressMonitor());
		}

		@Override
		public boolean isRunningInUIThread() {
			return false;
		}

	};

	public static synchronized IExecutionContextManager getManager() {
		if (fManager != null) {
			return fManager;
		} else {
			return fDefaultManager;
		}
	}

	public static synchronized void setManager(IExecutionContextManager manager) {
		fManager = manager;
	}

}
