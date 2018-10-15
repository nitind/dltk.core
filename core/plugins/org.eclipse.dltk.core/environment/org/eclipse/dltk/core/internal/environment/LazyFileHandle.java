/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.  
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.core.internal.environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.utils.PlatformFileUtils;

/**
 * @since 2.0
 */
public class LazyFileHandle implements IFileHandle {
	private final String environment;
	private final IPath path;
	private IFileHandle handle = null;

	public LazyFileHandle(String environment, IPath path) {
		this.environment = Util.EMPTY_STRING.equals(environment) ? LocalEnvironment.ENVIRONMENT_ID
				: environment;
		this.path = path;
	}

	private void initialize() {
		if (handle == null) {
			IEnvironment environment = resolveEnvironment();
			if (environment != null) {
				handle = PlatformFileUtils.findAbsoluteOrEclipseRelativeFile(
						environment, this.path);
			}
		}
	}

	private IEnvironment resolveEnvironment() {
		final IEnvironment environment = EnvironmentManager
				.getEnvironmentById(this.environment);
		if (environment instanceof LazyEnvironment) {
			return null;
		}
		return environment;
	}

	/**
	 * Support containers on load are always exists.
	 */
	@Override
	public boolean exists() {
		if (handle != null) {
			return this.handle.exists();
		}
		IEnvironment environment = resolveEnvironment();
		if (environment == null || !environment.connect()) {
			// Assume location exists if environment is not connected
			return true;
		}
		initialize();
		if (handle != null) {
			return this.handle.exists();
		}
		return true;
	}

	@Override
	public String getCanonicalPath() {
		initialize();
		if (handle != null) {
			return this.handle.getCanonicalPath();
		}
		return null;
	}

	@Override
	public IFileHandle getChild(String bundlePath) {
		initialize();
		if (handle != null) {
			return this.handle.getChild(bundlePath);
		}
		return null;
	}

	@Override
	public IFileHandle[] getChildren() {
		initialize();
		if (handle != null) {
			return this.handle.getChildren();
		}
		return null;
	}

	@Override
	public IEnvironment getEnvironment() {
		initialize();
		if (handle != null) {
			return this.handle.getEnvironment();
		}
		return EnvironmentManager.getEnvironmentById(environment);
	}

	@Override
	public IPath getFullPath() {
		// it is always possible to reconstruct full path back.
		return EnvironmentPathUtils.getFullPath(environment, path);
	}

	@Override
	public String getName() {
		initialize();
		if (handle != null) {
			return this.handle.getName();
		}
		return path.lastSegment();
	}

	@Override
	public IFileHandle getParent() {
		initialize();
		if (handle != null) {
			return this.handle.getParent();
		}
		if (path.segmentCount() > 0) {
			return new LazyFileHandle(environment, path.removeLastSegments(1));
		}
		return null;
	}

	@Override
	public IPath getPath() {
		return this.path;
	}

	@Override
	public boolean isDirectory() {
		initialize();
		if (handle != null) {
			return this.handle.isDirectory();
		}
		return false;
	}

	@Override
	public boolean isFile() {
		initialize();
		if (handle != null) {
			return this.handle.isFile();
		}
		return false;
	}

	@Override
	public boolean isSymlink() {
		initialize();
		if (handle != null) {
			return this.handle.isSymlink();
		}
		return false;
	}

	@Override
	public long lastModified() {
		initialize();
		if (handle != null) {
			return this.handle.lastModified();
		}
		return 0;
	}

	@Override
	public long length() {
		initialize();
		if (handle != null) {
			return this.handle.length();
		}
		return 0;
	}

	@Override
	public InputStream openInputStream(IProgressMonitor monitor)
			throws IOException {
		initialize();
		if (handle != null) {
			return this.handle.openInputStream(monitor);
		}
		return null;
	}

	@Override
	public OutputStream openOutputStream(IProgressMonitor monitor)
			throws IOException {
		initialize();
		if (handle != null) {
			return this.handle.openOutputStream(monitor);
		}
		throw new IOException("Error opening " + getFullPath()); //$NON-NLS-1$
	}

	@Override
	public String toOSString() {
		IEnvironment environment = resolveEnvironment();
		if (environment != null) {
			IPath newPath = PlatformFileUtils
					.findAbsoluteOrEclipseRelativeFile(environment, this.path)
					.getPath();
			return environment.convertPathToString(newPath);
		}
		initialize();
		if (this.handle != null) {
			return this.handle.toOSString();
		}
		return Util.EMPTY_STRING;
	}

	@Override
	public URI toURI() {
		initialize();
		if (handle != null) {
			return this.handle.toURI();
		}
		return null;
	}

	@Override
	public String getEnvironmentId() {
		return this.environment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((environment == null) ? 0 : environment.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof IFileHandle))
			return false;
		IFileHandle other = (IFileHandle) obj;
		if (environment == null) {
			if (other.getEnvironment() != null)
				return false;
		} else {
			final IEnvironment otherEnvironment = other.getEnvironment();
			if (otherEnvironment == null
					|| !environment.equals(otherEnvironment.getId()))
				return false;
		}
		if (path == null) {
			if (other.getPath() != null)
				return false;
		} else if (!path.equals(other.getPath()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		initialize();
		if (handle != null) {
			return this.handle.toString();
		}
		return "[UNRESOLVED]" + getFullPath(); //$NON-NLS-1$
	}

	@Override
	public void move(IFileHandle destination) throws CoreException {
		initialize();
		if (handle != null) {
			handle.move(destination);
		} else {
			throw new CoreException(new Status(IStatus.ERROR,
					DLTKCore.PLUGIN_ID, "Error resolving " + getFullPath())); //$NON-NLS-1$
		}
	}
}
