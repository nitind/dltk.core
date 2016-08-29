/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.compiler;

public class SourceElementRequestorAdaptor implements ISourceElementRequestor {

	/**
	 * @since 2.0
	 */
	@Override
	public void acceptFieldReference(String fieldName, int sourcePosition) {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void acceptMethodReference(String methodName, int argCount,
			int sourcePosition, int sourceEndPosition) {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void acceptPackage(int declarationStart, int declarationEnd,
			String name) {
	}

	@Override
	public void acceptTypeReference(String typeName, int sourcePosition) {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void enterField(FieldInfo info) {
	}

	public void updateField(FieldInfo fieldInfo, int flags) {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean enterFieldCheckDuplicates(FieldInfo info) {
		return false;
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void enterMethod(MethodInfo info) {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void enterMethodRemoveSame(MethodInfo info) {
	}

	@Override
	public void enterModule() {
	}

	@Override
	public void enterModuleRoot() {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void enterType(TypeInfo info) {
	}

	@Override
	public boolean enterTypeAppend(String fullName, String delimiter) {
		return false;
	}

	@Override
	public void exitField(int declarationEnd) {
	}

	@Override
	public void exitMethod(int declarationEnd) {
	}

	@Override
	public void exitModule(int declarationEnd) {
	}

	@Override
	public void exitModuleRoot() {
	}

	@Override
	public void exitType(int declarationEnd) {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void acceptImport(ImportInfo importInfo) {
	}

	/**
	 * @since 3.0
	 */
	@Override
	public void enterNamespace(String[] namespace) {
	}

	/**
	 * @since 3.0
	 */
	@Override
	public void exitNamespace() {
	}
}
