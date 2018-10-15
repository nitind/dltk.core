/*******************************************************************************
 * Copyright (c) 2008, 2018 xored software, Inc. and others.
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
package org.eclipse.dltk.internal.debug.core.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.debug.core.model.IScriptSpawnpoint;

public class ScriptSpawnpoint extends ScriptLineBreakpoint
		implements IScriptSpawnpoint {

	@Override
	protected String getMarkerId() {
		return ScriptMarkerFactory.SPAWNPOINT_MARKER_ID;
	}

	public ScriptSpawnpoint() {
		// empty
	}

	/**
	 * @param debugModelId
	 * @param resource
	 * @param path
	 * @param lineNumber
	 * @param charStart
	 * @param charEnd
	 * @param register
	 * @throws DebugException
	 */
	public ScriptSpawnpoint(final String debugModelId, final IResource resource,
			final IPath path, final int lineNumber, final int charStart,
			final int charEnd, final boolean register) throws DebugException {

		IWorkspaceRunnable wr = monitor -> {
			// create the marker
			setMarker(resource.createMarker(getMarkerId()));

			// add attributes
			final Map<String, Object> attributes = new HashMap<>();
			addScriptBreakpointAttributes(attributes, debugModelId, true);
			addLineBreakpointAttributes(attributes, path, lineNumber, charStart,
					charEnd);

			// set attributes
			ensureMarker().setAttributes(attributes);

			// add to breakpoint manager if requested
			register(register);
		};
		run(getMarkerRule(resource), wr);
	}

	private static final String[] UPDATABLE_ATTRS = new String[] {
			IMarker.LINE_NUMBER, IBreakpoint.ENABLED };

	@Override
	public String[] getUpdatableAttributes() {
		return UPDATABLE_ATTRS;
	}

}
