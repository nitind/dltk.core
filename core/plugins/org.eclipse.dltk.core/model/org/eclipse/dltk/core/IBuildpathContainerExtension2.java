/*******************************************************************************
 * Copyright (c) 2014 Alex Panchenko
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Alex Panchenko - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.core;

/**
 * @since 5.1
 */
public interface IBuildpathContainerExtension2 extends IBuildpathContainer {

	IModelStatus validate();

}
