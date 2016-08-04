/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.internal.environment;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;

public class LocalEnvironment implements IEnvironment, IAdaptable {
	public static final String ENVIRONMENT_ID = DLTKCore.PLUGIN_ID
			+ ".environment.localEnvironment"; //$NON-NLS-1$

	private static IEnvironment instance = new LocalEnvironment();
	private IFileSystem fs;

	private LocalEnvironment() {
		this.fs = EFS.getLocalFileSystem();
	}

	/*
	 * @see org.eclipse.dltk.core.environment.IEnvironment#isLocal()
	 */
	@Override
	public boolean isLocal() {
		return true;
	}

	@Override
	public IFileHandle getFile(IPath path) {
		if (path == null) {
			throw new NullPointerException();
		}
		IFileStore store = fs.getStore(path);
		EFSFileHandle fileHandle = new EFSFileHandle(this, store);
		// if (!fileHandle.exists()) {
		// // Try to resolve file from resources
		// IFile[] files = ResourcesPlugin.getWorkspace().getRoot()
		// .findFilesForLocation(path);
		// if (files.length == 1) {
		// store = fs.getStore(files[0].getLocation());
		// fileHandle = new EFSFileHandle(this, store);
		// }
		// }
		return fileHandle;
	}

	@Override
	public String getId() {
		return ENVIRONMENT_ID;
	}

	public static IEnvironment getInstance() {
		return instance;
	}

	@Override
	public String getSeparator() {
		return File.separator;
	}

	@Override
	public char getSeparatorChar() {
		return File.separatorChar;
	}

	@Override
	public String getName() {
		return "Localhost"; //$NON-NLS-1$
	}

	@Override
	public String convertPathToString(IPath path) {
		return EnvironmentPathUtils.getLocalPath(path).toOSString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return (T) Platform.getAdapterManager().loadAdapter(this,
				adapter.getName());
	}

	@Override
	public URI getURI(IPath location) {
		return URIUtil.toURI(location);
	}

	@Override
	public IFileHandle getFile(URI locationURI) {
		if (locationURI != null
				&& LocalEnvironmentProvider.FILE_SCHEME.equals(locationURI
						.getScheme())) {
			return new EFSFileHandle(this, fs.getStore(locationURI));
		} else {
			return null;
		}
	}

	@Override
	public String getPathsSeparator() {
		return Character.toString(getPathsSeparatorChar());
	}

	@Override
	public char getPathsSeparatorChar() {
		return Platform.getOS().equals(Platform.OS_WIN32) ? ';' : ':';
	}

	@Override
	public String getCanonicalPath(IPath path) {
		try {
			return path.toFile().getCanonicalFile().toString();
		} catch (IOException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			return path.toOSString();
		}
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean isConnected() {
		return true;
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean connect() {
		return true;
	}

}
