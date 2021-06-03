package org.asmeta.atgt.generator;

import java.util.ArrayList;
import java.util.Iterator;
/** 
 * counter example ad list of states
 * 
 *
 */
public class Counterexample implements Iterable<ModelCheckerState> {
	
	// empty counter example: there is no counter example
	public static final Counterexample EMPTY = new Counterexample();

	// teh counter example is aempty beacuse the test is unfeasible
	public static final Counterexample INFEASIBLE = new Counterexample();
	
	private ArrayList<ModelCheckerState> counterexample;

	public Counterexample() {
		counterexample = new ArrayList<ModelCheckerState>();
	}

	public void addState(ModelCheckerState state) {
		counterexample.add(state);
	}

	/**
	 * Gets the state.
	 *
	 * @param i the i-th state STARTING FROM 1 (nusmv numbers the states from 1)
	 * @return the state
	 */
	public ModelCheckerState getState(int i) {
		return counterexample.get(i - 1);
	}

	public int length() {
		return counterexample.size();
	}

	public void print() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for(ModelCheckerState state: counterexample) {
			sb.append("State " + i + "\n");
			sb.append(state.toString());
			i++;
		}
		return sb.toString();
	}

	@Override
	public Iterator<ModelCheckerState> iterator() {
		return counterexample.iterator();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Counterexample) {
			Counterexample otherObj = (Counterexample)obj;
			if(otherObj.length() == length()) {
				for(int i = 1; i <= length(); i++) {
					if(!otherObj.getState(i).equals(getState(i))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}