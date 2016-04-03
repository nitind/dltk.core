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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.IFileHandle;

public abstract class AbstractContentCache implements IContentCache {
	@Override
	public synchronized String getCacheEntryAttributeString(IFileHandle handle,
			String attribute) {
		return getCacheEntryAttributeString(handle, attribute, false);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public synchronized String getCacheEntryAttributeString(IFileHandle handle,
			String attribute, boolean localonly) {
		InputStream stream = getCacheEntryAttribute(handle, attribute,
				localonly);
		if (stream != null) {
			try {
				char[] chars = Util.getInputStreamAsCharArray(stream, -1, null);
				return new String(chars);
			} catch (IOException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean setCacheEntryAttribute(IFileHandle handle, String attribute,
			String value) {
		OutputStream outputStream = getCacheEntryAttributeOutputStream(handle,
				attribute);
		if (outputStream != null) {
			try {
				outputStream.write(value.getBytes());
			} catch (IOException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
				return false;
			} finally {
				try {
					outputStream.close();
				} catch (IOException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public synchronized long getCacheEntryAttributeLong(IFileHandle handle,
			String attribute) {
		return getCacheEntryAttributeLong(handle, attribute, false);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public synchronized long getCacheEntryAttributeLong(IFileHandle handle,
			String attribute, boolean localonly) {
		InputStream stream = getCacheEntryAttribute(handle, attribute,
				localonly);
		if (stream != null) {
			try {
				DataInputStream dias = new DataInputStream(stream);
				long result = dias.readLong();
				return result;
			} catch (IOException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}
		return 0;
	}

	@Override
	public boolean setCacheEntryAttribute(IFileHandle handle, String attribute,
			long value) {
		OutputStream outputStream = getCacheEntryAttributeOutputStream(handle,
				attribute);
		if (outputStream != null) {
			try {
				DataOutputStream dout = new DataOutputStream(outputStream);
				dout.writeLong(value);
				dout.close();
			} catch (IOException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
				return false;
			} finally {
				try {
					outputStream.close();
				} catch (IOException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
			return true;
		}
		return false;
	}

}