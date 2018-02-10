/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.dltk.internal.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
		Charset charset = storage.getAdapter(Charset.class);
		IProjectFragment projectFragment = this.getProjectFragment();
		if (charset == null) {
			if (projectFragment.isArchive())
				charset = StandardCharsets.UTF_8;
		}
		PerformanceNode p = RuntimePerformanceMonitor.begin();
		long length = 0;
		try {
			try {
				InputStream stream = null;
				try {
					stream = storage.getContents();
					char[] rv = Util.getInputStreamAsCharArray(stream, -1,
							charset == null ? null : charset.name());
					length = rv.length;
					return rv;
				} finally {
					if (stream != null)
						stream.close();
				}
			} catch (CoreException e) {
				throw new ModelException(e,
						IModelStatusConstants.ELEMENT_DOES_NOT_EXIST);
			}
		} catch (IOException e) {
			throw new ModelException(e, IModelStatusConstants.IO_EXCEPTION);
		} finally {
			p.done("#", RuntimePerformanceMonitor.IOREAD, length);
		}
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
