/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index.lucene;

import org.eclipse.osgi.util.NLS;

/**
 * Messages.
 * 
 * @author Bartlomiej Laczkowski
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.internal.core.index.lucene.messages"; //$NON-NLS-1$
	public static String LuceneManager_Committer_flushing_index_data;
	public static String LuceneManager_Committer_saving_indexes;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
