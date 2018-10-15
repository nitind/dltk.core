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

public abstract class ExecutableOperation implements IExecutableOperation {

	private final String operationName;

	public ExecutableOperation(String operationName) {
		this.operationName = operationName;
	}

	@Override
	public String getOperationName() {
		return operationName;
	}

}
