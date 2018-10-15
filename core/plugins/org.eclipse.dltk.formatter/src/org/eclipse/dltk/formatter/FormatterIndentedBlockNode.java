/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.formatter;

public class FormatterIndentedBlockNode extends FormatterBlockNode {

	private final boolean indenting;

	public FormatterIndentedBlockNode(IFormatterDocument document,
			boolean indenting) {
		super(document);
		this.indenting = indenting;
	}

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		if (isIndenting()) {
			context.incIndent();
		}
		super.accept(context, visitor);
		if (isIndenting()) {
			context.decIndent();
		}
	}

	protected boolean isIndenting() {
		return indenting;
	}

}
