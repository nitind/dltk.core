/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.dltk.internal.ui.text.hover;

import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.corext.util.Strings;
import org.eclipse.dltk.internal.ui.editor.ScriptEditor;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.part.IWorkbenchPartOrientation;

/**
 * Provides source as hover info for Java elements.
 */
public class ScriptSourceHover extends AbstractScriptEditorTextHover implements
		ITextHoverExtension, IInformationProviderExtension2 {

	/*
	 * @see JavaElementHover
	 */
	@Override
	protected String getHoverInfo(String nature, IModelElement[] result) {
		int nResults = result.length;

		if (nResults > 1)
			return null;

		IModelElement curr = result[0];
		if ((curr instanceof IMember) && curr instanceof ISourceReference) {
			try {
				String source = ((ISourceReference) curr).getSource();
				if (source == null)
					return null;

				String[] sourceLines = Strings.convertIntoLines(source);
				if (sourceLines == null)
					return null;

				// source = removeLeadingComments(source);
				String delim = System.getProperty("line.separator", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
				source = Strings.concatenate(sourceLines, delim);

				return source;
			} catch (ModelException ex) {
			}
		}
		return null;
	}

	@Override
	public IInformationControlCreator getHoverControlCreator() {
		return new IInformationControlCreator() {
			@Override
			public IInformationControl createInformationControl(Shell parent) {
				IEditorPart editor = getEditor();
				if (editor instanceof ScriptEditor) {
					int shellStyle = SWT.TOOL | SWT.NO_TRIM;
					if (editor instanceof IWorkbenchPartOrientation)
						shellStyle |= ((IWorkbenchPartOrientation) editor)
								.getOrientation();
					return new SourceViewerInformationControl(parent,
							shellStyle, SWT.NONE, EditorsUI
									.getTooltipAffordanceString(),
							((ScriptEditor) editor).getLanguageToolkit());
				}
				return null;
			}
		};
	}

	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return new IInformationControlCreator() {
			@Override
			public IInformationControl createInformationControl(Shell parent) {
				int style = SWT.V_SCROLL | SWT.H_SCROLL;
				int shellStyle = SWT.RESIZE | SWT.TOOL;
				IEditorPart editor = getEditor();
				if (editor instanceof IWorkbenchPartOrientation)
					shellStyle |= ((IWorkbenchPartOrientation) editor)
							.getOrientation();
				if (editor instanceof ScriptEditor) {
					return new SourceViewerInformationControl(parent,
							shellStyle, style, ((ScriptEditor) editor)
									.getLanguageToolkit());
				}
				return null;
			}
		};
	}
}
