/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.dltk.internal.testing.ui;

import java.util.ArrayList;

import org.eclipse.dltk.internal.testing.model.TestCaseElement;
import org.eclipse.dltk.internal.testing.model.TestContainerElement;
import org.eclipse.dltk.internal.testing.model.TestRoot;
import org.eclipse.dltk.internal.testing.model.TestSuiteElement;
import org.eclipse.dltk.testing.model.ITestElement;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestSessionTableContentProvider
		implements IStructuredContentProvider {

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		ArrayList<Object> all = new ArrayList<>();
		addAll(all, (TestRoot) inputElement);
		return all.toArray();
	}

	private void addAll(ArrayList<Object> all, TestContainerElement suite) {
		ITestElement[] children = suite.getChildren();
		for (int i = 0; i < children.length; i++) {
			ITestElement element = children[i];
			if (element instanceof TestContainerElement) {
				final TestContainerElement container = (TestContainerElement) element;
				if (element instanceof TestSuiteElement) {
					if (container.getSuiteStatus().isErrorOrFailure())
						all.add(element); // add failed suite to flat list too
				}
				addAll(all, container);
			} else if (element instanceof TestCaseElement) {
				all.add(element);
			}
		}
	}

	@Override
	public void dispose() {
	}
}
