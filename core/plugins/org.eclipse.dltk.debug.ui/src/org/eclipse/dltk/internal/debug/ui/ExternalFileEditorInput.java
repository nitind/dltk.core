/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/

package org.eclipse.dltk.internal.debug.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.editors.text.ILocationProvider;

public class ExternalFileEditorInput
		implements IPathEditorInput, ILocationProvider {
	private IFileHandle file;

	public ExternalFileEditorInput(IFileHandle file) {
		super();
		this.file = file;
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return file.toOSString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (ILocationProvider.class.equals(adapter)) {
			return (T) this;
		}

		if (IResource.class.equals(adapter)) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			IFile[] files = root.findFilesForLocation(getPath());
			if (files.length > 0) {
				return (T) files[0];
			}
		}

		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	public IPath getPath(Object element) {
		if (element instanceof ExternalFileEditorInput) {
			ExternalFileEditorInput input = (ExternalFileEditorInput) element;
			return input.getPath();
		}
		return null;
	}

	@Override
	public IPath getPath() {
		return Path.fromPortableString(file.toOSString());
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof ExternalFileEditorInput) {
			ExternalFileEditorInput input = (ExternalFileEditorInput) o;
			return file.equals(input.file);
		}

		if (o instanceof IPathEditorInput) {
			IPathEditorInput input = (IPathEditorInput) o;
			return getPath().equals(input.getPath());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}
}