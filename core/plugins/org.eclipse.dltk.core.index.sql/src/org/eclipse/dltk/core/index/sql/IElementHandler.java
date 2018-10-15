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
package org.eclipse.dltk.core.index.sql;

/**
 * Handler for database records while searching.
 * 
 * @author michael
 * 
 */
public interface IElementHandler {

	/**
	 * Handler for element record
	 * 
	 * @param element
	 *            Element returned from database record
	 */
	public void handle(Element element);
}
