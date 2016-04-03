/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

public abstract class DLTKContributedExtension implements
		IDLTKContributedExtension, IExecutableExtension {

	private String description;
	private String id;
	private String name;
	private String natureId;
	private String propertyPageId;
	private String preferencePageId;
	private int priority;
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNatureId() {
		return natureId;
	}

	@Override
	public String getPreferencePageId() {
		return preferencePageId;
	}

	@Override
	public int getPriority() {
		return priority;
	}
	
	@Override
	public String getPropertyPageId() {
		return propertyPageId;
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) {
		id = config.getAttribute(ID);
		name = config.getAttribute(NAME);
		description = config.getAttribute(DESCRIPTION);
		priority = Integer.parseInt(config.getAttribute(PRIORITY));
		
		propertyPageId = config.getAttribute(PROP_PAGE_ID);
		preferencePageId = config.getAttribute(PREF_PAGE_ID);

		// get the natureId from the parent
		final Object parent = config.getParent();
		if (parent instanceof IConfigurationElement)
			natureId = ((IConfigurationElement) parent).getAttribute(NATURE_ID);
	}
}
