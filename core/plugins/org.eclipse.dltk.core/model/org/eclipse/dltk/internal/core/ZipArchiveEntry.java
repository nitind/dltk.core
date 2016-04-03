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
package org.eclipse.dltk.internal.core;

import java.util.zip.ZipEntry;

import org.eclipse.dltk.core.IArchiveEntry;

public class ZipArchiveEntry implements IArchiveEntry {

	private ZipEntry zipEntry;

	public ZipArchiveEntry(ZipEntry zipEntry) {
		this.zipEntry = zipEntry;
	}

	public ZipEntry getZipEntry() {
		return zipEntry;
	}

	public void setZipEntry(ZipEntry zipEntry) {
		this.zipEntry = zipEntry;
	}

	@Override
	public String getName() {
		return zipEntry.getName();
	}

	@Override
	public boolean isDirectory() {
		return zipEntry.isDirectory();
	}

	@Override
	public long getSize() {
		return zipEntry.getSize();
	}
}
