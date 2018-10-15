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
package org.eclipse.dltk.ui.wizards;

import org.eclipse.swt.widgets.Composite;

/**
 * Substantially different mode of {@link NewSourceModuleWizard} operation, the
 * set of possible values is returned by
 * {@link ISourceModuleWizardExtension#getModes()}.
 * 
 * <p>
 * <strong>EXPERIMENTAL</strong>. This interface has been added as part of a
 * work in progress. There is no guarantee that this API will remain the same.
 * </p>
 * 
 * @since 2.0
 */
public interface ISourceModuleWizardMode {

	String getId();

	String getName();

	void createControl(Composite parent, int columns);

	/**
	 * Notifies this template if it was enabled
	 * 
	 * @param enabled
	 */
	void setEnabled(boolean enabled);

}
