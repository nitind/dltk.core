/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.corext.refactoring.base;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;

/**
 * A Script element context that can be used to annotate a
 * </code>RefactoringStatusEntry<code>
 * with detailed information about an error detected in an <code>IScriptElement</code>.
 */
public abstract class ScriptStatusContext extends RefactoringStatusContext {

	private static class MemberSourceContext extends ScriptStatusContext {
		private IMember fMember;

		private MemberSourceContext(IMember member) {
			fMember = member;
		}

		@Override
		public boolean isBinary() {
			// return fMember.isBinary();
			return false;
		}

		@Override
		public ISourceModule getSourceModule() {
			return fMember.getSourceModule();
		}

		@Override
		public ISourceRange getSourceRange() {
			try {
				return fMember.getSourceRange();
			} catch (ModelException e) {
				return new SourceRange(0, 0);
			}
		}
	}

	private static class SourceModuleSourceContext extends ScriptStatusContext {
		private ISourceModule fCUnit;
		private ISourceRange fSourceRange;

		private SourceModuleSourceContext(ISourceModule cunit, ISourceRange range) {
			fCUnit = cunit;
			fSourceRange = range;
			if (fSourceRange == null)
				fSourceRange = new SourceRange(0, 0);
		}

		@Override
		public boolean isBinary() {
			return false;
		}

		@Override
		public ISourceModule getSourceModule() {
			return fCUnit;
		}

		@Override
		public ISourceRange getSourceRange() {
			return fSourceRange;
		}

		@Override
		public String toString() {
			return getSourceRange() + " in " + super.toString(); //$NON-NLS-1$
		}
	}

	/**
	 * Creates an status entry context for the given member
	 *
	 * @param member
	 *            thescriptmember for which the context is supposed to be created
	 * @return the status entry context or <code>null</code> if the context cannot
	 *         be created
	 */
	public static RefactoringStatusContext create(IMember member) {
		if (member == null || !member.exists())
			return null;
		return new MemberSourceContext(member);
	}

	// /**
	// * Creates an status entry context for the given import declaration
	// *
	// * @param declaration the import declaration for which the context is
	// * supposed to be created
	// * @return the status entry context or <code>null</code> if the
	// * context cannot be created
	// */
	// public static RefactoringStatusContext create(IImportDeclaration declaration)
	// {
	// if (declaration == null || !declaration.exists())
	// return null;
	// return new ImportDeclarationSourceContext(declaration);
	// }

	/**
	 * Creates an status entry context for the given compilation unit.
	 *
	 * @param cunit
	 *            the compilation unit containing the error
	 * @return the status entry context or <code>Context.NULL_CONTEXT</code> if the
	 *         context cannot be created
	 */
	public static RefactoringStatusContext create(ISourceModule cunit) {
		return create(cunit, (ISourceRange) null);
	}

	/**
	 * Creates an status entry context for the given compilation unit and source
	 * range.
	 *
	 * @param cunit
	 *            the compilation unit containing the error
	 * @param range
	 *            the source range that has caused the error or <code>null</code> if
	 *            the source range is unknown
	 * @return the status entry context or <code>null</code> if the context cannot
	 *         be created
	 */
	public static RefactoringStatusContext create(ISourceModule cunit, ISourceRange range) {
		if (cunit == null)
			return null;
		return new SourceModuleSourceContext(cunit, range);
	}

	/**
	 * Creates an status entry context for the given compilation unit and AST node.
	 *
	 * @param cunit
	 *            the compilation unit containing the error
	 * @param node
	 *            an astNode denoting the source range that has caused the error
	 *
	 * @return the status entry context or <code>Context.NULL_CONTEXT</code> if the
	 *         context cannot be created
	 */
	public static RefactoringStatusContext create(ISourceModule cunit, ASTNode node) {
		ISourceRange range = null;
		if (node != null)
			range = new SourceRange(node.sourceStart(), node.sourceEnd());
		return create(cunit, range);
	}

	/**
	 * Creates an status entry context for the given compilation unit and selection.
	 *
	 * @param cunit
	 *            the compilation unit containing the error
	 * @param selection
	 *            a selection denoting the source range that has caused the error
	 *
	 * @return the status entry context or <code>Context.NULL_CONTEXT</code> if the
	 *         context cannot be created
	 *
	 *         public static RefactoringStatusContext create(ISourceModule cunit,
	 *         Selection selection) { ISourceRange range= null; if (selection !=
	 *         null) range= new SourceRange(selection.getOffset(),
	 *         selection.getLength()); return create(cunit, range); }
	 *
	 *         /** Returns whether this context is for a class file.
	 *
	 * @return <code>true</code> if from a class file, and <code>false</code> if
	 *         from a compilation unit
	 */
	public abstract boolean isBinary();

	/**
	 * Returns the compilation unit this context is working on. Returns
	 * <code>null</code> if the context is a binary context.
	 *
	 * @return the compilation unit
	 */
	public abstract ISourceModule getSourceModule();

	/**
	 * Returns the source range associated with this element.
	 *
	 * @return the source range
	 */
	public abstract ISourceRange getSourceRange();

	@Override
	public Object getCorrespondingElement() {
		return getSourceModule();
	}
}
