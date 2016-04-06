/*******************************************************************************
 * Copyright (c) 2016 Johan Compagnern and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Johan Compagner - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.ui.IFileEditorInput;

public class FileEditorInputAdapterFactory implements IAdapterFactory {

	private static Class<?>[] PROPERTIES = new Class[] { IModelElement.class };

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object element, Class<T> key) {
		if (IModelElement.class.equals(key)) {

			// Performance optimization, see https://bugs.eclipse.org/bugs/
			// show_bug.cgi?id=133141
			if (element instanceof IFileEditorInput) {
				IModelElement je = DLTKUIPlugin.getDefault()
						.getWorkingCopyManager().getWorkingCopy(
								(IFileEditorInput) element);
				if (je != null && je.exists()) {
					return (T) je;
				}
			}

			return (T) DLTKCore.create(((IFileEditorInput) element).getFile());
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return PROPERTIES;
	}

}
