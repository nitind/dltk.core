/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ui.text.completion;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

/**
 * extends {@link ContentAssistant}
 * 
 * Decides weather to provide extended content assist (templates). <br>
 * see bug#197419 - Template proposals short-cut doesn't work
 * 
 */
public interface IScriptContentAssistExtension {

	boolean provide(IContentAssistProcessor processor);

}
