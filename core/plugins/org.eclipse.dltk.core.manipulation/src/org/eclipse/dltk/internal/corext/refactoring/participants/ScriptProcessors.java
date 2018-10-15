/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring.participants;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;

/**
 * Utility class to deal with Script element processors.
 */
public class ScriptProcessors {

	public static String[] computeAffectedNatures(IModelElement element) throws CoreException {
		if (element instanceof IMember) {
			// IMember member= (IMember)element;

		}
		IScriptProject project = element.getScriptProject();
		return ResourceProcessors.computeAffectedNatures(project.getProject());
	}

	public static String[] computeAffectedNaturs(IModelElement[] elements) throws CoreException {
		Set<String> result = new HashSet<>();
		for (int i = 0; i < elements.length; i++) {
			String[] natures = computeAffectedNatures(elements[i]);
			for (int j = 0; j < natures.length; j++) {
				result.add(natures[j]);
			}
		}
		return result.toArray(new String[result.size()]);
	}
}
