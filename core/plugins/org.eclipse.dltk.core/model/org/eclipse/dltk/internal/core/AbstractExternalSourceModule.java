/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKContentTypeManager;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IExternalSourceModule;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelStatus;
import org.eclipse.dltk.core.IModelStatusConstants;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;

/**
 * Base class for all external source module representations.
 */
public abstract class AbstractExternalSourceModule extends AbstractSourceModule
		implements IExternalSourceModule {

	protected AbstractExternalSourceModule(ModelElement parent, String name,
			WorkingCopyOwner owner) {
		super(parent, name, owner);
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Override
	public void becomeWorkingCopy(IProblemRequestor problemRequestor,
			IProgressMonitor monitor) {
		// external, do nothing
	}

	@Override
	public void codeComplete(int offset, CompletionRequestor requestor) {
		// external, do nothing
	}

	@Override
	public void codeComplete(int offset, CompletionRequestor requestor,
			WorkingCopyOwner owner) {
		// external, do nothing
	}

	@Override
	public void commitWorkingCopy(boolean force, IProgressMonitor monitor)
			throws ModelException {
		throw new ModelException(new ModelStatus(
				IModelStatusConstants.INVALID_ELEMENT_TYPES, this));
	}

	@Override
	public void delete(boolean force, IProgressMonitor monitor) {
		// external, do nothing
	}

	@Override
	public void discardWorkingCopy() {
		// external, do nothing
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == IStorage.class) {
			return (T) this;
		}

		return super.getAdapter(adapter);
	}

	@Override
	public IResource getResource() {
		return null;
	}

	@Override
	public ISourceModule getWorkingCopy(WorkingCopyOwner workingCopyOwner,
			IProblemRequestor problemRequestor, IProgressMonitor monitor) {
		return this;
	}

	@Override
	public boolean isWorkingCopy() {
		return true;
	}

	@Override
	protected boolean hasBuffer() {
		return false;
	}

	@Override
	public void makeConsistent(IProgressMonitor monitor) throws ModelException {
		// makeConsistent(false/*don't create AST*/, 0, monitor);
		openWhenClosed(createElementInfo(), monitor);
	}

	@Override
	public void move(IModelElement container, IModelElement sibling,
			String rename, boolean replace, IProgressMonitor monitor)
			throws ModelException {
		// this may be slightly misleading to the user...
		copy(container, sibling, rename, replace, monitor);
	}

	@Override
	public void reconcile(boolean forceProblemDetection,
			WorkingCopyOwner workingCopyOwner, IProgressMonitor monitor) {
		// external, do nothing
	}

	@Override
	public void rename(String name, boolean replace, IProgressMonitor monitor) {
		// external, do nothing
	}

	@Override
	protected void closing(Object info) {
		// lifetime of the working copy
	}

	@Override
	protected IStatus validateSourceModule(IDLTKLanguageToolkit toolkit,
			IResource resource) {
		// external, resource will always be null
		IPath path = getFullPath();
		if (toolkit == null) {
			toolkit = DLTKLanguageManager.findToolkit(path);
		}

		if (toolkit != null) {
			if (DLTKContentTypeManager.isValidFileNameForContentType(toolkit,
					path)) {
				return IModelStatus.VERIFIED_OK;
			}
		}

		return null;
	}
}
