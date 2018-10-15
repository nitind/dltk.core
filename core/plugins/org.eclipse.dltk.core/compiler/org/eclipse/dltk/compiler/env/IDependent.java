/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.compiler.env;

/**
 * This represents the target file of a type dependency.
 * 
 * All implementors of this interface are containers for types or types
 * themselves which must be able to identify their source file name when file
 * dependencies are collected.
 */
public interface IDependent {
	char ARCHIVE_FILE_ENTRY_SEPARATOR = '|';

	/**
	 * Answer the file name which defines the type.
	 * 
	 * Return null if no file defines the type.
	 */
	String getFileName();
}
