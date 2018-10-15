/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.environment;

public interface IEnvironmentChangedListener {
	void environmentAdded(IEnvironment environment);

	void environmentRemoved(IEnvironment environment);

	void environmentChanged(IEnvironment environment);

	void environmentsModified();
}
