/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.internal.core;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.core.util.Messages;

/**
 * This operation moves elements from their current container to a specified
 * destination container, optionally renaming the elements. A move operation is
 * equivalent to a copy operation, where the source elements are deleted after
 * the copy.
 * <p>
 * This operation can be used for reorganizing elements within the same
 * container.
 * 
 * @see CopyElementsOperation
 */
public class MoveElementsOperation extends CopyElementsOperation {
	/**
	 * When executed, this operation will move the given elements to the given
	 * containers.
	 */
	public MoveElementsOperation(IModelElement[] elementsToMove, IModelElement[] destContainers, boolean force) {
		super(elementsToMove, destContainers, force);
	}

	/**
	 * Returns the <code>String</code> to use as the main task name for
	 * progress monitoring.
	 */
	@Override
	protected String getMainTaskName() {
		return Messages.operation_moveElementProgress;
	}

	@Override
	protected boolean isMove() {
		return true;
	}
}
