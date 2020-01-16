/*******************************************************************************
 * Copyright (c) 2020 Dawid Pakuła and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.dialogs;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.dltk.ui.dialogs.TypeSelectionExtension;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.swt.widgets.Shell;

public class OpenTypeSelectionDialog extends FilteredTypesSelectionDialog {

	private static final String DIALOG_SETTINGS = "OpenTypeSelectionDialog"; //$NON-NLS-1$

	public OpenTypeSelectionDialog(Shell shell, boolean multi, IRunnableContext context, IDLTKSearchScope scope,
			int elementKinds, IDLTKUILanguageToolkit toolkit) {
		this(shell, multi, context, scope, elementKinds, null, toolkit);
	}

	public OpenTypeSelectionDialog(Shell shell, boolean multi, IRunnableContext context, IDLTKSearchScope scope,
			int elementKinds, TypeSelectionExtension extension, IDLTKUILanguageToolkit toolkit) {
		super(shell, multi, context, scope, elementKinds, extension, toolkit.getCoreToolkit());
	}

	public OpenTypeSelectionDialog(Shell shell, boolean multi, IRunnableContext context, IDLTKSearchScope scope,
			int elementKinds, IDLTKLanguageToolkit toolkit) {
		this(shell, multi, context, scope, elementKinds, null, toolkit);
	}

	public OpenTypeSelectionDialog(Shell shell, boolean multi, IRunnableContext context, IDLTKSearchScope scope,
			int elementKinds, TypeSelectionExtension extension, IDLTKLanguageToolkit toolkit) {
		super(shell, multi, context, scope, elementKinds, extension, toolkit);
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = DLTKUIPlugin.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(DIALOG_SETTINGS);
		if (section == null) {
			section = settings.addNewSection(DIALOG_SETTINGS);
		}
		return section;
	}
}
