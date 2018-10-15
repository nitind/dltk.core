/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ti.goals;

import org.eclipse.dltk.ti.IContext;

public class AbstractReferencesGoal extends AbstractGoal {

	private final String name;
	private final String parentModelKey;

	public AbstractReferencesGoal(IContext context, String name,
			String parentKey) {
		super(context);
		this.name = name;
		parentModelKey = parentKey;
	}

	public String getName() {
		return name;
	}

	public String getParentModelKey() {
		return parentModelKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parentModelKey == null) ? 0 : parentModelKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractReferencesGoal other = (AbstractReferencesGoal) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (parentModelKey == null) {
			if (other.parentModelKey != null) {
				return false;
			}
		} else if (!parentModelKey.equals(other.parentModelKey)) {
			return false;
		}
		return true;
	}

}
