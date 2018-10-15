/*******************************************************************************
 * Copyright (c) 2009, 2016 xored software, Inc. and others. 
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
package org.eclipse.dltk.formatter;

/**
 * @since 2.0
 */
public class FormatterTextNodeWrapper implements IFormatterTextNode {

	protected final IFormatterTextNode target;

	public FormatterTextNodeWrapper(IFormatterTextNode target) {
		this.target = target;
	}

	@Override
	public String getText() {
		return target.getText();
	}

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		target.accept(context, visitor);
	}

	@Override
	public IFormatterDocument getDocument() {
		return target.getDocument();
	}

	@Override
	public int getEndOffset() {
		return target.getEndOffset();
	}

	@Override
	public int getStartOffset() {
		return target.getStartOffset();
	}

	@Override
	public boolean isEmpty() {
		return target.isEmpty();
	}

	@Override
	public String toString() {
		return target.toString();
	}

}
