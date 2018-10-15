/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
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
