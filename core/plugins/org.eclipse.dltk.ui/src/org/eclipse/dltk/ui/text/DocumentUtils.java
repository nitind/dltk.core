/*******************************************************************************
 * Copyright (c) 2012 NumberFour AG
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ui.text;

import org.eclipse.dltk.internal.ui.text.DocumentCharacterIterator;
import org.eclipse.jface.text.IDocument;

public class DocumentUtils {

	public static CharSequence asCharSequence(IDocument document) {
		return new DocumentCharacterIterator(document);
	}

}
