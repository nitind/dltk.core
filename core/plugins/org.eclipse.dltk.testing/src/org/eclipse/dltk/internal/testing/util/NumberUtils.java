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
package org.eclipse.dltk.internal.testing.util;

public class NumberUtils {

	public static int toInt(final String strPriority) {
		if (strPriority != null && strPriority.length() != 0) {
			try {
				return Integer.parseInt(strPriority);
			} catch (NumberFormatException e) {
				// ignore
			}
		}
		return 0;
	}

}
