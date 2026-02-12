/*******************************************************************************
 * Copyright (c) 2005, 2006 ASMETA group (http://asmeta.sourceforge.net)
 * License Information: http://asmeta.sourceforge.net/licensing/
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2 as
 *   published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 *   USA
 *
 *   http://www.gnu.org/licenses/gpl.txt
 *
 *
 *******************************************************************************/

/*
 * Simulator.java
 *
 * Created on 20 maggio 2006, 13.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Utility;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.parser.util.Defs;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.LocationSet;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.State;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.readers.FileMonFuncReader;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.util.TermChecker;
import org.asmeta.simulator.value.AgentValue;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.Value;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * A simulator that executes ASM models.
 *
 */
public class Simulator {
	//
	protected List<Invariant> controlledInvariants;
	private List<Invariant> monitoredInvariants;

	/** simulator logger with only ONE appender */
	public static class SimulatorLogger {
		public static Logger logger = Logger.getLogger(Simulator.class);

		public void info(UpdateSet updateSet) {
			logger.info(updateSet);
		}

		public void info(String string) {
			logger.info(string);
		}

		public boolean isInfoEnabled() {
			return logger.isInfoEnabled();
		}

		public void debug(String string) {
			logger.debug(string);
		}

		// add appender only if needed
		// only one appender is allowed !!!
		public void addAppender(Appender outputfromSim) {
			ArrayList<Appender> appenders = Collections.list(logger.getAllAppenders());
			if (appenders.isEmpty())
				logger.addAppender(outputfromSim);
			else {
				// advise that it is trying to add a new appender !! - it should never occur
				if (!appenders.contains(outputfromSim))
					System.err.println("trying to add a new appender but there is already one");
			}
		}

		public void setLevel(Level level) {
			logger.setLevel(level);
		}
	}

	public final static SimulatorLogger logger = new SimulatorLogger();

	// NO_CHECK -> do not check invariants
	// CHECK_CONTINUE -> check but just print a message and continue
	// CHECK_STOP -> stop and throw an exception
	public enum InvariantTreament {
		NO_CHECK, CHECK_CONTINUE, CHECK_STOP
	}

	/**
	 * Check invariants flag.
	 *
	 */
	public static InvariantTreament checkInvariants = InvariantTreament.CHECK_STOP;

	// show also the update set
	public static boolean showUpdateSet = false;

	/**
	 * Package of the model to simulate.
	 */
	protected AsmCollection asmCollection;

	/**
	 * The model to simulate.
	 *
	 */
	Asm asmModel;

	/**
	 * The state.
	 *
	 */
	State currentState;

	// useful when init state is queried and controlled state ned previous values
	public State previousState;

	/**
	 * The environment.
	 *
	 */
	protected Environment environment;

	/**
	 * The rule evaluator.
	 */
	protected RuleEvaluator ruleEvaluator;

	/**
	 * The main rule of the model.
	 *
	 */
	protected Rule mainRule;

	/**
	 * The number of the current state.
	 */
	protected int numOfState;// PA: 10 giugno 2010

	/**
	 * Constructor.
	 *
	 * @param modelName the model name (without extension)
	 * @param asmp      the package of the model
	 * @param env       the environment
	 * @throws AsmModelNotFoundException if the model has not been found
	 * @throws MainRuleNotFoundException if the model has not main rule
	 */
	public Simulator(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		set(modelName, asmp, env);
		//
		currentState = initState();
		initEvaluator(currentState);
		currentState.previousLocationValues.putAll(currentState.getLocationMap());// PA: 10 giugno 2010
	}

