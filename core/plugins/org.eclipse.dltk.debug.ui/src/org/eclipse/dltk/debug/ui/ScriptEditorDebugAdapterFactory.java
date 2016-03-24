package org.eclipse.dltk.debug.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.dltk.debug.ui.breakpoints.ScriptToggleBreakpointAdapter;
import org.eclipse.dltk.internal.debug.ui.ScriptRunToLineAdapter;

public abstract class ScriptEditorDebugAdapterFactory
		implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adapterType == IRunToLineTarget.class) {
			return (T) new ScriptRunToLineAdapter();
		} else if (adapterType == IToggleBreakpointsTarget.class) {
			return (T) getBreakpointAdapter();
		}

		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IRunToLineTarget.class,
				IToggleBreakpointsTarget.class };
	}

	/**
	 * Returns the project specific breakpoint adapter.
	 */
	protected abstract ScriptToggleBreakpointAdapter getBreakpointAdapter();
}
