/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.debug.ui.actions;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.dltk.debug.core.eval.IScriptEvaluationResult;
import org.eclipse.dltk.debug.core.eval.InspectEvaluatedScriptExpression;
import org.eclipse.dltk.debug.ui.DLTKDebugUIPlugin;
import org.eclipse.swt.widgets.Display;

/**
 * Places the result of an evaluation in the debug expression view.
 */
public class ScriptInspectAction extends ScriptEvaluationAction {
	@Override
	protected void displayResult(final IScriptEvaluationResult result) {
		final Display display = DLTKDebugUIPlugin.getStandardDisplay();
		display.asyncExec(() -> {
			if (!display.isDisposed()) {
				showExpressionView();
				InspectEvaluatedScriptExpression expression = new InspectEvaluatedScriptExpression(
						result);
				DebugPlugin.getDefault().getExpressionManager()
						.addExpression(expression);
			}
			evaluationCleanup();
		});
	}
}
