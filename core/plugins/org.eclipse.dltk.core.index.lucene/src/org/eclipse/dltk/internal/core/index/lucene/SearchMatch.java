/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index.lucene;

/**
 * Search match descriptor.
 * 
 * @author Bartlomiej Laczkowski
 */
public class SearchMatch {

	public final String path;
	public final String container;
	public final int elementType;
	public final String elementName;
	public final int offset;
	public final int length;
	public final String metadata;
	public final String qualifier;
	public final String doc;
	public final int flags;
	public final int nameOffset;
	public final int nameLength;
	public final String parent;

	/**
	 * Creates new search match.
	 * 
	 * @param container
	 * @param elementType
	 * @param offset
	 * @param length
	 * @param nameOffset
	 * @param nameLength
	 * @param flags
	 * @param elementName
	 * @param path
	 * @param parent
	 * @param qualifier
	 * @param doc
	 * @param metadata
	 */
	public SearchMatch(String container, long elementType, long offset,
			long length, long nameOffset, long nameLength, long flags,
			String elementName, String path, String parent, String qualifier,
			String doc, String metadata) {
		super();
		this.container = container;
		this.elementType = (int) elementType;
		this.offset = (int) offset;
		this.length = (int) length;
		this.nameOffset = (int) nameOffset;
		this.nameLength = (int) nameLength;
		this.flags = (int) flags;
		this.elementName = elementName;
		this.path = path;
		this.parent = parent;
		this.qualifier = qualifier;
		this.doc = doc;
		this.metadata = metadata;
	}

}
