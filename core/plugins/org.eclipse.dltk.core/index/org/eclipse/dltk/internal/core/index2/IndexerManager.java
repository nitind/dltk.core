/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.dltk.internal.core.index2;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.index2.AbstractIndexer;
import org.eclipse.dltk.core.index2.IIndexer;
import org.eclipse.dltk.core.index2.IIndexerParticipant;

/**
 * Indexer instances manager
 *
 * @author michael
 *
 */
public class IndexerManager {

	private static final String PARTICIPANT_POINT = DLTKCore.PLUGIN_ID
			+ ".indexerParticipant"; //$NON-NLS-1$
	private static final String INDEXER_POINT = DLTKCore.PLUGIN_ID + ".indexer"; //$NON-NLS-1$
	private static final String INDEXER_ATTR = "indexer"; //$NON-NLS-1$
	private static final String PARTICIPANT_ELEMENT = "indexerParticipant"; //$NON-NLS-1$
	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$
	private static final String NATURE_ATTR = "nature"; //$NON-NLS-1$
	private static final String ID_ATTR = "id"; //$NON-NLS-1$

	private static IConfigurationElement indexerElement;
	private static AbstractIndexer indexer;
	private static Map<String, IConfigurationElement> indexerParticipants = new HashMap<>();

	static {
		String indexerdId = getIndexerID();
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(INDEXER_POINT);
		for (IConfigurationElement element : elements) {
			String name = element.getName();
			String id = element.getAttribute(ID_ATTR);
			if (INDEXER_ATTR.equals(name)
					&& (indexerdId == null || indexerdId.equals(id))) {
				indexerElement = element;
				break;
			}
		}
		if (indexerElement == null) {
			if (indexerdId != null) {
				DLTKCore.error("Unable to find indexer: " + indexerdId);
			} else {
				DLTKCore.error("Unable to find any indexer");
			}
		}

		elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PARTICIPANT_POINT);
		for (IConfigurationElement element : elements) {
			String name = element.getName();
			if (PARTICIPANT_ELEMENT.equals(name)) {
				String nature = element.getAttribute(NATURE_ATTR);
				indexerParticipants.put(nature, element);
			}
		}
	}

	public static IIndexer getIndexer() {
		if (indexer == null && indexerElement != null) {
			try {
				indexer = (AbstractIndexer) indexerElement
						.createExecutableExtension(CLASS_ATTR);
				indexer.setId(indexerElement.getAttribute(ID_ATTR));
			} catch (CoreException e) {
				DLTKCore.error(e);
			}
		}
		return indexer;
	}

	private static String getIndexerID() {
		String indexerId = System.getProperty(DLTKCore.INDEXER_ID);
		if (indexerId != null) {
			return indexerId;
		}
		return Platform.getPreferencesService().getString(DLTKCore.PLUGIN_ID,
				DLTKCore.INDEXER_ID, null, null);
	}

	public static IIndexerParticipant getIndexerParticipant(IIndexer indexer,
			String natureId) {
		IConfigurationElement element = indexerParticipants.get(natureId);
		if (element != null) {
			try {
				return (IIndexerParticipant) element
						.createExecutableExtension(CLASS_ATTR);
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
