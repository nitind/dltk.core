/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.problem.IProblemReporter;

public abstract class AbstractSourceElementParser implements
		ISourceElementParser {

	private ISourceElementRequestor sourceElementRequestor = null;
	private IProblemReporter problemReporter;

	@Override
	public void parseSourceModule(IModuleSource module) {
		final ModuleDeclaration moduleDeclaration = parse(module);
		if (moduleDeclaration != null) {
			final SourceElementRequestVisitor requestor = createVisitor();
			try {
				moduleDeclaration.traverse(requestor);
			} catch (Exception e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
	}

	protected ModuleDeclaration parse(IModuleSource module) {
		if (module instanceof ISourceModule) {
			// use source module cache
			return SourceParserUtil.getModuleDeclaration(
					(ISourceModule) module, problemReporter);
		} else {
			// parse directly without cache
			return SourceParserUtil.getModuleDeclaration(module, getNatureId(),
					problemReporter);
		}
	}

	@Override
	public void setReporter(IProblemReporter reporter) {
		this.problemReporter = reporter;
	}

	@Override
	public void setRequestor(ISourceElementRequestor requestor) {
		this.sourceElementRequestor = requestor;
	}

	protected ISourceElementRequestor getRequestor() {
		return sourceElementRequestor;
	}

	protected IProblemReporter getProblemReporter() {
		return problemReporter;
	}

	/**
	 * Returns the language's nature id
	 */
	protected abstract String getNatureId();

	/**
	 * Creates a new source element visitor
	 * 
	 * <p>
	 * Sub-classes should use <code>getRequstor</code> and
	 * <code>getProblemReporter</code> if they need access to a source element
	 * requestor and/or a problem reporter.
	 * </p>
	 */
	protected SourceElementRequestVisitor createVisitor() {
		return new SourceElementRequestVisitor(getRequestor());
	}
}
