/*******************************************************************************
 * Copyright (c) 2009, 2018 xored software, Inc. and others.
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
package org.eclipse.dltk.ui;

import org.eclipse.jface.resource.DataFormatException;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.RGB;

/**
 * @since 2.0
 */
public class ColorPreferenceConverter {

	/**
	 * Converts preference value to {@link RGB}
	 *
	 * @param value
	 * @return converted value or <code>null</code>
	 */
	public static RGB asRGB(Object value) {
		if (value instanceof RGB)
			return (RGB) value;
		else if (value instanceof String) {
			final String s = (String) value;
			if (s.length() == 0) {
				// treat empty string as black color
				return new RGB(0, 0, 0);
			}
			try {
				return StringConverter.asRGB(s);
			} catch (DataFormatException e) {
				DLTKUIPlugin.warn(
						NLS.bind("Error parsing {0} as color value", s), e); //$NON-NLS-1$
				// fall through
			}
		}
		return null;
	}

}
