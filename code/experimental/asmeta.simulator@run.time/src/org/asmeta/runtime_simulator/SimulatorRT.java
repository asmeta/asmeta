package org.asmeta.runtime_simulator;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.LocationSet;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.AsmetaSimulatorWR;
import org.asmeta.simulator.main.MainRuleNotFoundException;

import asmeta.AsmCollection;
import asmeta.definitions.Invariant;

/**
 * 
 * @author Simone Giusso
 * This class extends simulator for throw exception in run method.
 */
public class SimulatorRT extends AsmetaSimulatorWR{
	
	private int Max = 500; //Default runUntilEmpty's max step
	private int currentStep; //For only runUntilEmpty
	
	public int getMax() {
		return Max;
	}

	public int setMax(int max) {
		return Max = max;
	}
	
	public SimulatorRT(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env);
		env.timeMngt=TimeMngt.ask_user; //2021_12_01 Silvia: Aggiunto per simulare il tempo usando quello settato dall'utente 
	}
	
	public SimulatorRT(String modelName, AsmCollection asmp, Environment env, State s)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env, s);
		env.timeMngt=TimeMngt.ask_user; //2021_12_01 Silvia: Aggiunto per simulare il tempo usando quello settato dall'utente 
		checkInvariantRestart();
	}
	
	private void checkInvariantRestart() {
		logger.debug("<Run>");
		UpdateSet updateSet = new UpdateSet();
		getContrMonInvariants();
		Invariant invariant = checkInvariants(ruleEvaluator.termEval, controlledInvariants);
		//System.out.println("Controllo invariants controllati nello stato iniziale.");
		if (invariant != null) {
			System.out.println("Invariant violation!");
			throw new InvalidInvariantException(invariant, updateSet);
		}
		logger.debug("</Run>");
	}

	
	/**
	 * Return an InvalidInvariantException or UpdateClashException if it occurs
	 */
	@Override
	public UpdateSet run(int ntimes){
		// get the update set
		UpdateSet updateSet = new UpdateSet();
		
		updateSet = runNoCatchInv(ntimes);
		
		return updateSet;
	}
	
	@Override
	public LocationSet runUntilEmpty() {
		cloneState = new State(super.getCurrentState());
		
		UpdateSet updateSet = new UpdateSet();
		getContrMonInvariants();
		if (checkInvariants != InvariantTreament.NO_CHECK) {
			Invariant invariant = checkInvariants(ruleEvaluator.termEval, controlledInvariants);
			if (invariant != null) {
				throw new InvalidInvariantException(invariant, updateSet);
			}
		}	
		
		//Stop RunUntilEmpty after "max" step.
		currentStep = 0; 
					 
		do{
			updateSet = doOneStep();
			
            if(currentStep == this.Max) {
            	rollBackToState();
            	break;
            }else {    	 
            	currentStep++;
            }
            
		}while(!updateSet.isEmpty());
		
		return getCurrentState();
	}
	
	public AsmCollection getAsmCollection() {
		return asmetaPackage;
	}
	
	public int getCurrentStep() {
		return currentStep;
	}
}
 