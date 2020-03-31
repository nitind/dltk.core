/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.ui.IContainmentAdapter;

public class DLTKElementContainmentAdapter implements IContainmentAdapter {

	@Override
	public boolean contains(Object workingSetElement, Object element, int flags) {
		if (!(workingSetElement instanceof IModelElement) || element == null)
			return false;

		IModelElement workingSetModelElement = (IModelElement) workingSetElement;
		IResource resource = null;
		IModelElement jElement = null;
		if (element instanceof IModelElement) {
			jElement = (IModelElement) element;
			resource = jElement.getResource();
		} else {
			if (element instanceof IAdaptable) {
				resource = ((IAdaptable) element).getAdapter(IResource.class);
				if (resource != null) {
					jElement = findElement(resource);
					;
					if (jElement != null && !jElement.exists())
						jElement = null;
				}
			}
		}

		if (jElement != null) {
			if (contains(workingSetModelElement, jElement, flags))
				return true;
			if (workingSetModelElement.getElementType() == IModelElement.PROJECT_FRAGMENT
					&& resource.getType() == IResource.FOLDER && checkIfDescendant(flags))
				return isChild(workingSetModelElement, resource);
		} else if (resource != null) {
			return contains(workingSetModelElement, resource, flags);
		}
		return false;
	}

	private IModelElement findElement(IResource resource) {
		IScriptProject scriptProject = DLTKCore.create(resource.getProject());
		if (scriptProject == null) {
			return null;
		}
		IModelElement element = ModelManager.create(resource, scriptProject);
		if (element != null && element.exists()) {
			return element;
		}
		return null;
	}

	private boolean contains(IModelElement workingSetElement, IModelElement element, int flags) {
		if (checkContext(flags) && workingSetElement.equals(element)) {
			return true;
		}
		if (checkIfChild(flags) && workingSetElement.equals(element.getParent())) {
			return true;
		}
		if (checkIfDescendant(flags) && check(workingSetElement, element)) {
			return true;
		}
		if (checkIfAncestor(flags) && check(element, workingSetElement)) {
			return true;
		}
		return false;
	}

	private boolean check(IModelElement ancestor, IModelElement descendent) {
		descendent = descendent.getParent();
		while (descendent != null) {
			if (ancestor.equals(descendent))
				return true;
			descendent = descendent.getParent();
		}
		return false;
	}

	private boolean isChild(IModelElement workingSetElement, IResource element) {
		IResource resource = workingSetElement.getResource();
		if (resource == null)
			return false;
		return check(element, resource);
	}

	private boolean contains(IModelElement workingSetElement, IResource element, int flags) {
		IResource workingSetResource = workingSetElement.getResource();
		if (workingSetResource == null)
			return false;
		if (checkContext(flags) && workingSetResource.equals(element)) {
			return true;
		}
		if (checkIfChild(flags) && workingSetResource.equals(element.getParent())) {
			return true;
		}
		if (checkIfDescendant(flags) && check(workingSetResource, element)) {
			return true;
		}
		if (checkIfAncestor(flags) && check(element, workingSetResource)) {
			return true;
		}
		return false;
	}

	private boolean check(IResource ancestor, IResource descendent) {
		descendent = descendent.getParent();
		while (descendent != null) {
			if (ancestor.equals(descendent))
				return true;
			descendent = descendent.getParent();
		}
		return false;
	}

	private boolean checkIfDescendant(int flags) {
		return (flags & CHECK_IF_DESCENDANT) != 0;
	}

	private boolean checkIfAncestor(int flags) {
		return (flags & CHECK_IF_ANCESTOR) != 0;
	}

	private boolean checkIfChild(int flags) {
		return (flags & CHECK_IF_CHILD) != 0;
	}

	private boolean checkContext(int flags) {
		return (flags & CHECK_CONTEXT) != 0;
	}
}
