/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormatterBlockNode extends AbstractFormatterNode
		implements IFormatterContainerNode {

	/**
	 * @param document
	 */
	public FormatterBlockNode(IFormatterDocument document) {
		super(document);
	}

	private final List<IFormatterNode> body = new ArrayList<>();

	protected void acceptNodes(final List<IFormatterNode> nodes,
			IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		for (IFormatterNode node : nodes) {
			context.enter(node);
			node.accept(context, visitor);
			context.leave(node);
		}
	}

	@Override
	public void addChild(IFormatterNode child) {
		body.add(child);
	}

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		acceptBody(context, visitor);
	}

	protected void acceptBody(IFormatterContext context,
			IFormatterWriter visitor) throws Exception {
		acceptNodes(body, context, visitor);
	}

	@Override
	public int getEndOffset() {
		if (!body.isEmpty()) {
			return body.get(body.size() - 1).getEndOffset();
		} else {
			return DEFAULT_OFFSET;
		}
	}

	@Override
	public int getStartOffset() {
		if (!body.isEmpty()) {
			return body.get(0).getStartOffset();
		} else {
			return DEFAULT_OFFSET;
		}
	}

	@Override
	public boolean isEmpty() {
		for (IFormatterNode node : body) {
			if (!node.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<IFormatterNode> getChildren() {
		return Collections.unmodifiableList(body);
	}

	@Override
	public String toString() {
		return body.toString();
	}

	@Override
	public List<IFormatterNode> getBody() {
		return body;
	}

}
