/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
