/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.internal.ui;

import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.actions.IActionFilterTester;

public class ModelElementNatureTest implements IActionFilterTester {

	@Override
	public boolean test(Object target, String name, String value) {
		if (target instanceof IModelElement) {
			final IDLTKLanguageToolkit toolkit = DLTKLanguageManager
					.getLanguageToolkit((IModelElement) target);
			if (toolkit != null) {
				return toolkit.getNatureId().equals(value);
			}
		}
		return false;
	}

}
