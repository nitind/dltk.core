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
package org.eclipse.dltk.ui.formatter;

public class FormatterSyntaxProblemException extends FormatterException {

	private static final long serialVersionUID = 4527887872127464243L;

	public FormatterSyntaxProblemException() {
		// empty
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FormatterSyntaxProblemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public FormatterSyntaxProblemException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FormatterSyntaxProblemException(Throwable cause) {
		super(cause);
	}

}
