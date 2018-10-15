/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.ui.search;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Color;

public class ColorDecoratingLabelProvider extends DecoratingLabelProvider implements IColorProvider {

	public ColorDecoratingLabelProvider(ILabelProvider provider, ILabelDecorator decorator) {
		super(provider, decorator);
	}

	@Override
	public Color getForeground(Object element) {
		ILabelProvider labelProvider = getLabelProvider();
		if (labelProvider instanceof IColorProvider)
			return ((IColorProvider)labelProvider).getForeground(element);
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		ILabelProvider labelProvider = getLabelProvider();
		if (labelProvider instanceof IColorProvider)
			return ((IColorProvider)labelProvider).getBackground(element);
		return null;
	}
}
