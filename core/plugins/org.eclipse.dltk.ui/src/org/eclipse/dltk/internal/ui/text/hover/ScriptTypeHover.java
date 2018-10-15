/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.text.hover;

import org.eclipse.dltk.ui.text.hover.IScriptEditorTextHover;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;

public class ScriptTypeHover implements IScriptEditorTextHover {

	private IScriptEditorTextHover fDocumentationHover;

	public ScriptTypeHover() {
		fDocumentationHover = new DocumentationHover();
	}

	@Override
	public void setEditor(IEditorPart editor) {
		fDocumentationHover.setEditor(editor);
	}

	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		fDocumentationHover.setPreferenceStore(store);
	}

	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return fDocumentationHover.getHoverRegion(textViewer, offset);
	}

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		return fDocumentationHover.getHoverInfo(textViewer, hoverRegion);
	}
}
