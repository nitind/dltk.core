/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.text.hover;

import java.util.Iterator;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.internal.ui.editor.ScriptAnnotationIterator;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.text.completion.HTMLPrinter;
import org.eclipse.dltk.utils.TextUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;

/**
 * Abstract super class for annotation hovers.
 *
 */
public abstract class AbstractAnnotationHover extends AbstractScriptEditorTextHover {

	protected DefaultMarkerAnnotationAccess fAnnotationAccess = new DefaultMarkerAnnotationAccess();
	protected boolean fAllAnnotations;

	public AbstractAnnotationHover(boolean allAnnotations) {
		fAllAnnotations = allAnnotations;
	}

	protected String postUpdateMessage(String message) {
		return message;
	}

	/*
	 * Formats a message as HTML text.
	 */
	protected String formatMessage(String message) {
		StringBuffer buffer = new StringBuffer();
		ColorRegistry registry = JFaceResources.getColorRegistry();
		RGB fgRGB = registry.getRGB("org.eclipse.dltk.ui.documentation.foregroundColor"); //$NON-NLS-1$
		RGB bgRGB = registry.getRGB("org.eclipse.dltk.ui.documentation.backgroundColor"); //$NON-NLS-1$
		HTMLPrinter.insertPageProlog(buffer, 0, bgRGB, fgRGB, getStyleSheet());
		buffer.append(postUpdateMessage(TextUtils.escapeHTML(message)));
		HTMLPrinter.addPageEpilog(buffer);
		return buffer.toString();
	}

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		IPath path;
		IAnnotationModel model;

		if (textViewer instanceof ISourceViewer) {
			path = null;
			model = ((ISourceViewer) textViewer).getAnnotationModel();
		} else {
			// Get annotation model from file buffer manager
			path = getEditorInputPath();
			model = getAnnotationModel(path);
		}

		if (model == null) {
			return null;
		}

		try {
			final IPreferenceStore store = getCombinedPreferenceStore();
			Iterator<Annotation> e = new ScriptAnnotationIterator(model, fAllAnnotations);
			int layer = -1;
			String message = null;
			boolean multi = false;
			while (e.hasNext()) {
				Annotation a = e.next();

				AnnotationPreference preference = getAnnotationPreference(a);
				if (preference == null) {
					continue;
				}
				if (!isActive(store, preference.getTextPreferenceKey())
						&& !isActive(store, preference.getHighlightPreferenceKey())) {
					continue;
				}

				Position p = model.getPosition(a);

				int l = fAnnotationAccess.getLayer(a);

				if (l >= layer && p != null && p.overlapsWith(hoverRegion.getOffset(), hoverRegion.getLength())) {
					String msg = getMessageFromAnnotation(a);
					if (msg != null && msg.trim().length() > 0) {
						if (message != null) {
							message = message + "\n-" + msg;
							multi = true;
						} else {
							message = msg;
						}
						layer = l;
					}
				}
			}
			if (layer > -1) {
				if (multi) {
					message = "Multiple markers:\n-" + message;
				}
				return formatMessage(message);
			}

		} finally {
			try {
				if (path != null) {
					ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
					manager.disconnect(path, LocationKind.NORMALIZE, null);
				}
			} catch (CoreException ex) {
				DLTKUIPlugin.log(ex.getStatus());
			}
		}

		return null;
	}

	protected String getMessageFromAnnotation(Annotation a) {
		return a.getText();
	}

	protected static boolean isActive(IPreferenceStore store, String preference) {
		return preference != null && store.getBoolean(preference);
	}

	private IPreferenceStore combinedStore = null;

	protected synchronized IPreferenceStore getCombinedPreferenceStore() {
		if (combinedStore == null) {
			combinedStore = new ChainedPreferenceStore(
					new IPreferenceStore[] { getPreferenceStore(), EditorsUI.getPreferenceStore() });
		}
		return combinedStore;
	}

	protected IPath getEditorInputPath() {
		if (getEditor() == null)
			return null;

		IEditorInput input = getEditor().getEditorInput();
		if (input instanceof IStorageEditorInput) {
			try {
				return ((IStorageEditorInput) input).getStorage().getFullPath();
			} catch (CoreException ex) {
				DLTKUIPlugin.log(ex.getStatus());
			}
		}
		return null;
	}

	protected IAnnotationModel getAnnotationModel(IPath path) {
		if (path == null)
			return null;

		ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
		try {
			manager.connect(path, LocationKind.NORMALIZE, null);
		} catch (CoreException ex) {
			DLTKUIPlugin.log(ex.getStatus());
			return null;
		}

		IAnnotationModel model = null;
		try {
			model = manager.getTextFileBuffer(path, LocationKind.NORMALIZE).getAnnotationModel();
			return model;
		} finally {
			if (model == null) {
				try {
					manager.disconnect(path, LocationKind.NORMALIZE, null);
				} catch (CoreException ex) {
					DLTKUIPlugin.log(ex.getStatus());
				}
			}
		}
	}

	/**
	 * Returns the annotation preference for the given annotation.
	 *
	 * @param annotation the annotation
	 * @return the annotation preference or <code>null</code> if none
	 */
	protected AnnotationPreference getAnnotationPreference(Annotation annotation) {
		if (annotation.isMarkedDeleted()) {
			return null;
		}

		return EditorsUI.getAnnotationPreferenceLookup().getAnnotationPreference(annotation);
	}
}
