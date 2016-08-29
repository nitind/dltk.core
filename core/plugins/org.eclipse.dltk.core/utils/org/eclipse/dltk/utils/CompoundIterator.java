/*******************************************************************************
 * Copyright (c) 2011, 2016 NumberFour AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.utils;

import java.util.Iterator;

public abstract class CompoundIterator<E> implements Iterator<E> {

	protected Iterator<E> current;

	@Override
	public boolean hasNext() {
		if (current.hasNext()) {
			return true;
		}
		return fetchNext();
	}

	protected abstract boolean fetchNext();

	@Override
	public E next() {
		return current.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
