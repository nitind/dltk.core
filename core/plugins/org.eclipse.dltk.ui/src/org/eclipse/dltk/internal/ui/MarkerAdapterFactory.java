/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 *******************************************************************************/
package org.eclipse.dltk.internal.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.internal.ui.search.DLTKSearchPageScoreComputer;
import org.eclipse.dltk.internal.ui.search.SearchUtil;
import org.eclipse.search.ui.ISearchPageScoreComputer;



/**
 * Adapter factory to support basic UI operations for markers.
 */
public class MarkerAdapterFactory implements IAdapterFactory {

	private static Class<?>[] PROPERTIES = new Class[0];
	

	private Object fSearchPageScoreComputer;
	
	@Override
	public Class<?>[] getAdapterList() {
		updateLazyLoadedAdapters();
		return PROPERTIES;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object element, Class<T> key) {
		updateLazyLoadedAdapters();
		if (fSearchPageScoreComputer != null && ISearchPageScoreComputer.class.equals(key))
			return (T) fSearchPageScoreComputer;
		return null;
	}

	private void updateLazyLoadedAdapters() {
		if (fSearchPageScoreComputer == null && SearchUtil.isSearchPlugInActivated())
			createSearchPageScoreComputer();
	}
	
	private void createSearchPageScoreComputer() {
		fSearchPageScoreComputer= new DLTKSearchPageScoreComputer();
		PROPERTIES= new Class[] {ISearchPageScoreComputer.class};
	}
}
