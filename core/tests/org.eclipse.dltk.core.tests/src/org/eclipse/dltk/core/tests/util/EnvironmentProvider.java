package org.eclipse.dltk.core.tests.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IEnvironmentProvider;

public class EnvironmentProvider implements IEnvironmentProvider {

	public static Map<String, IEnvironment> environments = Collections
			.synchronizedMap(new HashMap<String, IEnvironment>());

	public EnvironmentProvider() {
	}

	@Override
	public String getProviderName() {
		return "Test environment provider";
	}

	@Override
	public IEnvironment[] getEnvironments() {
		return environments.keySet().toArray(new IEnvironment[0]);
	}

	@Override
	public IEnvironment getEnvironment(String envId) {
		return environments.get(envId);
	}

	@Override
	public boolean isInitialized() {
		return true;
	}

	@Override
	public void waitInitialized() {
	}

	@Override
	public IEnvironment getProjectEnvironment(IProject project) {
		return null;
	}

	@Override
	public IEnvironment getEnvironment(URI locationURI) {
		return environments.get(locationURI.getScheme());
	}

	public static Closeable setEnvironment(final IEnvironment environment) {
		final String id = environment.getId();
		environments.put(id, environment);
		return new Closeable() {
			@Override
			public void close() throws IOException {
				environments.remove(id);
			}
		};
	}
}
