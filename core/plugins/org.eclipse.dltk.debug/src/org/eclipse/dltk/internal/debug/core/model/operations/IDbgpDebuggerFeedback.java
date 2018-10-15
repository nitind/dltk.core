/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.debug.core.model.operations;

import org.eclipse.dltk.dbgp.IDbgpStatus;
import org.eclipse.dltk.dbgp.exceptions.DbgpException;

public interface IDbgpDebuggerFeedback {
	void endStepInto(DbgpException e, IDbgpStatus status);

	void endStepOver(DbgpException e, IDbgpStatus status);

	void endStepReturn(DbgpException e, IDbgpStatus status);

	void endSuspend(DbgpException e, IDbgpStatus status);

	void endResume(DbgpException e, IDbgpStatus status);

	void endTerminate(DbgpException e, IDbgpStatus status);

	void endInitialStepInto(DbgpException e, IDbgpStatus status);
}
