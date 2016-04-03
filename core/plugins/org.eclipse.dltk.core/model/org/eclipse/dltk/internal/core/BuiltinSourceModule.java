/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.dltk.internal.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.WorkingCopyOwner;

/**
 * Represents a builtin source module.
 */
public class BuiltinSourceModule extends AbstractExternalSourceModule implements
		org.eclipse.dltk.compiler.env.ISourceModule {

	public BuiltinSourceModule(BuiltinScriptFolder parent, String name,
			WorkingCopyOwner owner) {
		super(parent, name, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BuiltinSourceModule)) {
			return false;
		}

		return super.equals(obj);
	}

	@Override
	public InputStream getContents() {
		String builtinModuleContent = getSourceModuleContent();
		if (builtinModuleContent == null) {
			return new ByteArrayInputStream(new byte[0]);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(
				builtinModuleContent.getBytes());
		return input;
	}

	@Override
	public String getFileName() {
		return this.getPath().toOSString();
	}

	@Override
	public IPath getFullPath() {
		return new Path(getName());
	}

	@Override
	public String getName() {
		return getElementName();
	}

	@Override
	public boolean isBuiltin() {
		return true;
	}

	@Override
	protected char[] getBufferContent() {
		String content = getSourceModuleContent();
		if (content != null) {
			return content.toCharArray();
		}

		return new char[0];
	}

	@Override
	protected String getModuleType() {
		return "DLTK Builtin Source Module"; //$NON-NLS-1$
	}

	@Override
	protected String getNatureId() {
		IScriptProject project = getScriptProject();
		IDLTKLanguageToolkit toolkit = lookupLanguageToolkit(project);
		return toolkit != null ? toolkit.getNatureId() : null;
	}

	@Override
	protected ISourceModule getOriginalSourceModule() {
		return new BuiltinSourceModule((BuiltinScriptFolder) getParent(),
				getElementName(), DefaultWorkingCopyOwner.PRIMARY);
	}

	private String getSourceModuleContent() {
		BuiltinProjectFragment fragment = (BuiltinProjectFragment) getProjectFragment();
		String builtinModuleContent = fragment.builtinProvider
				.getBuiltinModuleContent(getName());
		return builtinModuleContent;
	}
}
