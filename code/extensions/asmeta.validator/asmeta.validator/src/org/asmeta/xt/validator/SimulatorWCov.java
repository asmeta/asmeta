package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

public class SimulatorWCov extends Simulator {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SimulatorWCov.class);

	private SimulatorWCov(String modelName, AsmCollection asmp, Environment env)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env);
	}

	// create a new Simulator with the coverage tracing
	public static SimulatorWCov createSimulatorWC(String modelPath)
			throws Exception {
		File modelFile = new File(modelPath);
		if (!modelFile.exists()) {
			throw new FileNotFoundException(modelPath);
		}
		AsmCollection asmetaPackage = ASMParser.setUpReadAsm(modelFile);
		String fileName = modelFile.getName().split("\\.")[0];

		return new SimulatorWCov(fileName, asmetaPackage, new Environment(
				new InteractiveMFReader(System.in, System.out)));
	}

	/**
	 * Inizializza il valutatore delle regole con quello che valuta anche la
	 * copertura
	 * 
	 * @param state
	 *            stato iniziale
	 * @return il valutatore delle regole inizializzato
	 */
	@Override
	protected void initEvaluator(State state) {
		logger.info("init the simulator with coverage evaluator");
		RuleFactory factory = new RuleFactory();
		ruleEvaluator = new RuleEvalWCov(state, environment, factory);
		return;
	}
	/**
	 * get the macros that were covered
	 * return the ASMETA name - which is modified becuas ethe validator rebuils the ASM
	 */
	public List<AbstractMap.SimpleEntry<String, String>> getCoveredMacro() {
		ArrayList<AbstractMap.SimpleEntry<String, String>> s = new ArrayList<>();
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {			
			s.add(new AbstractMap.SimpleEntry<>(md.getAsmBody().getAsm().getName(),md.getName()));
		}
		return s;
	}
}
