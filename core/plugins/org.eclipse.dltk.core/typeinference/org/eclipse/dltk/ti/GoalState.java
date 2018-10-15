/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ti;

public interface GoalState {

	final static GoalState DONE = new GoalState() {
		@Override
		public String toString() {
			return "DONE"; //$NON-NLS-1$
		}
	};

	final static GoalState WAITING = new GoalState() {
		@Override
		public String toString() {
			return "WAITING"; //$NON-NLS-1$
		}
	};

	final static GoalState PRUNED = new GoalState() {
		@Override
		public String toString() {
			return "PRUNED"; //$NON-NLS-1$
		}
	};

	final static GoalState RECURSIVE = new GoalState() {
		@Override
		public String toString() {
			return "RECURSIVE"; //$NON-NLS-1$
		}
	};
}
