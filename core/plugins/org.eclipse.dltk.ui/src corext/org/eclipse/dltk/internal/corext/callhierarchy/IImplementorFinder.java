/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 * 			(report 36180: Callers/Callees view)
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.callhierarchy;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IType;


public interface IImplementorFinder {
	
    /**
     * Find implementors of the specified IType instance.
     */
    public abstract Collection findImplementingTypes(IType type,
        IProgressMonitor progressMonitor);

//    /**
//     * Find interfaces which are implemented by the specified IType instance.
//     */
//    public abstract Collection findInterfaces(IType type, IProgressMonitor progressMonitor);
}
