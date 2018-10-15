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
 *     xored software, Inc. - initial API and Implementation (Yuri Strot)
 *******************************************************************************/
package org.eclipse.dltk.internal.launching.execution;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.dltk.core.environment.IDeployment;

public class DeploymentManager implements ILaunchesListener2 {
	private Map<ILaunch, Set<IDeployment>> launchToDeployment = new HashMap<>();
	private Set<IDeployment> activeDeployments = new HashSet<>();

	private static DeploymentManager sInstance = null;

	public static synchronized DeploymentManager getInstance() {
		if (sInstance == null) {
			sInstance = new DeploymentManager();
		}
		return sInstance;
	}

	public void startup() {
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(this);
	}

	public void shutdown() {
		undeployAll(activeDeployments);
		DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(this);
	}

	@Override
	public void launchesAdded(ILaunch[] launches) {
	}

	@Override
	public void launchesChanged(ILaunch[] launches) {
	}

	@Override
	public synchronized void launchesRemoved(ILaunch[] launches) {
		for (int i = 0; i < launches.length; i++) {
			if (launchToDeployment.containsKey(launches[i])) {
				Set<IDeployment> deployments = launchToDeployment
						.get(launches[i]);
				undeployAll(deployments);
				launchToDeployment.remove(launches[i]);
			}
		}
	}

	private synchronized void undeployAll(Collection<IDeployment> deployments) {
		Set<IDeployment> copy = new HashSet<>(deployments);
		for (Iterator<IDeployment> iterator = copy.iterator(); iterator
				.hasNext();) {
			IDeployment deployment = iterator.next();
			deployment.dispose();
			activeDeployments.remove(deployment);
		}
	}

	public synchronized void addDeployment(IDeployment deployment) {
		activeDeployments.add(deployment);
	}

	public synchronized void addDeployment(ILaunch launch,
			IDeployment deployment) {
		activeDeployments.add(deployment);
		if (launchToDeployment.containsKey(launch)) {
			launchToDeployment.get(launch).add(deployment);
		} else {
			Set<IDeployment> elements = new HashSet<>();
			elements.add(deployment);
			launchToDeployment.put(launch, elements);
		}
	}

	public synchronized void removeDeployment(IDeployment deployment) {
		activeDeployments.remove(deployment);
	}

	@Override
	public void launchesTerminated(ILaunch[] launches) {
		launchesRemoved(launches);
	}
}
