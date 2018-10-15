/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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
package org.eclipse.dltk.internal.core.structure;

import org.eclipse.dltk.core.search.indexing.IndexDocument;
import org.eclipse.dltk.core.search.indexing.core.AbstractProjectIndexer;

public class StructureProjectIndexer extends AbstractProjectIndexer {
	@Override
	public void doIndexing(IndexDocument document) {
		new StructureIndexer(document).indexDocument();
	}
}
