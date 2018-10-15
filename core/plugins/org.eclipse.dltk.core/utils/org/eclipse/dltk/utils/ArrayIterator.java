/*******************************************************************************
 * Copyright (c) 2012, 2016 NumberFour AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
	private final T[] items;
	private int index;
	private final int endIndex;

	public ArrayIterator(T[] items) {
		this(items, 0, items.length);
	}

	public ArrayIterator(T[] items, int startIndex, int endIndex) {
		super();
		this.items = items;
		index = startIndex;
		this.endIndex = endIndex;
	}

	@Override
	public boolean hasNext() {
		return index < endIndex;
	}

	@Override
	public T next() throws NoSuchElementException {
		try {
			return items[index++];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
