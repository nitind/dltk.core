package org.eclipse.dltk.internal.ui.rse;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.internal.rse.RSEEnvironment;
import org.eclipse.dltk.ui.environment.IEnvironmentUI;

public class RSEEnvironmentUIAdapter implements IAdapterFactory {
	private final static Class<?>[] ADAPTABLES = new Class[] { IEnvironmentUI.class };

	public RSEEnvironmentUIAdapter() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof RSEEnvironment
				&& adapterType == IEnvironmentUI.class) {
			return (T) new RSEEnvironmentUI((RSEEnvironment) adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return ADAPTABLES;
	}
}
