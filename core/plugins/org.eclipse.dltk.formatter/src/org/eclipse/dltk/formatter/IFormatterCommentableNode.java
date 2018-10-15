/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.formatter;

import java.util.List;

public interface IFormatterCommentableNode {

	void insertBefore(List<? extends IFormatterNode> comments);

}
