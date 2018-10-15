/*******************************************************************************
 * Copyright (c) 2011 xored software, Inc.
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

import org.eclipse.dltk.ui.text.ITokenFactory;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;

public interface ITextRuleProvider {

	/**
	 * Creates {@link IRule} for the specified document content type.
	 * 
	 * @param contentType
	 *            the document content type to create rules for
	 * @param tokenFactory
	 *            token factory to return {@link IToken} by its internal
	 *            identifier
	 * 
	 * @return
	 */
	IRule[] getRules(String contentType, ITokenFactory tokenFactory);

}
