/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
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
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IType;


public class DLTKImplementorFinder implements IImplementorFinder {
	@Override
	public Collection findImplementingTypes(IType type, IProgressMonitor progressMonitor) {
		if (DLTKCore.DEBUG) {
			System.err.println("TODO: Add findImplementinTypes call"); //$NON-NLS-1$
		}
//        ITypeHierarchy typeHierarchy;
//
//        try {
//            typeHierarchy = type.newTypeHierarchy(progressMonitor);
//
//            IType[] implementingTypes = typeHierarchy.getAllClasses();
//            HashSet result = new HashSet(Arrays.asList(implementingTypes));
//
//            return result;
//        } catch (ModelException e) {
//            DLTKUIPlugin.log(e);
//        }

        return null;
    }
}
