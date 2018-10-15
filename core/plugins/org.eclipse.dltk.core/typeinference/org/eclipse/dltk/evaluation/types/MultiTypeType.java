/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.evaluation.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ti.types.IEvaluatedType;

public class MultiTypeType implements IEvaluatedType {

	private List<IEvaluatedType> fTypes = new ArrayList<>();

	public MultiTypeType() {
	}

	/**
	 * Add using equal.
	 */
	public void addType(IEvaluatedType type) {
		Iterator<IEvaluatedType> i = fTypes.iterator();
		while (i.hasNext()) {
			IEvaluatedType ltype = i.next();
			if (ltype.equals(type)) {
				return;
			}
		}
		fTypes.add(type);
	}

	@Override
	public String getTypeName() {
		String names = ""; //$NON-NLS-1$
		Iterator<IEvaluatedType> i = fTypes.iterator();
		while (i.hasNext()) {
			IEvaluatedType type = i.next();
			names += type.getTypeName() + " "; //$NON-NLS-1$
		}
		return "multitype:" + names; //$NON-NLS-1$
	}

	public List<IEvaluatedType> getTypes() {
		return this.fTypes;
	}

	public int size() {
		if (this.fTypes != null) {
			return this.fTypes.size();
		}
		return 0;
	}

	public IEvaluatedType get(int i) {
		if (this.fTypes != null) {
			return this.fTypes.get(i);
		}
		return null;
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fTypes == null) ? 0 : fTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiTypeType other = (MultiTypeType) obj;
		if (fTypes == null) {
			if (other.fTypes != null)
				return false;
		} else if (!fTypes.equals(other.fTypes))
			return false;
		return true;
	}
}
