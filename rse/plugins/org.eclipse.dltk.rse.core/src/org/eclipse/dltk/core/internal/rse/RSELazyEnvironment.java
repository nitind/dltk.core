/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.  
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
package org.eclipse.dltk.core.internal.rse;

import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.internal.environment.LazyEnvironment;

/**
 * @since 2.0
 */
public class RSELazyEnvironment extends LazyEnvironment {

	private final RSEEnvironmentProvider provider;

	public RSELazyEnvironment(String environmentId,
			RSEEnvironmentProvider provider) {
		super(environmentId);
		this.provider = provider;
	}

	@Override
	protected IEnvironment resolveEnvironment(String envId) {
		return provider.getEnvironment(envId, false);
	}

}
