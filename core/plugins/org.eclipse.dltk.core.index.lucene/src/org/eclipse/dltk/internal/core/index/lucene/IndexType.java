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
 * Index type (declarations or references).
 */
public enum IndexType {

	/**
	 * Index type for storing declarations data.
	 */
	DECLARATIONS("declarations"), //$NON-NLS-1$
	/**
	 * Index type for storing references data.
	 */
	REFERENCES("references"); //$NON-NLS-1$

	private final String fDirectory;

	private IndexType(String directory) {
		this.fDirectory = directory;
	}

	/**
	 * Returns related directory name.
	 * 
	 * @return related directory name
	 */
	public String getDirectory() {
		return fDirectory;
	}

}