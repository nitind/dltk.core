/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *          (report 36180: Callers/Callees view)
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.callhierarchy;

public abstract class CallHierarchyVisitor {
    public void preVisit(MethodWrapper methodWrapper) {
    }
    
    public void postVisit(MethodWrapper methodWrapper) {
    }
    
    public boolean visit(MethodWrapper methodWrapper) {
        return true;
    }
}
