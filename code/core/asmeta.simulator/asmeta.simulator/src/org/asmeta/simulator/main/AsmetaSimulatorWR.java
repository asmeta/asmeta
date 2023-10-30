package org.asmeta.simulator.main;

import java.util.Stack;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.LocationSet;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.AsmCollection;

/**
 * experimental: asmeta simulator with roll back (of one state or more)
 * 
 * 14/2/19 Angelo Patrizia Silvia
 */
public class AsmetaSimulatorWR extends Simulator {

	// keep track of all the states in the trace
	Stack<State> states = new Stack<>();
	protected State cloneState;	// clone state before call runUntilEmpty()

	public AsmetaSimulatorWR(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env);
	}
	
	public AsmetaSimulatorWR(String modelName, AsmCollection asmp, Environment env, State s)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env, s);
	}

	@Override
	public UpdateSet doOneStep() {
		states.push(new State(currentState));	//New state is necessary else states contain only a reference to currentState not a copy.
		UpdateSet result = super.doOneStep();
		return result;
	}
	
	@Override
	public LocationSet runUntilEmpty() {
		cloneState = new State(super.getCurrentState());
		
		LocationSet finalState = super.runUntilEmpty();
		
		return finalState;
	}

	/**
	 * roll back one state: it can generate the empty stack exception
	 */
	public void rollBack() {
		currentState = states.pop();
		clearMon();
		ruleEvaluator = new RuleEvaluator(currentState, environment, new RuleFactory());
	}
	
	/**
	 * rollback to cloneState
	 */
	public void rollBackToState() {
		currentState = cloneState;
		clearMon();
		ruleEvaluator = new RuleEvaluator(currentState, environment, new RuleFactory());
	}
	
	public void rollbackfirstState() {
		while(states.size()!=1) {
			currentState = states.pop();}
			clearMon();
			ruleEvaluator = new RuleEvaluator(currentState, environment, new RuleFactory());	
			System.out.println("Numero stati dentro: "+states.size());
		
		
	}

}
