package org.eclipse.dltk.core.internal.rse;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.environment.IExecutionEnvironment;

public class RSEExecEnvironmentAdapter implements IAdapterFactory {
	public static final Class<?>[] ADAPTER_LIST = { IExecutionEnvironment.class };
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adapterType == IExecutionEnvironment.class && 
				adaptableObject instanceof RSEEnvironment) {
			RSEEnvironment env = (RSEEnvironment) adaptableObject;
			return (T) new RSEExecEnvironment(env);
		}
		return null;
	}
	@Override
	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST; 
	}

}
