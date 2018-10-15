/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.environment;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.ui.environment.IEnvironmentUI;

public class LocalEnvironmentUIAdapter implements IAdapterFactory {
	private final static Class<?>[] ADAPTABLES = new Class[] {
			IEnvironmentUI.class };

	public LocalEnvironmentUIAdapter() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof LocalEnvironment
				&& adapterType == IEnvironmentUI.class) {
			return (T) new LocalEnvironmentUI();
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return ADAPTABLES;
	}
}
