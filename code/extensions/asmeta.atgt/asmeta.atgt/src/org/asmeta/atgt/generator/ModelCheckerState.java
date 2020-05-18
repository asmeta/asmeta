package org.asmeta.atgt.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ModelCheckerState {
	private Map<String, String> varValue;
	private boolean loopStart;

	public ModelCheckerState(boolean loopStart) {
		this.loopStart = loopStart;
		varValue = new HashMap<String, String>();
	}

	public void addVarValue(String var, String value) {
		varValue.put(var, value);
	}

	public String getVarValue(String var) {
		return varValue.get(var);
	}

	public String remove(String var) {
		return varValue.remove(var);
	}

	public Set<String> getVars() {
		return varValue.keySet();
	}

	public void printState() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> v: varValue.entrySet()) {
			sb.append(v.getKey() + " = " + v.getValue() + "\n");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof ModelCheckerState) {
			ModelCheckerState otherObj = (ModelCheckerState)obj;
			if(otherObj.getVars().size() == getVars().size()) {
				for(String var: getVars()) {
					if(!otherObj.getVars().contains(var) || !otherObj.getVarValue(var).equals(getVarValue(var))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}