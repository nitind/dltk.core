/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.IBuffer;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.WorkingCopyOwner;


/**
 * A working copy owner that creates internal buffers.
 */
public class DefaultWorkingCopyOwner extends WorkingCopyOwner {
	
	public WorkingCopyOwner primaryBufferProvider;
		
	public static final DefaultWorkingCopyOwner PRIMARY =  new DefaultWorkingCopyOwner();
	
	private DefaultWorkingCopyOwner() {
		// only one instance can be created
	}
	
	/**
	 * @deprecated Marked deprecated as it is using deprecated code
	 */
	@Deprecated
	@Override
	public IBuffer createBuffer(ISourceModule workingCopy) {
		if (this.primaryBufferProvider != null) return this.primaryBufferProvider.createBuffer(workingCopy);
		return super.createBuffer(workingCopy);
	}

	@Override
	public String toString() {
		return "Primary owner"; //$NON-NLS-1$
	}
}
