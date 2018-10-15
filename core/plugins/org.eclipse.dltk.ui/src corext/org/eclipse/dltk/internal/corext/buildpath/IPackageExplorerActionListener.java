/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/

package org.eclipse.dltk.internal.corext.buildpath;

import org.eclipse.dltk.internal.ui.wizards.buildpath.newsourcepage.DialogPackageExplorerActionGroup;

/**
 * Interface for listeners of <code>PackageExplorerActionEvent</code>.
 */
public interface IPackageExplorerActionListener {
    
    /**
     * Handle the <code>PackageExplorerActionEvent</code> which is fired 
     * whenever the set of available actions changes.
     * 
     * @param event event to be processed
     * 
     * @see PackageExplorerActionEvent
     * @see DialogPackageExplorerActionGroup#addListener(IPackageExplorerActionListener)
     */
    public void handlePackageExplorerActionEvent(PackageExplorerActionEvent event);
    
}
