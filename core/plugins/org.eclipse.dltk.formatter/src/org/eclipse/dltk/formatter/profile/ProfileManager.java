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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ui.formatter.IProfile;
import org.eclipse.dltk.ui.formatter.IProfileManager;
import org.eclipse.dltk.ui.formatter.ProfileKind;

/**
 * The model for the set of profiles which are available in the workbench.
 */
public class ProfileManager implements IProfileManager {

	/**
	 * A map containing the available profiles, using the IDs as keys.
	 */
	private final Map<String, IProfile> fProfiles;

	/**
	 * The available profiles, sorted by name.
	 */
	private final List<IProfile> fProfilesByName;

	/**
	 * The currently selected profile.
	 */
	private IProfile fSelected;

	private boolean fDirty = false;

	/**
	 * Create and initialize a new profile manager.
	 *
	 * @param profiles
	 *            Initial custom profiles (List of type
	 *            <code>CustomProfile</code>)
	 * @param profileVersioner
	 */
	public ProfileManager(List<IProfile> profiles) {
		fProfiles = new HashMap<>();
		fProfilesByName = new ArrayList<>();
		for (final IProfile profile : profiles) {
			fProfiles.put(profile.getID(), profile);
			fProfilesByName.add(profile);
		}
		Collections.sort(fProfilesByName);
		if (!fProfilesByName.isEmpty()) {
			fSelected = fProfilesByName.get(0);
		}
	}

	/**
	 * Get an immutable list as view on all profiles, sorted alphabetically.
	 * Unless the set of profiles has been modified between the two calls, the
	 * sequence is guaranteed to correspond to the one returned by
	 * <code>getSortedNames</code>.
	 *
	 * @return a list of elements of type <code>Profile</code>
	 *
	 * @see #getSortedDisplayNames()
	 */
	@Override
	public List<IProfile> getSortedProfiles() {
		return Collections.unmodifiableList(fProfilesByName);
	}

	/**
	 * Get the names of all profiles stored in this profile manager, sorted
	 * alphabetically. Unless the set of profiles has been modified between the
	 * two calls, the sequence is guaranteed to correspond to the one returned
	 * by <code>getSortedProfiles</code>.
	 *
	 * @return All names, sorted alphabetically
	 * @see #getSortedProfiles()
	 */
	@Override
	public String[] getSortedDisplayNames() {
		final String[] sortedNames = new String[fProfilesByName.size()];
		int i = 0;
		for (IProfile curr : fProfilesByName) {
			sortedNames[i++] = curr.getName();
		}
		return sortedNames;
	}

	/**
	 * Get the profile for this profile id.
	 *
	 * @param ID
	 *            The profile ID
	 * @return The profile with the given ID or <code>null</code>
	 */
	public IProfile getProfile(String ID) {
		return fProfiles.get(ID);
	}

	@Override
	public IProfile getSelected() {
		return fSelected;
	}

	@Override
	public void setSelected(IProfile profile) {
		final IProfile newSelected = fProfiles.get(profile.getID());
		if (newSelected != null && !newSelected.equals(fSelected)) {
			fSelected = newSelected;
		}
	}

	@Override
	public boolean containsName(String name) {
		for (IProfile curr : fProfilesByName) {
			if (name.equals(curr.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IProfile findProfile(String profileId) {
		return fProfiles.get(profileId);
	}

	@Override
	public void addProfile(IProfile profile) {
		if (profile instanceof CustomProfile) {
			CustomProfile newProfile = (CustomProfile) profile;
			// newProfile.setManager(this);
			final CustomProfile oldProfile = (CustomProfile) fProfiles
					.get(profile.getID());
			if (oldProfile != null) {
				fProfiles.remove(oldProfile.getID());
				fProfilesByName.remove(oldProfile);
				// oldProfile.setManager(null);
			}
			fProfiles.put(profile.getID(), profile);
			fProfilesByName.add(profile);
			Collections.sort(fProfilesByName);
			fSelected = newProfile;
			fDirty = true;
		}
	}

	@Override
	public boolean deleteProfile(IProfile profile) {
		if (profile instanceof CustomProfile) {
			CustomProfile oldProfile = (CustomProfile) profile;
			int index = fProfilesByName.indexOf(profile);

			fProfiles.remove(oldProfile.getID());
			fProfilesByName.remove(oldProfile);

			// oldProfile.setManager(null);

			if (index >= fProfilesByName.size())
				index--;
			fSelected = fProfilesByName.get(index);

			fDirty = true;

			return true;
		}
		return false;
	}

	@Override
	public IProfile rename(IProfile profile, String newName) {
		final String trimmed = newName.trim();
		if (trimmed.equals(profile.getName()))
			return profile;
		if (profile.isBuiltInProfile()) {
			CustomProfile newProfile = new CustomProfile(trimmed,
					profile.getSettings(), profile.getFormatterId(),
					profile.getVersion());
			addProfile(newProfile);
			fDirty = true;
			return newProfile;
		} else {
			CustomProfile cProfile = (CustomProfile) profile;

			String oldID = profile.getID();
			cProfile.fName = trimmed;

			fProfiles.remove(oldID);
			fProfiles.put(profile.getID(), profile);

			Collections.sort(fProfilesByName);
			fDirty = true;
			return cProfile;
		}
	}

	public void profileReplaced(CustomProfile oldProfile,
			CustomProfile newProfile) {
		fProfiles.remove(oldProfile.getID());
		fProfiles.put(newProfile.getID(), newProfile);
		fProfilesByName.remove(oldProfile);
		fProfilesByName.add(newProfile);
		Collections.sort(fProfilesByName);

		setSelected(newProfile);
	}

	@Override
	public IProfile create(ProfileKind kind, String profileName,
			Map<String, String> settings, String formatterId, int version) {
		CustomProfile profile = new CustomProfile(profileName, settings,
				formatterId, version);
		if (kind != ProfileKind.TEMPORARY) {
			addProfile(profile);
		}
		return profile;
	}

	@Override
	public boolean isDirty() {
		return fDirty;
	}

	@Override
	public void markDirty() {
		fDirty = true;
	}

	@Override
	public void clearDirty() {
		fDirty = false;
	}
}
