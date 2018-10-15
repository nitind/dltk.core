/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index2;

import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.index2.ProjectIndexer2;

/**
 * Request for indexing builtin (special) project fragment
 * 
 * @author michael
 * 
 */
public class BuiltinProjectFragmentRequest extends
		ExternalProjectFragmentRequest {

	public BuiltinProjectFragmentRequest(ProjectIndexer2 indexer,
			IProjectFragment fragment, ProgressJob progressJob) {
		super(indexer, fragment, progressJob);
	}

	@Override
	protected IEnvironment getEnvironment() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BuiltinProjectFragmentRequest) {
			return super.equals(obj);
		}
		return false;
	}
}
