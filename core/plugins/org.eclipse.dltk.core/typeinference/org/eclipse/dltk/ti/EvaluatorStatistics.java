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

/**
 * @deprecated use IEvaluationStatisticsRequestor
 */
@Deprecated
public class EvaluatorStatistics {

	private int totalSubGoalsRequested;
	private long timeRunning;
	private int subGoalsDone;
	private int subGoalsDoneSuccessful;

	public EvaluatorStatistics(int totalSubGoalsRequested, long timeRunning,
			int subGoalsDone, int subGoalsDoneSuccessful) {
		super();
		this.totalSubGoalsRequested = totalSubGoalsRequested;
		this.timeRunning = timeRunning;
		this.subGoalsDone = subGoalsDone;
		this.subGoalsDoneSuccessful = subGoalsDoneSuccessful;
	}

	public int getTotalSubGoalsRequested() {
		return totalSubGoalsRequested;
	}

	public long getTimeRunning() {
		return timeRunning;
	}

	public int getSubGoalsDone() {
		return subGoalsDone;
	}

	public int getSubGoalsDoneSuccessful() {
		return subGoalsDoneSuccessful;
	}

}
