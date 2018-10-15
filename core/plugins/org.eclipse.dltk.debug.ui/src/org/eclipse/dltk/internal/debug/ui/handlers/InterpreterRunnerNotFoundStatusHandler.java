/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.debug.ui.handlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.dltk.debug.ui.DLTKDebugUIPlugin;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 *
 * @author kds
 *
 */
public class InterpreterRunnerNotFoundStatusHandler implements IStatusHandler {
	@Override
	public Object handleStatus(final IStatus status, Object source)
			throws CoreException {
		DLTKDebugUIPlugin.getStandardDisplay()
				.syncExec(() -> MessageDialog.openError(
						DLTKDebugUIPlugin.getActiveWorkbenchShell(),
						HandlerMessages.InterpreterRunnerNotFound,
						status.getMessage()));

		return null;
	}
}
