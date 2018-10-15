/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.internal.debug.core.model;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class ScriptMarkerFactory {
	public static final String LINE_BREAKPOINT_MARKER_ID = "org.eclipse.dltk.debug.scriptLineBreakpointMarker"; //$NON-NLS-1$
	public static final String METHOD_ENTRY_MARKER_ID = "org.eclipse.dltk.debug.scriptMethodEntryBreakpointMarker"; //$NON-NLS-1$
	public static final String WATCHPOINT_MARKER_ID = "org.eclipse.dltk.debug.scriptWatchPointMarker"; //$NON-NLS-1$
	public static final String SPAWNPOINT_MARKER_ID = "org.eclipse.dltk.debug.scriptSpawnpointMarker"; //$NON-NLS-1$

	public static IMarker makeMarker(IResource resource,
			Map<String, Object> attributes, String id) throws CoreException {
		IMarker marker = resource.createMarker(id);
		marker.setAttributes(attributes);
		return marker;
	}
}
