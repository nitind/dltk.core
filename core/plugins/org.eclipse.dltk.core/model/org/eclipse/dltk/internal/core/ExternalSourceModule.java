/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.dltk.internal.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IModelStatusConstants;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.RuntimePerformanceMonitor;
import org.eclipse.dltk.core.RuntimePerformanceMonitor.PerformanceNode;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IFileHandle;

/**
 * Represents an external source module.
 */
public class ExternalSourceModule extends AbstractExternalSourceModule {

	private IStorage storage;

	public ExternalSourceModule(ModelElement parent, String name,
			WorkingCopyOwner owner, IStorage storage) {
		super(parent, name, owner);
		this.storage = storage;
	}

	public IStorage getStorage() {
		return storage;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExternalSourceModule)) {
			return false;
		}

		return super.equals(obj);
	}

	@Override
	public InputStream getContents() throws CoreException {
		return storage.getContents();
	}

	@Override
	public String getFileName() {
		return getPath().toOSString();
	}

	@Override
	public IPath getFullPath() {
		if (this.storage != null) {
			return storage.getFullPath();
		} else {
			return getPath();
		}
	}

	@Override
	public String getName() {
		return storage.getName();
	}

	@Override
	public IResource getResource() {
		return null;
	}

	@Override
	protected char[] getBufferContent() throws ModelException {
		IPath path = getBufferPath();
		IFileHandle file = EnvironmentPathUtils.getFile(path);

		IProjectFragment projectFragment = this.getProjectFragment();
		if (file != null && file.exists() && !projectFragment.isArchive()) {
			return org.eclipse.dltk.internal.core.util.Util
					.getResourceContentsAsCharArray(file);
		} else {
			if (projectFragment.isArchive()) {
				final InputStream stream;
				PerformanceNode p = RuntimePerformanceMonitor.begin();
				try {
					stream = new BufferedInputStream(storage.getContents(),
							4096);
				} catch (CoreException e) {
					throw new ModelException(e,
							IModelStatusConstants.ELEMENT_DOES_NOT_EXIST);
				}
				try {
					char[] data = Util.getInputStreamAsCharArray(stream, -1,
							Util.UTF_8);
					p.done("#", RuntimePerformanceMonitor.IOREAD, data.length);
					return data;
				} catch (IOException e) {
					throw new ModelException(e,
							IModelStatusConstants.IO_EXCEPTION);
				} finally {
					try {
						stream.close();
					} catch (IOException e) {
						// ignore
					}
				}
			}
		}
		throw newNotPresentException();
	}

	/**
	 * Return buffer path in full mode
	 */
	protected IPath getBufferPath() {
		return getPath();
	}

	@Override
	protected String getModuleType() {
		return "DLTK External Source Module"; //$NON-NLS-1$
	}

	@Override
	protected String getNatureId() {
		IPath path = getFullPath();
		IDLTKLanguageToolkit toolkit = lookupLanguageToolkit(path);
		if (toolkit == null) {
			toolkit = DLTKLanguageManager
					.getLanguageToolkit(getScriptProject());
		}
		return (toolkit != null) ? toolkit.getNatureId() : null;
	}

	@Override
	protected ISourceModule getOriginalSourceModule() {
		return new ExternalSourceModule((ModelElement) getParent(),
				getElementName(), DefaultWorkingCopyOwner.PRIMARY, storage);
	}
}
