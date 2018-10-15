/*******************************************************************************
 * Copyright (c) 2010, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.launching.model.util;

import org.eclipse.dltk.core.Predicate;
import org.eclipse.dltk.launching.model.InterpreterGeneratedContent;
import org.eclipse.emf.ecore.EObject;

public class GeneratedContentPredicate implements Predicate<EObject> {

	private final String key;

	public GeneratedContentPredicate(String key) {
		this.key = key;
	}

	@Override
	public boolean evaluate(EObject t) {
		return t instanceof InterpreterGeneratedContent
				&& key.equals(((InterpreterGeneratedContent) t).getKey());
	}

}
