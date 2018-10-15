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
package org.eclipse.dltk.validators.core.tests;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.validators.core.IValidatorType;
import org.eclipse.dltk.validators.internal.core.ValidatorManager;

public class SimpleValidatorUtils {

	public static SimpleValidatorType find() throws CoreException {
		IValidatorType[] allValidatorTypes;
		allValidatorTypes = ValidatorManager.getAllValidatorTypes();
		for (int i = 0; i < allValidatorTypes.length; i++) {
			if (allValidatorTypes[i] instanceof SimpleValidatorType) {
				return (SimpleValidatorType) allValidatorTypes[i];
			}
		}
		return null;
	}

}
