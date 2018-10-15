/*******************************************************************************
 * Copyright (c) 2010, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.core.IParameter;

public class MethodParameterInfo implements IParameter {

	private final String name;
	private final String type;
	private final String defaultValue;
	private final int flags;

	public MethodParameterInfo(String name) {
		this(name, null, null);
	}

	public MethodParameterInfo(String name, String type, String defaultValue) {
		this(name, type, defaultValue, 0);
	}

	public MethodParameterInfo(String name, String type, String defaultValue,
			int flags) {
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
		this.flags = flags;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public int getFlags() {
		return flags;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof MethodParameterInfo) {
			final MethodParameterInfo other = (MethodParameterInfo) obj;
			return name.equals(other.name)
					&& CharOperation.equals(type, other.type)
					&& CharOperation.equals(defaultValue, other.defaultValue)
					&& flags == other.flags;
		}
		return false;
	}
}
