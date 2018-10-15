/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
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
package org.eclipse.dltk.ui.coloring;

/**
 * @since 3.0
 */
public interface IColoringPreferenceKey {

	String getColorKey();

	String getBoldKey();

	String getItalicKey();

	String getStrikethroughKey();

	String getUnderlineKey();

	String getEnableKey();

}
