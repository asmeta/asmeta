/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/

package atgt.coverage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.tpstatus.TestConditionState;

// TODO: Auto-generated Javadoc
/**
 * the info about a coverage: the number of tps, covered , ...
 * 
 * @author garganti
 */
public class CoverageInfo {

	/** The info. */
	private Map<TestConditionState, Integer> info = new HashMap<TestConditionState, Integer>();

	/**
	 * Gets the info.
	 * 
	 * @return the info
	 */
	public Map<TestConditionState, Integer> getInfo() {
		return info;
	}

	/*
	 * number of test that are not covered by others
	 */
	/** The not covered by others. */
	public int notCoveredByOthers = 0;

	/** The time. */
	public long time = 0;

	/**
	 * Instantiates a new coverage info.
	 */
	public CoverageInfo() {
	}

	/**
	 * Adds the test with state.
	 * 
	 * @param state
	 *            the state
	 */
	public void addTestWithState(TestConditionState state) {
		Integer value = info.get(state);
		if (value == null)
			info.put(state, 1);
		else
			info.put(state, value + 1);
	}

	/**
	 * add another information.
	 * 
	 * @param other
	 *            the other
	 */
	public void addCoverageInfo(CoverageInfo other) {
		for (Entry<TestConditionState, Integer> ts_int : other.info.entrySet()) {
			TestConditionState ts = ts_int.getKey();
			if (info.containsKey(ts)) {
				Integer valother = ts_int.getValue();
				Integer valthis = info.get(ts);
				info.put(ts, valother + valthis);
			} else {
				info.put(ts, other.info.get(ts));
			}
		}
		time += other.time;
		notCoveredByOthers += other.notCoveredByOthers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String shortMsg = "COVERAGE INFO:" + info.toString()
				+ " not covered by others: " + notCoveredByOthers + " time "
				+ time;
		// covered directly or by others
		int covered = getNumber(info, TestConditionState.AssertViolated)
				+ getNumber(info, TestConditionState.Covered);
		Integer unfeasible = info.get(TestConditionState.UNFEASIBLE);
		int stillTodo = getNumber(info, TestConditionState.TODO)
				+ getNumber(info, TestConditionState.Unknown);
		assert getNumber(info, TestConditionState.Running) == 0;
		int total = covered + stillTodo
				+ ((unfeasible != null) ? unfeasible : 0);
		// int perCent = (total- unfeasible != 0) ? covered*100/(total-
		// unfeasible) : 100;
		return "COVERAGE INFO \t"
				+ "total tp: "
				+ total
				+ ", covered: "
				+ covered
				+
				/* "(" + perCent + "%) + */", still to do: "
				+ stillTodo
				+
				// print unfeasible only if found information
				((unfeasible != null) ? (", found unfeasible: " + unfeasible)
						: "");
	}

	private int getNumber(Map<TestConditionState, Integer> info,
			TestConditionState state) {
		Integer res = info.get(state);
		if (res == null)
			return 0;
		else
			return res;
	}
	// prints the data separeted by tab (useful for experiments)
	public String toStringTab() {
		// covered directly or by others
		int covered = getNumber(info, TestConditionState.AssertViolated)
				+ getNumber(info, TestConditionState.Covered);
		Integer unfeasible = info.get(TestConditionState.UNFEASIBLE);
		if (unfeasible == null) unfeasible = 0;
		int stillTodo = getNumber(info, TestConditionState.TODO)
				+ getNumber(info, TestConditionState.Unknown);
		assert getNumber(info, TestConditionState.Running) == 0;
		int total = covered + stillTodo
				+ ((unfeasible != null) ? unfeasible : 0);
		// int perCent = (total- unfeasible != 0) ? covered*100/(total-
		// unfeasible) : 100;
		return total + "\t" + covered +
		/* "(" + perCent + "%) + */"\t" + stillTodo +
		// print unfeasible only if found information
				"\t " + unfeasible;

	}

}
