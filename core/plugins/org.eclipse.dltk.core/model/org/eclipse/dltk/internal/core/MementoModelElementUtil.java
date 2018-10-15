/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
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
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementMemento;
import org.eclipse.dltk.core.IParent;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.internal.core.util.MementoTokenizer;

public class MementoModelElementUtil {
	public static IModelElement getHandleFromMemento(MementoTokenizer memento,
			IParent parent, WorkingCopyOwner owner) {
		String token = null;
		String name = "";
		while (memento.hasMoreTokens()) {
			token = memento.nextToken();
			char firstChar = token.charAt(0);
			if (ModelElement.JEM_USER_ELEMENT_ENDING.indexOf(firstChar) == -1
					&& firstChar != ModelElement.JEM_COUNT) {
				name += token;
			} else {
				break;
			}
		}
		if (!memento.hasMoreTokens()) {
			token = null;
		}
		try {
			IModelElement[] children = parent.getChildren();
			for (int i = 0; i < children.length; i++) {
				if (name.equals(children[i].getElementName())
						&& children[i] instanceof IModelElementMemento) {
					IModelElementMemento childMemento = (IModelElementMemento) children[i];
					if (token == null) {
						return childMemento
								.getHandleFromMemento(memento, owner);
					} else {
						return childMemento.getHandleFromMemento(token,
								memento, owner);
					}
				}
			}
		} catch (ModelException e) {
			DLTKCore.error("Incorrect handle resolving", e);
		}
		return null;
	}
}
