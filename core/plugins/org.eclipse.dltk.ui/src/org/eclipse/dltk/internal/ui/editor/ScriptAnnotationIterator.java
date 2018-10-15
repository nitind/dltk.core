/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.editor;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.MarkerAnnotation;

/**
 * Filters problems based on their types.
 */
public class ScriptAnnotationIterator implements Iterator<Annotation> {

	private Iterator<Annotation> fIterator;
	private Annotation fNext;
	private boolean fReturnAllAnnotations;

	/**
	 * Returns a new JavaAnnotationIterator.
	 *
	 * @param model
	 *            the annotation model
	 * @param returnAllAnnotations
	 *            Whether to return non IJavaAnnotations as well
	 */
	public ScriptAnnotationIterator(IAnnotationModel model,
			boolean returnAllAnnotations) {
		fReturnAllAnnotations = returnAllAnnotations;
		if (model != null)
			fIterator = model.getAnnotationIterator();
		else
			fIterator = Collections.emptyIterator();
		skip();
	}

	private void skip() {
		while (fIterator.hasNext()) {
			Annotation next = fIterator.next();
			if (next.isMarkedDeleted())
				continue;
			if (fReturnAllAnnotations || next instanceof IScriptAnnotation
					|| isProblemMarkerAnnotation(next)) {
				fNext = next;
				return;
			}
		}
		fNext = null;
	}

	private static boolean isProblemMarkerAnnotation(Annotation annotation) {
		if (!(annotation instanceof MarkerAnnotation))
			return false;
		try {
			return (((MarkerAnnotation) annotation).getMarker()
					.isSubtypeOf(IMarker.PROBLEM));
		} catch (CoreException e) {
			return false;
		}
	}

	@Override
	public boolean hasNext() {
		return fNext != null;
	}

	/**
	 * @see Iterator#next()
	 * @since 3.0
	 */
	@Override
	public Annotation next() {
		try {
			return fNext;
		} finally {
			skip();
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
