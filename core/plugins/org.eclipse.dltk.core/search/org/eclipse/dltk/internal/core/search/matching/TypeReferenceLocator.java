/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core.search.matching;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.matching.PatternLocator;

public class TypeReferenceLocator extends PatternLocator {

	protected TypeReferencePattern pattern;

	protected boolean isDeclarationOfReferencedTypesPattern;

	public TypeReferenceLocator(TypeReferencePattern pattern) {

		super(pattern);

		this.pattern = pattern;
		this.isDeclarationOfReferencedTypesPattern = this.pattern instanceof DeclarationOfReferencedTypesPattern;
	}

	protected IModelElement findElement(IModelElement element, int accuracy) {
		// need exact match to be able to open on type ref
		if (accuracy != SearchMatch.A_ACCURATE)
			return null;

		// element that references the type must be included in the enclosing
		// element
		DeclarationOfReferencedTypesPattern declPattern = (DeclarationOfReferencedTypesPattern) this.pattern;
		while (element != null && !declPattern.enclosingElement.equals(element))
			element = element.getParent();
		return element;
	}	
	public int match(SimpleReference ref, MatchingNodeSet nodeSet) {
		if (!(ref instanceof TypeReference)) return IMPOSSIBLE_MATCH;
		return match((TypeReference)ref, nodeSet );
	}
	@Override
	public int match(TypeReference node, MatchingNodeSet nodeSet) { // interested in NameReference & its subtypes
		
		if (this.pattern.simpleName == null)
			return nodeSet.addMatch(node, POSSIBLE_MATCH);
		// TODO handle qualifiedTypeReference
		if (matchesName(this.pattern.simpleName, ((SimpleReference) node).getName().toCharArray()))
			return nodeSet.addMatch(node, ACCURATE_MATCH);
		
		
		return IMPOSSIBLE_MATCH;
	}


	@Override
	protected int referenceType() {
		return IModelElement.TYPE;
	}

	@Override
	public String toString() {
		return "Locator for " + this.pattern.toString(); //$NON-NLS-1$
	}
}
