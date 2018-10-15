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
package org.eclipse.dltk.ui.text;

import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.jface.text.IDocument;

/**
 * Resolution for a annotation. When run, a resolution would typically eliminate
 * the need for the annotation.
 */
public interface IAnnotationResolution {

	/**
	 * Returns a short label indicating what the resolution will do.
	 * 
	 * @return a short label for this resolution
	 */
	public String getLabel();

	/**
	 * Runs this resolution.
	 * 
	 * @param annotation
	 *            the annotation to resolve
	 * @param document
	 */
	void run(IScriptAnnotation annotation, IDocument document);

}
