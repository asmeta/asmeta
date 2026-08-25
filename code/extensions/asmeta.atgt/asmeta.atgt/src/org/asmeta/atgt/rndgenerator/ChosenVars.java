package org.asmeta.atgt.rndgenerator;

import org.asmeta.simulator.RuleEvaluatorObserver;

class ChosenVars implements RuleEvaluatorObserver{

	@Override
	public void update(Object change) {
		System.out.println(change.getClass());
		
	}
	
}
