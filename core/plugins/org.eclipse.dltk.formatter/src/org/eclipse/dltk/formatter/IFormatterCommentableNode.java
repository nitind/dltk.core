/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.formatter;

import java.util.List;

public interface IFormatterCommentableNode {

	void insertBefore(List<? extends IFormatterNode> comments);

}