	/**
	 * Instantiates a new simulator.
	 *
	 * @param modelName the model name
	 * @param asmp      the asmp
	 * @param env       the env
	 * @param s         the intial state
	 * @throws AsmModelNotFoundException the asm model not found exception
	 * @throws MainRuleNotFoundException the main rule not found exception
	 */
	public Simulator(String modelName, AsmCollection asmp, Environment env, State s)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		set(modelName, asmp, env);
		//
		currentState = s;
		initEvaluator(currentState);
		currentState.previousLocationValues.putAll(currentState.getLocationMap());// PA: 10 giugno 2010
	}

	// common part of the simulator
	// TODO build only two constructors
	void set(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		assert env != null;
		assert !modelName.endsWith(ASMParser.ASM_EXTENSION);
		asmCollection = asmp;
		initAsmModel(modelName);
		environment = env;
		numOfState = 0;// PA: 10 giugno 2010
		controlledInvariants = new ArrayList<Invariant>();
		monitoredInvariants = new ArrayList<Invariant>();
		// if the MF readers supports the lazy evaluation
		TermEvaluator.setAllowLazyEval(env.supportsLazyTermEval());
		// sort out the type of invariants
		getContrMonInvariants(asmCollection, controlledInvariants, monitoredInvariants);
	}

	/**
	 * Returns a simulator ready to execute the given model. The environment is read
	 * by the standard input.
	 *
	 * @param modelPath path name of the model file
	 * @return a simulator
	 * @throws Exception
	 */
	public static Simulator createSimulator(String modelPath) throws Exception {
		return createSimulator(modelPath, (String) null);
	}

	/**
	 * Returns a simulator ready to execute the given model. The environment is read
	 * by the given file.
	 *
	 * @param modelPath path name of the model file
	 * @param envPath   path name of the environment file
	 * @return a simulator
	 * @throws Exception
	 */
	protected static Simulator createSimulator(String modelPath, String envPath) throws Exception {
		Environment env;
		if (envPath == null) {
			env = new Environment(new InteractiveMFReader(System.in, System.out));
		} else {
			env = new Environment(new FileMonFuncReader(envPath));
		}
		return createSimulator(modelPath, env);
	}

	/**
	 * create a simulator using a random environment *
	 *
	 * @param modelPath path name of the model file
	 * @return a simulator
	 * @throws Exception
	 */
	protected static Simulator createSimulatorRnd(String modelPath) throws Exception {
		Environment env = new Environment(new RandomMFReader());
		return createSimulator(modelPath, env);
	}

	/**
	 * Returns a simulator ready to execute the given model. TODO use factory
	 * instead
	 *
	 * @param modelPath path name of the model file
	 * @param env       environment
	 * @return a simulator
	 * @throws Exception
	 */
	public static Simulator createSimulator(String modelPath, Environment env) throws Exception {
		// check that the file exists
		File modelFile = new File(modelPath);
		if (!modelFile.exists()) {
			throw new FileNotFoundException(modelPath);
		}
		AsmCollection asmetaPackage = ASMParser.setUpReadAsm(modelFile);
		// take the name without extension
		assert modelFile.getName().endsWith(ASMParser.ASM_EXTENSION);
		// remove the extension (allow also a point the the name?)
		String fileName = modelFile.getName().replaceFirst("[.][^.]+$", "");
		assert modelFile.getName().equals(fileName + ASMParser.ASM_EXTENSION);
		Simulator sim = new Simulator(fileName, asmetaPackage, env);
		return sim;
	}

	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * Sets the shuffle flag for the choose rule (default is false).
	 *
	 * @param flag the new value
	 */
	public void setShuffleFlag(boolean flag) {
		RuleEvaluator.isShuffled = flag;
	}

	/**
	 * Executes the machine a fixed number of times.
	 *
	 * @param ntimes transition number
	 * @return the final state
	 */
	public UpdateSet run(int ntimes) {
		// get the update set
		return runUntil(x -> false, ntimes, checkInvariants).updateSet;
	}

	// it checks invariants and throws exception
	public UpdateSet runNoCatchInv(int ntimes) {
		return runUntil(x -> false, ntimes, InvariantTreament.CHECK_STOP).updateSet;
	}

	class RunResult {
		LocationSet currentstate;
		UpdateSet updateSet;
	}

	private interface StopCondition {
		public boolean stop(UpdateSet us);
	}

