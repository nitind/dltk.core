/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.launching.process;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ISourceLocator;

class LaunchProxy implements ILaunch {

	private final ILaunch original;

	/**
	 * @param original
	 */
	public LaunchProxy(ILaunch original) {
		this.original = original;
	}

	/*
	 * @see ILaunch#addDebugTarget(IDebugTarget)
	 */
	@Override
	public void addDebugTarget(IDebugTarget target) {
		original.addDebugTarget(target);
	}

	/*
	 * @see ILaunch#addProcess(IProcess)
	 */
	@Override
	public void addProcess(IProcess process) {
		original.addProcess(process);
	}

	/*
	 * @see ILaunch#getAttribute(String)
	 */
	@Override
	public String getAttribute(String key) {
		if (DebugPlugin.ATTR_CAPTURE_OUTPUT.equals(key)) {
			return Boolean.TRUE.toString();
		} else {
			return original.getAttribute(key);
		}
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#getChildren()
	 */
	@Override
	public Object[] getChildren() {
		return original.getChildren();
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#getDebugTarget()
	 */
	@Override
	public IDebugTarget getDebugTarget() {
		return original.getDebugTarget();
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#getDebugTargets()
	 */
	@Override
	public IDebugTarget[] getDebugTargets() {
		return original.getDebugTargets();
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#getLaunchConfiguration()
	 */
	@Override
	public ILaunchConfiguration getLaunchConfiguration() {
		return original.getLaunchConfiguration();
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#getLaunchMode()
	 */
	@Override
	public String getLaunchMode() {
		return original.getLaunchMode();
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#getProcesses()
	 */
	@Override
	public IProcess[] getProcesses() {
		return original.getProcesses();
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#getSourceLocator()
	 */
	@Override
	public ISourceLocator getSourceLocator() {
		return original.getSourceLocator();
	}

	/*
	 * @see org.eclipse.debug.core.ILaunch#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return original.hasChildren();
	}

	/*
	 * @see
	 * org.eclipse.debug.core.ILaunch#removeDebugTarget(org.eclipse.debug.core
	 * .model.IDebugTarget)
	 */
	@Override
	public void removeDebugTarget(IDebugTarget target) {
		original.removeDebugTarget(target);
	}

	/*
	 * @see ILaunch#removeProcess(IProcess)
	 */
	@Override
	public void removeProcess(IProcess process) {
		original.removeProcess(process);
	}

	/*
	 * @see ILaunch#setAttribute(String,String)
	 */
	@Override
	public void setAttribute(String key, String value) {
		original.setAttribute(key, value);
	}

	/*
	 * @see ILaunch#setSourceLocator(ISourceLocator)
	 */
	@Override
	public void setSourceLocator(ISourceLocator sourceLocator) {
		original.setSourceLocator(sourceLocator);
	}

	/*
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	@Override
	public boolean canTerminate() {
		return original.canTerminate();
	}

	/*
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	@Override
	public boolean isTerminated() {
		return original.isTerminated();
	}

	/*
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	@Override
	public void terminate() throws DebugException {
		original.terminate();
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return original.getAdapter(adapter);
	}

}
