/*******************************************************************************
 * Copyright (c) 2016, 2017 xored software, Inc. and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.core.search;

import org.eclipse.dltk.core.ISearchPatternProcessor;

public abstract class SearchPatternProcessor
		implements ISearchPatternProcessor {

	protected static final String TYPE_SEPARATOR_STR = String
			.valueOf(TYPE_SEPARATOR);

	private static ISearchPatternProcessor instance = null;

	public static ISearchPatternProcessor getDefault() {
		if (instance == null) {
			instance = new SearchPatternProcessor() {
			};
		}
		return instance;
	}

	protected SearchPatternProcessor() {
		//
	}

	protected static class TypePattern implements ITypePattern {
		private final String qualification;
		private final String simpleName;

		public TypePattern(String qualification, String simpleName) {
			this.qualification = qualification;
			this.simpleName = simpleName;
		}

		@Override
		public char[] qualification() {
			return qualification != null ? qualification.toCharArray() : null;
		}

		@Override
		public String getQualification() {
			return qualification;
		}

		@Override
		public char[] simpleName() {
			return simpleName != null ? simpleName.toCharArray() : null;
		}

		@Override
		public String getSimpleName() {
			return simpleName;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + '(' + qualification + ','
					+ simpleName + ')';
		}
	}

	/**
	 * There is a typo in class name, {@link TypePattern} class should be used
	 * instead.
	 */
	@Deprecated
	protected static class TypePatten extends TypePattern {
		public TypePatten(String qualification, String simpleName) {
			super(qualification, simpleName);
		}
	}

	@Override
	public ITypePattern parseType(String patternString) {
		return new TypePattern(null, patternString);
	}

	@Override
	public String getDelimiterReplacementString() {
		return ".";
	}

	@Override
	public char[] extractDeclaringTypeQualification(String patternString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] extractDeclaringTypeSimpleName(String patternString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] extractSelector(String patternString) {
		// TODO Auto-generated method stub
		return patternString.toCharArray();
	}

	@Override
	public final char[] extractTypeQualification(String patternString) {
		return parseType(patternString).qualification();
	}

	@Override
	@Deprecated
	public final String extractTypeChars(String patternString) {
		return parseType(patternString).getSimpleName();
	}

}
