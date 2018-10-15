/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring.reorg;

import org.eclipse.core.runtime.OperationCanceledException;

public interface IConfirmQuery {
	public boolean confirm(String question) throws OperationCanceledException;
	public boolean confirm(String question, Object[] elements) throws OperationCanceledException;
}
