/*******************************************************************************
 * Copyright (c) 2011, 2017 NumberFour AG and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ui.documentation;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

class DocumentationResponseDelegate implements IDocumentationResponse {
	private final IDocumentationResponse target;

	@Override
	public String getTitle() {
		return target.getTitle();
	}

	@Override
	public ImageDescriptor getImage() {
		return target.getImage();
	}

	@Override
	public Object getObject() {
		return target.getObject();
	}

	@Override
	public URL getURL() throws IOException {
		return target.getURL();
	}

	@Override
	public Reader getReader() throws IOException {
		return target.getReader();
	}

	@Override
	public String getText() throws IOException {
		return target.getText();
	}

	public DocumentationResponseDelegate(IDocumentationResponse target) {
		this.target = target;
	}

}
