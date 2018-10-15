/*******************************************************************************
 * Copyright (c) 2010, 2016 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.compiler;

public class SourceElementRequestorDelegate implements ISourceElementRequestor {

	private final ISourceElementRequestor target;

	public SourceElementRequestorDelegate(ISourceElementRequestor target) {
		this.target = target;
	}

	protected int translate(int offset) {
		return offset;
	}

	protected FieldInfo translate(FieldInfo info) {
		final FieldInfo result = new FieldInfo();
		copy(info, result);
		result.type = info.type;
		return result;
	}

	private void copy(ElementInfo src, ElementInfo dest) {
		dest.name = src.name;
		dest.modifiers = src.modifiers;
		dest.declarationStart = translate(src.declarationStart);
		dest.nameSourceStart = translate(src.nameSourceStart);
		dest.nameSourceEnd = translate(src.nameSourceEnd);
	}

	protected MethodInfo translate(MethodInfo info) {
		final MethodInfo result = new MethodInfo();
		copy(info, result);
		result.parameterNames = info.parameterNames;
		result.parameterInitializers = info.parameterInitializers;
		result.parameterTypes = info.parameterTypes;
		result.exceptionTypes = info.exceptionTypes;
		result.returnType = info.returnType;
		result.isConstructor = info.isConstructor;
		return result;
	}

	protected TypeInfo translate(TypeInfo info) {
		final TypeInfo result = new TypeInfo();
		copy(info, result);
		result.superclasses = info.superclasses;
		return result;
	}

	protected ImportInfo translate(ImportInfo importInfo) {
		final ImportInfo result = new ImportInfo();
		result.containerName = importInfo.containerName;
		result.name = importInfo.name;
		result.version = importInfo.version;
		result.sourceStart = translate(importInfo.sourceStart);
		result.sourceEnd = translate(importInfo.sourceEnd);
		return result;
	}

	@Override
	public void acceptFieldReference(String fieldName, int sourcePosition) {
		target.acceptFieldReference(fieldName, translate(sourcePosition));
	}

	@Override
	public void acceptImport(ImportInfo importInfo) {
		target.acceptImport(translate(importInfo));
	}

	@Override
	public void acceptMethodReference(String methodName, int argCount,
			int sourcePosition, int sourceEndPosition) {
		target.acceptMethodReference(methodName, argCount,
				translate(sourcePosition), translate(sourceEndPosition));
	}

	@Override
	public void acceptPackage(int declarationStart, int declarationEnd,
			String name) {
		target.acceptPackage(translate(declarationStart),
				translate(declarationEnd), name);
	}

	@Override
	public void acceptTypeReference(String typeName, int sourcePosition) {
		target.acceptTypeReference(typeName, translate(sourcePosition));
	}

	@Override
	public void enterField(FieldInfo info) {
		target.enterField(translate(info));

	}

	@Override
	public void enterMethod(MethodInfo info) {
		target.enterMethod(translate(info));
	}

	@Override
	public void enterModule() {
		target.enterModule();
	}

	@Override
	public void enterModuleRoot() {
		target.enterModuleRoot();
	}

	@Override
	public void enterType(TypeInfo info) {
		target.enterType(translate(info));
	}

	@Override
	public void exitField(int declarationEnd) {
		target.exitField(translate(declarationEnd));
	}

	@Override
	public void exitMethod(int declarationEnd) {
		target.exitMethod(translate(declarationEnd));
	}

	@Override
	public void exitModule(int declarationEnd) {
		target.exitModule(translate(declarationEnd));
	}

	@Override
	public void exitModuleRoot() {
		target.exitModuleRoot();
	}

	@Override
	public void exitType(int declarationEnd) {
		target.exitType(translate(declarationEnd));
	}

	@Override
	public boolean enterFieldCheckDuplicates(FieldInfo info) {
		return target.enterFieldCheckDuplicates(translate(info));
	}

	@Override
	public void enterMethodRemoveSame(MethodInfo info) {
		target.enterMethodRemoveSame(translate(info));
	}

	@Override
	public boolean enterTypeAppend(String fullName, String delimiter) {
		return target.enterTypeAppend(fullName, fullName);
	}

	@Override
	public void enterNamespace(String[] namespace) {
		target.enterNamespace(namespace);
	}

	@Override
	public void exitNamespace() {
		target.exitNamespace();
	}

}
