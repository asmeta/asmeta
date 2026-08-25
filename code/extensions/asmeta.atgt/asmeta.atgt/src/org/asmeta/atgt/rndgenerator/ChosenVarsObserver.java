package org.asmeta.atgt.rndgenerator;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.RuleEvaluatorObserver;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;

class ChosenVarsObserver implements RuleEvaluatorObserver {

	
	Map<LogicalVarChoosen, Value> pickedValues = new HashMap<>();  
	
	@Override
	public void update(Object change) {
		java.util.HashMap<String,Value> assignemnts = (HashMap<String, Value>) change;
		assignemnts.entrySet().forEach(x  -> {
			String[] var = x.getKey().split("in");
			LogicalVarChoosen loc = new LogicalVarChoosen(var[0], var[1]);
			pickedValues.put(loc, x.getValue());
		});
	}

	public Map<LogicalVarChoosen, Value> getPickedValues() {
		HashMap<LogicalVarChoosen, Value> returnVal = new HashMap<LogicalVarChoosen, Value>(pickedValues);
		pickedValues.clear();
		return returnVal;		
	}
}

class LogicalVarChoosen extends Location{

	private String logicalVar;
	private String ruleDecl;

	public LogicalVarChoosen(String logicalVar, String ruleDecl) {
		super(null, null);
		this.logicalVar = logicalVar;
		this.ruleDecl =ruleDecl;
	}
	
	/**
	 * Returns the location name.
	 * 
	 * @return the location name
	 */
	protected String getName() {
		return logicalVar + " in " + ruleDecl;
	}

	@Override
	public String toString() {
		return getName();
	}

	
	
}