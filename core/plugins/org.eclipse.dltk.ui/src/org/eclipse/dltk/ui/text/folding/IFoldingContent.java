/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
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
package org.eclipse.dltk.ui.text.folding;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.jface.text.IRegion;

public interface IFoldingContent extends IModuleSource {

	String get(int offset, int length);

	String get(IRegion region);

	String substring(int beginIndex, int endIndex);

}
