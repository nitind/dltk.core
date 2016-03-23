/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.testing;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.testing.model.ITestCaseElement;
import org.eclipse.dltk.testing.model.ITestElement;
import org.eclipse.dltk.testing.model.ITestRunSession;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractTestRunnerUI implements ITestRunnerUI {

	/*
	 * @see org.eclipse.dltk.testing.ITestRunnerUI#canFilterStack()
	 */
	@Override
	public boolean canFilterStack() {
		return false;
	}

	@Override
	public String filterStackTrace(String trace) {
		return trace;
	}

	@Override
	public boolean isStackFrame(String line) {
		return false;
	}

	@Override
	public IAction createOpenEditorAction(String traceLine) {
		return null;
	}

	@Override
	public String getTestCaseLabel(ITestCaseElement caseElement, boolean full) {
		String testName = caseElement.getTestName();
		int index = testName.indexOf('(');
		if (index > 0) {
			if (full) {
				int end = testName.length();
				if (end > index + 1 && testName.charAt(end - 1) == ')') {
					--end;
				}
				final String template = DLTKTestingMessages.TestSessionLabelProvider_testMethodName_className;
				return NLS.bind(template, testName.substring(index + 1, end),
						testName.substring(0, index));
			} else {
				return testName.substring(0, index);
			}
		}
		index = testName.indexOf('@');
		if (index > 0) {
			if (full) {
				final String template = DLTKTestingMessages.TestSessionLabelProvider_testMethodName_className;
				return NLS.bind(template, testName.substring(index + 1),
						testName.substring(0, index));
			} else {
				return testName.substring(0, index);
			}
		}
		return testName;
	}

	@Override
	public String getTestStartedMessage(ITestCaseElement caseElement) {
		return caseElement.getTestName();
	}

	@Override
	public boolean canRerun(ITestElement testElement) {
		// IScriptProject project = fTestRunnerPart.getLaunchedProject();
		// if (project == null)
		// return false;
		// try {
		// IType type = project.findType(className);
		// return type != null;
		// } catch (ModelException e) {
		// // fall through
		// }
		return false;
	}

	@Override
	public boolean canRerunFailures() {
		return false;
	}

	@Override
	public String collectFailures(ITestRunSession testRunSession)
			throws CoreException {
		return null;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean isFilterStack() {
		final IPreferenceStore store = getPreferenceStore();
		if (store != null) {
			return store
					.getBoolean(DLTKTestingPreferencesConstants.DO_FILTER_STACK);
		} else {
			return false;
		}
	}

	@Override
	public void setFilterStack(boolean value) {
		final IPreferenceStore store = getPreferenceStore();
		if (store != null) {
			store.setValue(DLTKTestingPreferencesConstants.DO_FILTER_STACK,
					value);
		}
	}

	protected IPreferenceStore getPreferenceStore() {
		return DLTKTestingPlugin.getDefault().getPreferenceStore();
	}

}
