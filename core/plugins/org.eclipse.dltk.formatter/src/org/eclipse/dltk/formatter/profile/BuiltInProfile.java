/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     xored software, Inc. - initial API and Implementation (Yuri Strot) 
 *******************************************************************************/
package org.eclipse.dltk.formatter.profile;

import java.util.Collections;
import java.util.Map;

import org.eclipse.dltk.ui.formatter.IProfile;

/**
 * Represents a built-in profile. The state of a built-in profile cannot be
 * changed after instantiation.
 */
public class BuiltInProfile extends Profile {

	public BuiltInProfile(String ID, String name, Map<String, String> settings,
			int order, String formatter, int currentVersion) {
		fName = name;
		fID = ID;
		fSettings = Collections.unmodifiableMap(settings);
		fOrder = order;
		fFormatter = formatter;
		fCurrentVersion = currentVersion;
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public Map<String, String> getSettings() {
		return fSettings;
	}

	@Override
	public void setSettings(Map<String, String> settings) {
	}

	@Override
	public String getID() {
		return fID;
	}

	@Override
	public final int compareTo(IProfile o) {
		if (o instanceof BuiltInProfile) {
			return fOrder - ((BuiltInProfile) o).fOrder;
		}
		return -1;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + fName + "]";
	}

	public boolean isProfileToSave() {
		return false;
	}

	@Override
	public boolean isBuiltInProfile() {
		return true;
	}

	@Override
	public int getVersion() {
		return fCurrentVersion;
	}

	@Override
	public String getFormatterId() {
		return fFormatter;
	}

	private final String fName;
	private final String fID;
	private final Map<String, String> fSettings;
	private final int fOrder;
	private final int fCurrentVersion;
	private final String fFormatter;

}
