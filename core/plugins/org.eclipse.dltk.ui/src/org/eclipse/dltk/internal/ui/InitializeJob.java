/*******************************************************************************
 * Copyright (c) 2012 NumberFour AG
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
package org.eclipse.dltk.internal.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.builder.ScriptBuilderUtil;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.ui.progress.UIJob;

public class InitializeJob extends UIJob {

	static class WorkerJob extends Job {

		public WorkerJob() {
			super(DLTKUIMessages.InitializeJob_Initializing);
			// setPriority(BUILD);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				ScriptBuilderUtil.rebuildAfterUpgrade(monitor);
			} catch (CoreException e) {
				DLTKUIPlugin.logErrorMessage(DLTKUIMessages.InitializeJob_ErrorInitializing, e);
				return e.getStatus();
			}
			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object family) {
			return DLTKUIPlugin.PLUGIN_ID.equals(family);
		}
	}

	public InitializeJob() {
		super(DLTKUIMessages.InitializeJob_Starting);
		setSystem(true);
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		final WorkerJob job = new WorkerJob();
		job.setPriority(Job.SHORT);
		job.schedule();
		return Status.OK_STATUS;
	}

}
