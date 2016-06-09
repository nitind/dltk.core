/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.dltk.core.IModelStatusConstants;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.IFileHandle;

/**
 * A archive entry that represents a non-java resource found in a archive.
 * 
 * @see IStorage
 */
public class ExternalEntryFile extends PlatformObject implements IStorage {
	private IFileHandle file;

	public ExternalEntryFile(IFileHandle file) {
		if (file == null)
			throw new NullPointerException();
		this.file = file;
	}

	@Override
	public InputStream getContents() throws CoreException {
		try {
			return new BufferedInputStream(file.openInputStream(null));
		} catch (IOException e) {
			throw new ModelException(e, IModelStatusConstants.IO_EXCEPTION);
		}
	}

	@Override
	public IPath getFullPath() {
		return this.file.getPath();
	}

	@Override
	public String getName() {
		return this.file.getName();
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Override
	public String toString() {
		return "ExternalEntryFile[" + this.file.toOSString() + "]"; //$NON-NLS-2$ //$NON-NLS-1$
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		T rv = super.getAdapter(adapter);
		if (rv != null)
			return rv;
		return Platform.getAdapterManager().getAdapter(file, adapter);
	}
}