// TODO
// sun untile the variable step takes a value, useful for validation asmetaV
//	public UpdateSet runUntilStepNeg() {
//		StopCondition stepNegative = new StopCondition() {			
//			@Override
//			public boolean stop(UpdateSet us) {
//				for (Entry<Location, Value> updates: us.getLocationMap().entrySet()) {
//					if (updates.getKey().getSignature().getName().equals("step__")) {
//						IntegerValue v = (IntegerValue) (updates.getValue());
//						if (v.getValue() < 0) return true;
//					}					
//				}
//				return false;
//			}
//		};
//		return runUntil(stepNegative,  Integer.MAX_VALUE, InvariantTreament.CHECK_CONTINUE).updateSet;
//	}

	// run until f becomes true
	// throw exception only if check_stop
	// f: stop condition
	private RunResult runUntil(StopCondition stopCondition, int maxNTimes, InvariantTreament check) {
		logger.debug("<Run>");
		int step = 0;
		UpdateSet updateSet = new UpdateSet();
		//
		if (check != InvariantTreament.NO_CHECK) {
			Invariant invariant = checkInvariants(ruleEvaluator.termEval, controlledInvariants);
			// System.out.println("Controllo invarianti controllati nello stato iniziale.");
			if (invariant != null) {
				if (check == InvariantTreament.CHECK_STOP) {
					throw new InvalidInvariantException(invariant, updateSet);
				} else {
					assert check == InvariantTreament.CHECK_CONTINUE;
					logger.info("<Invariant violation>");
					AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
					logger.info(tp.visit(invariant.getBody()));
					logger.info("</Invariant violation>");
				}
			}
		}
		//
		do {
			try {
				updateSet = doOneStep();
			} catch (InvalidInvariantException e) {
				if (check == InvariantTreament.CHECK_STOP)
					throw e;
				else {
					assert check == InvariantTreament.CHECK_CONTINUE;
					logger.info("<Invariant violation>");
					AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
					logger.info(tp.visit(e.getInvariant().getBody()));
					logger.info("</Invariant violation>");
					// in this case the update set must be taken for the exception
					// TODO write a test to check this
					updateSet = e.us;
				}
			}
		} while (!stopCondition.stop(updateSet) && ++step < maxNTimes);
		logger.debug("</Run>");
		RunResult result = new RunResult();
		result.currentstate = currentState;
		result.updateSet = updateSet;
		return result;
	}
	//

	/**
	 * Executes a single transition and returns the last applied update set.
	 *
	 * @return the update set
	 */
	UpdateSet doOneStep() {
		logger.debug("<Transition>");
		// STEP 1
		// Visit the main rule to compute the update set. Ask the value of the
		// monitored functions in the current state.
		// Current state is completed with the monitored values.
		UpdateSet updateSet = ruleEvaluator.visit(mainRule);
		// System.out.println("Locations updated for model
		// "+this.asmModel.getName()+updateSet.getLocationsUpdated().toString());
		// //Patrizia for debugging Jan 2021
		// STEP 2
		// Check invariants that require monitored functions (not only controlled)
		// before the update set is committed but the current state is completed
		if (checkInvariants != InvariantTreament.NO_CHECK) {
			Invariant invariant = checkInvariants(ruleEvaluator.termEval, monitoredInvariants);
			// System.out.println("Controllo invarianti monitorati");
			if (invariant != null) {
				throw new InvalidInvariantException(invariant, updateSet);
			}
		}
		//
		// STEP 3
		// store the previous state
		previousState = new State(currentState);
		// Apply the update set to apply the state changes (the new state is
		// updated in its controlled part) - monitored functions still have the
		// old values.
		currentState.fireUpdates(updateSet);
		// Now the current state has monitored old values and new controlled
		// values.
		// So it is not an actual state but a mix between the current state
		// (the monitored functions values) and next state (the controlled
		// functions values).

		// Print of the current values of monitored
		// print the next state in controlled part
		if (logger.isInfoEnabled()) {
			String monLocState = currentState.getMonLocsState();
			if (monLocState.length() > 0) {
				logger.info("<State " + numOfState + " (monitored)>");
				logger.info(monLocState);
				logger.info("</State " + numOfState + " (monitored)>");// PA: 10 giugno 2010
			}
			// if also debug, show the update set
			if (showUpdateSet) {
				logger.info("<UpdateSet - " + numOfState + ">");
				if (!updateSet.isEmpty())
					logger.info(updateSet);
				logger.info("</UpdateSet>");
			}
			// controlled part of the next state
			String contrLocState = currentState.getContrLocsState();
			logger.info("<State " + (numOfState + 1) + " (controlled)>");
			if (contrLocState.length() > 0) {
				logger.info(contrLocState);
			}
			logger.info("</State " + (numOfState + 1) + " (controlled)>");
		}
		numOfState++;//
		// STEP 4
		// Clear the monitored variables of the current state
		clearMon();
		// STEP 5
		// Check invariants over only controlled functions.
		// It is not necessary to have the complete state (indeed the values of
		// monitored function is missing).
		if (checkInvariants != InvariantTreament.NO_CHECK) {
			Invariant invariant = checkInvariants(ruleEvaluator.termEval, controlledInvariants);
			// System.out.println("Controllo invarianti controllati.");
			if (invariant != null) {
				throw new InvalidInvariantException(invariant, updateSet);
			}
		}
		logger.debug("</Transition>");
		return updateSet;
	}

	/**
	 * Executes the machine until the main rule produces an empty update set.
	 *
	 * @return the final state
	 */
	public LocationSet runUntilEmpty() {
		return runUntil(updateSet -> updateSet.isEmpty(), Integer.MAX_VALUE, checkInvariants).currentstate;
	}

	/**
	 * Executes the machine until the main rule produces a set equal to the previous
	 * one
	 *
	 * @return the final state
	 */
	public LocationSet runUntilTrivial() {
		return runUntil(updateSet -> updateSet.isTrivial(previousState), Integer.MAX_VALUE,
				checkInvariants).currentstate;
	}

	/**
	 * Deletes the content of the monitored functions. It is called at the end of
	 * the current transition.
	 */
	public void clearMon() {
		logger.debug("clear monitored vars");
		for (Iterator<Entry<Location, Value>> i = currentState.iterator(); i.hasNext();) {
			Entry<Location, Value> locVal = i.next();
			if (Defs.isMonitored(locVal.getKey().getSignature())) {
				logger.debug("clear " + locVal);
				i.remove();
			}
		}
	}

	/**
	 * It checks the invariants given as input.
	 * 
	 * @param eval       A term evaluator
	 * @param invariants a list of invariants to check
	 * @return
	 */
	protected Invariant checkInvariants(TermEvaluator eval, List<Invariant> invariants) {
		Term invariantBody;
		String invariantName;
		for (Invariant invariant : invariants) {
			invariantBody = invariant.getBody();
			invariantName = invariant.getName();
			logger.debug("<Invariant"
					+ ((invariantName == null || invariantName.equals("")) ? "" : (" name=" + invariantName)) + ">");
			BooleanValue result = (BooleanValue) eval.visit(invariantBody);
			logger.debug("<Value>" + result + "</Value>");
			logger.debug("</Invariant>");
			if (!result.getValue()) {
				return invariant;
			}
		}
		return null;
	}

	/**
	 * Makes some initializations on the model.
	 *
	 * @param modelName the model name
	 * @throws AsmModelNotFoundException
	 * @throws MainRuleNotFoundException
	 */
	private void initAsmModel(String modelName) throws AsmModelNotFoundException, MainRuleNotFoundException {
		// get the model
		asmModel = asmCollection.getMain();
		//
		assert asmModel.getName().equals(modelName);
		// check the main rule
		RuleDeclaration mainRuleDec = asmModel.getMainrule();
		if (mainRuleDec == null)
			throw new MainRuleNotFoundException(modelName);
		mainRule = mainRuleDec.getRuleBody();
	}

	/**
	 * Makes some initializations on the state.
	 *
	 * @return a fresh new state
	 */
	private State initState() {
		// NOTE the order of the initializations is important
		Initialization initialization = asmModel.getDefaultInitialState();
		State state = new State(initialization, environment);
		initAbstractConstants(state);
		initAgents(state);
		// search the self function in the StandardLibrary,
		// then assign it to the static attribute of TermEvaluator
		for (Asm asm : asmCollection) {
			String name = asm.getName();
			if (!name.equals(Utility.STANDARD_LIBRARY_NAME)) {
				continue;
			}
			Collection<?> functions = asm.getHeaderSection().getSignature().getFunction();
			for (Object o : functions) {
				Function func = (Function) o;
				String fname = func.getName();
				if (func.getArity() == 0 && fname.equals("self")) {
					TermEvaluator.self = new Location(func, new Value[0]);
					break;
				}
			}
		}
		assert TermEvaluator.self != null;
		return state;
	}

	/**
	 * Makes some initializations on the rule evaluator.
	 *
	 * @param state the initial state
	 * @return a fresh new evaluator
	 */
	protected void initEvaluator(State state) {
		RuleFactory factory = new RuleFactory();
		ruleEvaluator = new RuleEvaluator(state, environment, factory);
		return;
	}

	/**
	 * Searches the abstract constants in the signature and saves them in the
	 * initial state.
	 *
	 * @param state the initial state
	 */
	private void initAbstractConstants(State state) {
		for (Asm asm : asmCollection) {
			Collection<Function> functions = asm.getHeaderSection().getSignature().getFunction();
			for (Function func : functions) {
				// NOTE the order of controls does matter, because an agent is
				// an abstract constant but not the contrary
				if (Defs.isAgentConst(func)) {
					String name = func.getName();
					Domain signature = func.getCodomain();
					// NOTE the agent initializations is incomplete, because
					// the program )remains unspecified.
					// The remaining work is carried out by initAgents()
					AgentValue element = new AgentValue(name, signature, null);
					state.add(signature, element);
				} else if (Defs.isAbstractConst(func)) {
					String name = func.getName();
					Domain signature = func.getCodomain();
					ReserveValue element = new ReserveValue(name);
					state.add(signature, element);
				}
			}
		}
	}

	/**
	 * Searches the agent constants in the signature and sets their own program.
	 *
	 * @param state the initial state
	 */
	private void initAgents(State state) {
		AgentValue.setAsm(asmModel);
		if (state.getInitialization() != null) {
			Collection<?> agentInitList = state.getInitialization().getAgentInitialization();
			for (Object o : agentInitList) {
				AgentInitialization agentInit = (AgentInitialization) o;
				Domain domain = agentInit.getDomain();
				String domainName = domain.getName();
				Rule program = agentInit.getProgram();
				SetValue<?> agentSet = state.read(domain);
				logger.debug("<initAgents>set program for agents in " + domainName);
				for (Value<?> oo : agentSet) {
					AgentValue agent = (AgentValue) oo;
					Domain agentDomain = agent.getDomain();
					logger.debug("<agent>" + agent.getId() + (Object) agent.hashCode() + "</agent>");
					logger.debug("<program>" + program + "</program>");
					if (Defs.equals(domain, agentDomain)) {
						agent.setProgram(program);
					}
				}
				logger.debug("</initAgents>");
			}
		}
	}

	/**
	 * PA: 31/10/2010 It separates the monitored invariants (invariants which
	 * contain at least a monitored function) from controlled invariants (invariants
	 * which do not contain any monitored function).
	 *
	 * @param asmCollection the asm collection
	 * @param controlledInvariants the controlled invariants, to be filled
	 * @param monitoredInvariants the monitored invariants, to be filled
	 * @return the contr mon invariants
	 */
	static protected void getContrMonInvariants(AsmCollection asmCollection, List<Invariant> controlledInvariants,
			List<Invariant> monitoredInvariants) {
		assert controlledInvariants.isEmpty();
		assert monitoredInvariants.isEmpty();
		TermChecker mf = TermChecker.monitoredFinder;
		boolean isMonitoredInvariant;
		for (Iterator<Asm> i = asmCollection.iterator(); i.hasNext();) {
			Asm asm_i = i.next();
			Body b = asm_i.getBodySection();
			Collection<Property> propertiesList = b.getProperty();
			if (propertiesList != null) {
				for (Property property : propertiesList) {
					if (property instanceof Invariant) {
						Term body = ((Invariant) property).getBody();
						isMonitoredInvariant = mf.visit(body);
						if (isMonitoredInvariant) {
							monitoredInvariants.add((Invariant) property);
						} else {
							controlledInvariants.add((Invariant) property);
						}
					}
				}
			}
		}
	}

	public Asm getAsmModel() {
		return asmModel;
	}

	/** return the state number starting from 0 */
	public int getNumOfState() {
		return numOfState;
	}

}