/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.validators.core;

public interface IValidatorChangedListener {
	// public void validatorChanged(IValidator validator);

	// public void validatorAdded(IValidator validator);

	// public void validatorRemoved(IValidator validator);

	void validatorChanged();
}
