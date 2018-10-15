/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.ui.part.FileEditorInput;

public class ResourceAdapterFactory implements IAdapterFactory {

	private static Class<?>[] PROPERTIES = new Class[] { IModelElement.class };

	@Override
	public Class<?>[] getAdapterList() {
		return PROPERTIES;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object element, Class<T> key) {
		if (IModelElement.class.equals(key)) {

			// Performance optimization, see https://bugs.eclipse.org/bugs/
			// show_bug.cgi?id=133141
			if (element instanceof IFile) {
				IModelElement je = DLTKUIPlugin.getDefault()
						.getWorkingCopyManager().getWorkingCopy(
								new FileEditorInput((IFile) element));
				if (je != null && je.exists()) {
					return (T) je;
				}
			}

			return (T) DLTKCore.create((IResource) element);
		}
		return null;
	}
}
