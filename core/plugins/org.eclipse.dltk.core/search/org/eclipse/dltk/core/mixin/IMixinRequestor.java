/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.core.mixin;

public interface IMixinRequestor {
	public final static String MIXIN_NAME_SEPARATOR = MixinModel.SEPARATOR;

	public static class ElementInfo {
		/**
		 * Could be separated by MIXIN_NAME_SEPARATOR. If it is separated, then
		 * it added by splitting. Then user ask for parent, it will contain this
		 * element.
		 */
		public String key;
		/**
		 * All possible user object.
		 */
		public Object object;

		@Override
		public String toString() {
			return key + " : " + object; //$NON-NLS-1$
		}
	}

	void reportElement(ElementInfo info);
}
