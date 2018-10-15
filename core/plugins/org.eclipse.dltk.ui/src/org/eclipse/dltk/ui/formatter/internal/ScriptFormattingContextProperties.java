/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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
package org.eclipse.dltk.ui.formatter.internal;

public class ScriptFormattingContextProperties {

	/**
	 * Property key of the project property. The property must be
	 * <code>org.eclipse.core.resources.IProject</code>. If set the preferences
	 * of the specified project will be used first.
	 * <p>
	 * Value: <code>"formatting.context.project"</code>
	 */
	public static final String CONTEXT_PROJECT = "formatting.context.project"; //$NON-NLS-1$

	/**
	 * Property key of the source module property. The property must be
	 * <code>org.eclipse.dltk.core.ISourceModule</code>.
	 * <p>
	 * Value: <code>"formatting.context.sourceModule"</code>
	 */
	public static final String MODULE = "formatting.context.sourceModule"; //$NON-NLS-1$

	/**
	 * Property key of the formatter id property. The property must be of the
	 * type <code>java.lang.String</code>.
	 * <p>
	 * Value: <code>"formatting.context.formatterId"</code>
	 */
	public static final String CONTEXT_FORMATTER_ID = "formatting.context.formatterId"; //$NON-NLS-1$

}
