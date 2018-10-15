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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.dltk.core.IArchive;
import org.eclipse.dltk.core.IArchiveEntry;

public class ZipArchiveFile implements IArchive {

	private ZipFile zipFile;
	public ZipArchiveFile(File file) throws ZipException, IOException {
		zipFile = new ZipFile(file);
	}

	public ZipArchiveFile(String zipName) throws IOException {
		zipFile = new ZipFile(zipName);
	}

	@Override
	public InputStream getInputStream(IArchiveEntry entry) throws IOException {
		ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) entry;
		return zipFile.getInputStream(zipArchiveEntry.getZipEntry());
	}

	@Override
	public IArchiveEntry getArchiveEntry(String name) {
		return new ZipArchiveEntry(zipFile.getEntry(name));
	}

	@Override
	public Enumeration<? extends IArchiveEntry> getArchiveEntries() {
		final Enumeration<? extends ZipEntry> zipEnumeration = zipFile
				.entries();

		return new Enumeration<IArchiveEntry>() {

			@Override
			public boolean hasMoreElements() {
				return zipEnumeration.hasMoreElements();
			}

			@Override
			public IArchiveEntry nextElement() {
				return new ZipArchiveEntry(zipEnumeration.nextElement());
			}

		};
	}

	@Override
	public void close() throws IOException {
		zipFile.close();
	}

	@Override
	public String getName() {
		return zipFile.getName();
	}

}
