/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/

package org.eclipse.dltk.internal.ui.search;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.search.IQueryParticipant;

public class SearchParticipantDescriptor {
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String NATURE = "nature"; //$NON-NLS-1$
	private static final String LANGUAGE = "language"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$

	private IConfigurationElement fConfigurationElement;
	private boolean fEnabled; //

	protected SearchParticipantDescriptor(IConfigurationElement configElement) {
		fConfigurationElement = configElement;
		fEnabled = true;
	}

	/**
	 * checks whether a participant has all the proper attributes.
	 *
	 * @return returns a status describing the result of the validation
	 */
	protected IStatus checkSyntax() {
		if (fConfigurationElement.getAttribute(ID) == null) {
			String format = SearchMessages.SearchParticipant_error_noID;
			String message = MessageFormat.format(format,
					fConfigurationElement.getDeclaringExtension().getUniqueIdentifier());
			return new Status(IStatus.ERROR, DLTKUIPlugin.getPluginId(), 0, message, null);
		}

		if (fConfigurationElement.getAttribute(LANGUAGE) == null) {
			String format = SearchMessages.SearchParticipant_error_noLanguage;
			String message = MessageFormat.format(format, fConfigurationElement.getAttribute(ID));
			return new Status(IStatus.ERROR, DLTKUIPlugin.getPluginId(), 0, message, null);
		}

		if (fConfigurationElement.getAttribute(NATURE) == null) {
			String format = SearchMessages.SearchParticipant_error_noNature;
			String message = MessageFormat.format(format, fConfigurationElement.getAttribute(ID));
			return new Status(IStatus.ERROR, DLTKUIPlugin.getPluginId(), 0, message, null);
		}

		if (fConfigurationElement.getAttribute(CLASS) == null) {
			String format = SearchMessages.SearchParticipant_error_noClass;
			String message = MessageFormat.format(format, fConfigurationElement.getAttribute(ID));
			return new Status(IStatus.ERROR, DLTKUIPlugin.getPluginId(), 0, message, null);
		}
		return Status.OK_STATUS;
	}

	public String getID() {
		return fConfigurationElement.getAttribute(ID);
	}

	public void disable() {
		fEnabled = false;
	}

	public boolean isEnabled() {
		return fEnabled;
	}

	protected IQueryParticipant create() throws CoreException {
		try {
			return (IQueryParticipant) fConfigurationElement.createExecutableExtension(CLASS);
		} catch (ClassCastException e) {
			throw new CoreException(new Status(IStatus.ERROR, DLTKUIPlugin.getPluginId(), 0,
					SearchMessages.SearchParticipant_error_classCast, e));
		}
	}

	protected String getNature() {
		return fConfigurationElement.getAttribute(NATURE);
	}

	protected String getLanguage() {
		return fConfigurationElement.getAttribute(LANGUAGE);
	}

}
