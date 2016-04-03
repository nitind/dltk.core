/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.core.caching;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.mixin.IMixinRequestor;

public class MixinModelCollector extends AbstractDataSaver implements
		IMixinRequestor {

	@Override
	public void reportElement(ElementInfo info) {
		try {
			if (info != null && info.key != null) {
				writeString(info.key);
			}
		} catch (IOException e) {
		}
	}

	public byte[] getBytes() {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			saveTo(stream);
		} catch (IOException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return stream.toByteArray();
	}
}
