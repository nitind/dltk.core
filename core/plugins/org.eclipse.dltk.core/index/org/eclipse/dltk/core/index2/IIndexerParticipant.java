/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.dltk.core.index2;

/**
 * Provides language dependent implementations for indexing parser and element
 * resolver.
 * 
 * @author michael
 * @since 2.0
 * 
 */
public interface IIndexerParticipant {

	/**
	 * Returns indexing parser for indexer.
	 * 
	 * @return
	 */
	public IIndexingParser getIndexingParser();

	/**
	 * Returns element resolver for indexer.
	 * 
	 * @return
	 */
	public IElementResolver getElementResolver();
}
