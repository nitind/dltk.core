/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.launching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.IRuntimeBuildpathEntry;
import org.eclipse.dltk.launching.IRuntimeBuildpathEntry2;
import org.eclipse.dltk.launching.IRuntimeBuildpathEntryResolver;
import org.eclipse.dltk.launching.ScriptRuntime;

/**
 * Default resolver for a contributed buildpath entry
 */
public class DefaultEntryResolver implements IRuntimeBuildpathEntryResolver {

	@Override
	public IRuntimeBuildpathEntry[] resolveRuntimeBuildpathEntry(
			IRuntimeBuildpathEntry entry, ILaunchConfiguration configuration)
			throws CoreException {
		IRuntimeBuildpathEntry2 entry2 = (IRuntimeBuildpathEntry2) entry;
		IRuntimeBuildpathEntry[] entries = entry2
				.getRuntimeBuildpathEntries(configuration);
		List<IRuntimeBuildpathEntry> resolved = new ArrayList<>();
		for (int i = 0; i < entries.length; i++) {
			IRuntimeBuildpathEntry[] temp = ScriptRuntime
					.resolveRuntimeBuildpathEntry(entries[i], configuration);
			for (int j = 0; j < temp.length; j++) {
				resolved.add(temp[j]);
			}
		}
		return resolved.toArray(new IRuntimeBuildpathEntry[resolved.size()]);
	}

	@Override
	public IRuntimeBuildpathEntry[] resolveRuntimeBuildpathEntry(
			IRuntimeBuildpathEntry entry, IScriptProject project)
			throws CoreException {
		IRuntimeBuildpathEntry2 entry2 = (IRuntimeBuildpathEntry2) entry;
		IRuntimeBuildpathEntry[] entries = entry2
				.getRuntimeBuildpathEntries(null);
		List<IRuntimeBuildpathEntry> resolved = new ArrayList<>();
		for (int i = 0; i < entries.length; i++) {
			IRuntimeBuildpathEntry[] temp = ScriptRuntime
					.resolveRuntimeBuildpathEntry(entries[i], project);
			for (int j = 0; j < temp.length; j++) {
				resolved.add(temp[j]);
			}
		}
		return resolved.toArray(new IRuntimeBuildpathEntry[resolved.size()]);
	}

	@Override
	public IInterpreterInstall resolveInterpreterInstall(String lang,
			String environment, IBuildpathEntry entry) throws CoreException {
		return null;
	}
}
