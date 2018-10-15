/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.caching;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;

public class WrapTimeStampHandle implements IFileHandle {
	IFileHandle handle;
	long timestamp;

	public WrapTimeStampHandle(IFileHandle handle, long timestamp) {
		this.handle = handle;
		this.timestamp = timestamp;
	}

	@Override
	public boolean exists() {
		return this.handle.exists();
	}

	@Override
	public String getCanonicalPath() {
		return this.handle.getCanonicalPath();
	}

	@Override
	public IFileHandle getChild(String path) {
		return this.handle.getChild(path);
	}

	@Override
	public IFileHandle[] getChildren() {
		return this.handle.getChildren();
	}

	@Override
	public IEnvironment getEnvironment() {
		return this.handle.getEnvironment();
	}

	@Override
	public String getEnvironmentId() {
		return this.handle.getEnvironmentId();
	}

	@Override
	public IPath getFullPath() {
		return this.handle.getFullPath();
	}

	@Override
	public String getName() {
		return this.handle.getName();
	}

	@Override
	public IFileHandle getParent() {
		return this.handle.getParent();
	}

	@Override
	public IPath getPath() {
		return this.handle.getPath();
	}

	@Override
	public boolean isDirectory() {
		return this.handle.isDirectory();
	}

	@Override
	public boolean isFile() {
		return this.handle.isFile();
	}

	@Override
	public boolean isSymlink() {
		return this.handle.isSymlink();
	}

	@Override
	public long lastModified() {
		return this.timestamp;
	}

	@Override
	public long length() {
		return this.handle.length();
	}

	@Override
	public InputStream openInputStream(IProgressMonitor monitor)
			throws IOException {
		return this.handle.openInputStream(monitor);
	}

	@Override
	public OutputStream openOutputStream(IProgressMonitor monitor)
			throws IOException {
		return this.handle.openOutputStream(monitor);
	}

	@Override
	public String toOSString() {
		return this.handle.toOSString();
	}

	@Override
	public URI toURI() {
		return this.handle.toURI();
	}

	@Override
	public void move(IFileHandle destination) throws CoreException {
		this.handle.move(destination);
	}
}
