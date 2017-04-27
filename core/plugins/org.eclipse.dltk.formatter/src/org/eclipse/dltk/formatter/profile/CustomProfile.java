/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     xored software, Inc. - initial API and Implementation (Yuri Strot)
 *******************************************************************************/
package org.eclipse.dltk.formatter.profile;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ui.formatter.IProfile;

/**
 * Represents a user-defined profile. A custom profile can be modified after
 * instantiation.
 */
public class CustomProfile extends Profile implements IProfile.ICustomProfile {

	public CustomProfile(String name, Map<String, String> settings,
			String formatter, int version) {
		fName = name;
		fSettings = settings;
		fFormatter = formatter;
		fVersion = version;
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public Map<String, String> getSettings() {
		return new HashMap<>(fSettings);
	}

	@Override
	public void setSettings(Map<String, String> settings) {
		if (settings == null)
			throw new IllegalArgumentException();
		fSettings = settings;
	}

	@Override
	public String getID() {
		return fName;
	}

	@Override
	public int getVersion() {
		return fVersion;
	}

	@Override
	public void setVersion(int version) {
		fVersion = version;
	}

	@Override
	public int compareTo(IProfile o) {
		if (o instanceof CustomProfile) {
			return getName().compareToIgnoreCase(o.getName());
		}
		return 1;
	}

	public boolean isProfileToSave() {
		return true;
	}

	@Override
	public String getFormatterId() {
		return fFormatter;
	}

	private String fFormatter;
	String fName;
	private Map<String, String> fSettings;
	// protected ProfileManager fManager;
	private int fVersion;

}
