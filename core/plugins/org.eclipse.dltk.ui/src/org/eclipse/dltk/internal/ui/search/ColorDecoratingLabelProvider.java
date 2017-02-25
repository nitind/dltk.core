/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
