package org.asmeta.atgt.generator;

import java.util.ArrayList;
import java.util.Iterator;

public class Counterexample implements Iterable<ModelCheckerState> {
	private ArrayList<ModelCheckerState> counterexample;

	public Counterexample() {
		counterexample = new ArrayList<ModelCheckerState>();
	}

	public void addState(ModelCheckerState state) {
		counterexample.add(state);
	}

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