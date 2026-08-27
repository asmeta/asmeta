package org.asmeta.atgt.rndgenerator;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.RuleEvaluatorObserver;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;

import asmeta.AsmCollection;

public class SimulatorForRndGeneration extends Simulator {

	public SimulatorForRndGeneration(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env);
	}
	
	
    public void addObserver(RuleEvaluatorObserver observer) {
        this.ruleEvaluator.addObserver(observer);
    }


}
