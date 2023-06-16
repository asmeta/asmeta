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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.LocationSet;
import org.asmeta.simulator.RuleEvaluatorAllUpdateSets;
import org.asmeta.simulator.SetUpdateSet;
import org.asmeta.simulator.State;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.readers.FileMonFuncReader;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.AgentValue;
import org.asmeta.simulator.value.BooleanValue;
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
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * A simulator that executes ASM models.
 *
 */
public class SimulatorAllUpdateSets extends Simulator {

	static Logger logger = Logger.getLogger(SimulatorAllUpdateSets.class);

	/**
	 * Check invariants flag.
	 *
	 */
	public static boolean checkAxioms = true;

	private static BufferedReader in;


	/**
	 * The rule evaluator.
	 */
	protected RuleEvaluatorAllUpdateSets ruleEvaluator;
	
	
	/**
	 * Constructor.
	 *
	 * @param modelName
	 *            the model name
	 * @param asmp
	 *            the package of the model
	 * @param env
	 *            the environment
	 * @throws AsmModelNotFoundException
	 *             if the model has not been found
	 * @throws MainRuleNotFoundException
	 *             if the model has not main rule
	 */
	public SimulatorAllUpdateSets(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName,asmp,env);
		assert env != null;
		asmetaPackage = asmp;
		initAsmModel(modelName);
		environment = env;
		currentState = initState();
		initEvaluator(currentState);
		numOfState = 0;//PA: 10 giugno 2010
		currentState.previousLocationValues.putAll(currentState.locationMap);//PA: 10 giugno 2010
	}

	/**
	 * Returns a simulator ready to execute the given model. The environment is
	 * read by the standard input.
	 *
	 * @param modelPath
	 *            path name of the model file
	 * @return a simulator
	 * @throws Exception
	 */
	public static SimulatorAllUpdateSets createSimulator(String modelPath) throws Exception {
		return createSimulator(modelPath, (String) null);
	}

	/**
	 * Returns a simulator ready to execute the given model. The environment is
	 * read by the given file.
	 *
	 * @param modelPath
	 *            path name of the model file
	 * @param envPath
	 *            path name of the environment file
	 * @return a simulator
	 * @throws Exception
	 */
	protected static SimulatorAllUpdateSets createSimulator(String modelPath, String envPath)
			throws Exception {
		Environment env;
		in = new BufferedReader(new InputStreamReader(System.in));
		if (envPath == null) {
			env = new Environment(
					new InteractiveMFReader(System.in, System.out));
		} else {
			env = new Environment(new FileMonFuncReader(envPath));
		}
		return createSimulator(modelPath, env);
	}

	/**
	 * create a simulator using a random environment *
	 *
	 * @param modelPath
	 *            path name of the model file
	 * @return a simulator
	 * @throws Exception
	 */
	protected static SimulatorAllUpdateSets createSimulatorRnd(String modelPath)
			throws Exception {
		in = new BufferedReader(new InputStreamReader(System.in));
		Environment env = new Environment(new RandomMFReader());
		return createSimulator(modelPath, env);
	}

	/**
	 * Returns a simulator ready to execute the given model.
	 *
	 * @param modelPath
	 *            path name of the model file
	 * @param env
	 *            environment
	 * @return a simulator
	 * @throws Exception
	 */
	public static SimulatorAllUpdateSets createSimulator(String modelPath, Environment env)
			throws Exception {
		File modelFile = new File(modelPath);
		if (!modelFile.exists()) {
			throw new FileNotFoundException(modelPath);
		}
		AsmCollection asmetaPackage = ASMParser.setUpReadAsm(modelFile);
		String fileName = modelFile.getName().split("\\.")[0];
		SimulatorAllUpdateSets sim = new SimulatorAllUpdateSets(fileName, asmetaPackage, env);
		return sim;
	}

	/*public static SimulatorAllUpdateSets createSimulator(String modelPath, Environment env, AsmCollection asmetaPackage) throws Exception {
		File modelFile = new File(modelPath);
		if (!modelFile.exists()) {
			throw new FileNotFoundException(modelPath);
		}
		String fileName = modelFile.getName().split("\\.")[0];
		SimulatorAllUpdateSets sim = new SimulatorAllUpdateSets(fileName, asmetaPackage, env);
		return sim;
	}*/

	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	@Override
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * Sets the shuffle flag for the choose rule (default is false).
	 *
	 * @param flag
	 *            the new value
	 */
	/*public void setShuffleFlag(boolean flag) {
		RuleEvaluatorAllUpdateSets.isShuffled = flag;
	}*/

	/**
	 * Executes the machine a fixed number of times.
	 *
	 * @param ntimes
	 *            transition number
	 * @return the final state
	 */
	@Override
	public UpdateSet run(int ntimes) {
		logger.debug("<Run>");
		UpdateSet updateSet = new UpdateSet();
		try {
			for (; ntimes > 0; ntimes--) {
				updateSet = doOneStep();
			}
		} catch (InvalidInvariantException e) {
			
		}
		logger.debug("</Run>");
		return updateSet;
	}

	public SetUpdateSet getNextUpdates() {
		return ruleEvaluator.visit(mainRule);
	}

	/**
	 * Executes a single transition.
	 *
	 * @return the update set
	 */
	@Override
	UpdateSet doOneStep() {
		logger.debug("<Transition>");
		SetUpdateSet updateSets = getNextUpdates();
		int numOfUpdateSets = updateSets.size(); 
		UpdateSet updateSet;
		String chooseString;
		//Almeno un update set dovrebbe esserci sempre.
		//Anche la sola skip rule ritorna un update set vuoto.
		if(numOfUpdateSets == 0) {
			throw new Error("There should be at least one update set.");
		}
		if(numOfUpdateSets == 1) {
			updateSet = updateSets.get(0);
			if(!updateSet.isEmpty()) {
				System.out.println("There is just one update set:");
				System.out.println(updateSet);
			}
		}
		else {
			System.out.println("There are " + numOfUpdateSets + " update sets available.");
			for(int i = 0; i < numOfUpdateSets; i++) {
				System.out.println("------- UpdateSet " + i + " -------");
				System.out.println(updateSets.get(i));
				System.out.println("---------------------------\n");
			}
			chooseString = "Choose an updateSet from the above (0-" + (numOfUpdateSets - 1) + "): ";
			System.out.print(chooseString);
			int selectedUpdateSet = -1;
			while (selectedUpdateSet == -1) {
				try {
					String insertedText = in.readLine();
					int parsedInt = Integer.parseInt(insertedText);
					if(parsedInt >=0 && parsedInt < numOfUpdateSets) {
						selectedUpdateSet = parsedInt;
						break;
					}
				}
				catch (IOException e) {}
				catch(NumberFormatException nfe) {}
				System.out.print(chooseString);
			}
			System.out.println("\nChosen updateSet is: " + selectedUpdateSet + "\n");
			updateSet = updateSets.get(selectedUpdateSet);
		}
		currentState.fireUpdates(updateSet);
		if (checkAxioms) {
			Invariant invariant = checkAxioms(ruleEvaluator.termEval);
			if (invariant != null) {
				throw new InvalidInvariantException(invariant, updateSet);
			}
		}
		if(logger.isInfoEnabled()) {
	 		String monLocState = currentState.getMonLocsState();
			if(monLocState.length() > 0) {
				logger.info("<State " + numOfState + " (monitored)>\n" + monLocState + "\n</State " + numOfState + " (monitored)>");//PA: 10 giugno 2010
			}
			String contrLocState = currentState.getContrLocsState();
			logger.info("<State " + (numOfState + 1) + " (controlled)>");
			if(contrLocState.length() > 0) {
				logger.info(contrLocState);
			}
			logger.info("</State " + (numOfState + 1) + " (controlled)>");
		}
		numOfState++;
		clearMon();
		logger.debug("</Transition>");
		return updateSet;
	}

	/**
	 * Executes the machine until the main rule produces an empty update set.
	 *
	 * @return the final state
	 */
	@Override
	public LocationSet runUntilEmpty() {
		logger.debug("<Run>");
		UpdateSet updateSet;
		do {
			updateSet = doOneStep();
		} while (!updateSet.isEmpty());
		logger.debug("</Run>");
		return currentState;
	}

	public LocationSet runUntilEmptyForMonitoring() {
		SetUpdateSet updateSets;
		UpdateSet updateSet;
		do {
			updateSets = getNextUpdates();
			int numOfUpdateSets = updateSets.size(); 
			if(numOfUpdateSets == 1) {
				updateSet = updateSets.get(0);
				fireUpdates(updateSet);
			}
			else {
				throw new Error("Too many update sets in run until empty.");
			}
		} while (!updateSet.isEmpty());
		return currentState;
	}

	/**
	 * Deletes the content of the monitored functions.<br>
	 * It is called at the end of the current transition.
	 */
	@Override
	public void clearMon() {
		logger.debug("clear monitored vars");
		for(Iterator<Entry<Location,Value>> i = currentState.iterator();i.hasNext();){
			 Entry<Location, Value> locVal = i.next();
			 if (Defs.isMonitored(locVal.getKey().getSignature())){
				 logger.debug("clear "+ locVal);
				 i.remove();
			 }
		}
	}

	public void fireUpdates(UpdateSet updateSet) {
		currentState.fireUpdates(updateSet);
		clearMon();
	}

	/**
	 * Checks the invariants.
	 *
	 * @param eval
	 *            a term evaluator
	 * @return null if the invariants are right, otherwise the first one wrong
	 */
	private Invariant checkAxioms(TermEvaluator eval) {
		// enable use of pre
		//eval.isPreEnabled = true;
		// get all the invariants for every ASm in the AsmCollection
		for (Iterator<Asm> i = asmetaPackage.iterator(); i.hasNext();) {
			Asm asm_i = i.next();
			Collection<Property> propertiesList = asm_i.getBodySection().getProperty();
			if (propertiesList != null) {
				for (Property property : propertiesList) {
					if(property instanceof Invariant){
						Term body = ((Invariant)property).getBody();
						String name = property.getName();
						logger.debug(
							"<Axiom" + ((name == null || name.equals("")) ? "" : (" name=" + name)) + ">");
						BooleanValue result = (BooleanValue) eval.visit(body);						
						logger.debug("<Value>" + result + "</Value>");
						logger.debug("</Axiom>");
						if (!result.getValue()) {
							//eval.isPreEnabled  = false;//PA: 18 giugno 2010
							return (Invariant)property;
						}
					}
				}
			}
		}
		//eval.isPreEnabled  = false;//PA: 18 giugno 2010
		return null;
	}

	/**
	 * Makes some initializations on the model.
	 *
	 * @param modelName
	 *            the model name
	 * @throws AsmModelNotFoundException
	 * @throws MainRuleNotFoundException
	 */
	private void initAsmModel(String modelName) throws AsmModelNotFoundException,
			MainRuleNotFoundException {
		// get the model
		asmModel = asmetaPackage.getMain();
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
		for (Asm asm : asmetaPackage) {
			String name = asm.getName();
			if (!name.equals("StandardLibrary")) {
				continue;
			}
			Collection<?> functions = 
				asm.getHeaderSection().getSignature().getFunction();
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
	 * @param state
	 *            the initial state
	 * @return a fresh new evaluator
	 */
	@Override
	protected void initEvaluator(State state) {
		RuleFactory factory = new RuleFactory();
		ruleEvaluator = new RuleEvaluatorAllUpdateSets(state, environment, factory);
		return;
	}

	/**
	 * Searches the abstract constants in the signature and saves them in the
	 * initial state.
	 *
	 * @param state
	 *            the initial state
	 */
	private void initAbstractConstants(State state) {
		for (Asm asm : asmetaPackage) {
			Collection<Function> functions = 
				asm.getHeaderSection().getSignature().getFunction();
			for (Function func : functions) {
				// NOTE the order of controls does matter, because an agent is
				// an abstract constant but not the contrary
				if (Defs.isAgentConst(func)) {
					String name = func.getName();
					Domain signature = func.getCodomain();
					// NOTE the agent initializations is incomplete, because
					// the program remains unspecified.
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
	 * @param state
	 *            the initial state
	 */
	private void initAgents(State state) {
		AgentValue.setAsm(asmModel);
		if (state.getInitialization() != null) {
			Collection<?> agentInitList = 
				state.getInitialization().getAgentInitialization();
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
					logger.debug("<agent>" + agent.getId()
							+ (Object) agent.hashCode() + "</agent>");
					logger.debug("<program>" + program + "</program>");
					if (Defs.equals(domain, agentDomain)) {
						agent.setProgram(program);
					}
				}
				logger.debug("</initAgents>");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		//SimulatorAllUpdateSets s = createSimulator("../../../asm_examples/examples/simple_ex/example.asm");
		//SimulatorAllUpdateSets s = createSimulator("../../../asm_examples/examples/simple_ex/SwapSort.asm");
		//SimulatorAllUpdateSets s = createSimulator("../../../asm_examples/examples/simple_ex/swapSortOnSeq.asm");
		//SimulatorAllUpdateSets s = createSimulator("../../../asm_examples/examples/agents/population.asm");//Errore updateClash
		//SimulatorAllUpdateSets s = createSimulator("../../../asm_examples/examples/agents/producerConsumerGround.asm");
		//SimulatorAllUpdateSets s = createSimulator("../../../asm_examples/examples/ferryman/ferrymanSimulator.asm");
		//SimulatorAllUpdateSets s = createSimulator("../../experimental/programMonitoring/models/sort/randomSort.asm");
		SimulatorAllUpdateSets s = createSimulator("../../experimental/programMonitoring/models/railroadGate.asm");
		//s.runUntilEmpty();
		while(true) {
			s.doOneStep();
		}
	}
}