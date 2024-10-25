package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.asmeta.parser.util.AsmPrinter;

import asmeta.AsmCollection;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

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
	public static SimulatorWCov createSimulatorWC(String modelPath) throws Exception {
		File modelFile = new File(modelPath);
		if (!modelFile.exists()) {
			throw new FileNotFoundException(modelPath);
		}
		AsmCollection asmetaPackage = ASMParser.setUpReadAsm(modelFile);
		String fileName = modelFile.getName().split("\\.")[0];

		return new SimulatorWCov(fileName, asmetaPackage,
				new Environment(new InteractiveMFReader(System.in, System.out)));
	}

	/**
	 * Inizializza il valutatore delle regole con quello che valuta anche la
	 * copertura
	 *
	 * @param state stato iniziale
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
	 * get the macros that were covered return the ASMETA name - which is modified
	 * because the validator rebuilds the ASM
	 */
	public List<AbstractMap.SimpleEntry<String, String>> getCoveredMacro() {
		ArrayList<AbstractMap.SimpleEntry<String, String>> s = new ArrayList<>();
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			s.add(new AbstractMap.SimpleEntry<>(md.getAsmBody().getAsm().getName(), md.getName()));
		}
		return s;
	}

	// the coverage information about the branches inside a rule
	public class BranchCovData {
		Set<Integer> coveredT;
		Set<Integer> coveredF;
		int tot;

		public BranchCovData() {
			coveredT = new HashSet<>();
			coveredF = new HashSet<>();
			tot = 0;
		}

		@Override
		public String toString() {
			String result = "(" + tot + " cond rules)\n";
			result += "Covered true: " + coveredT.toString() + "\n";
			result += "Covered false: " + coveredF.toString() + "\n";
			return result;
		}
	}

	public class UpdateCovData {
		int covered = 0, tot = 0;
		String macroName;

		public UpdateCovData(String name) {
			macroName = name;
		}

		@Override
		public String toString() {
			return macroName + "::" + covered + "/" + tot;
		}
	}

	// return the coverage of the branches (conditional rules)
	// PROBLEM is the branches of the modified ASM not the original one.
	public Map<String, BranchCovData> getCoveredBranches() {
		Map<String, BranchCovData> covData = new HashMap<>();
		System.err.println("executing getCoveredBranches");
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			System.err.println(md);
			if (!covData.containsKey(md.getName())) {
				covData.put(md.getName(), new BranchCovData());
			}
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			int tot = 0;
			Rule r;
			for (int i = 0; i < rules.size(); i++) {
				r = rules.get(i);
				if (r instanceof ConditionalRule) {
					tot++;
					if (RuleEvalWCov.coveredConRuleF.contains(r) 
							|| (RuleEvalWCov.ruleSubstitutions.containsKey(r) && 
								RuleEvalWCov.coveredConRuleF.stream().anyMatch(RuleEvalWCov.ruleSubstitutions.get(r)::contains)))
						covData.get(md.getName()).coveredF.add(i);

					if (RuleEvalWCov.coveredConRuleT.contains(r)
							|| (RuleEvalWCov.ruleSubstitutions.containsKey(r) && 
								RuleEvalWCov.coveredConRuleT.stream().anyMatch(RuleEvalWCov.ruleSubstitutions.get(r)::contains)))
						covData.get(md.getName()).coveredT.add(i);
				}
			}
			covData.get(md.getName()).tot = tot;
		}
		return covData;
	}

	// return the coverage of the update rules
	public List<AbstractMap.SimpleEntry<String, UpdateCovData>> getCoveredUpdateRules() {
		AsmPrinter printer = new AsmPrinter(new PrintWriter(System.out, true));
		ArrayList<AbstractMap.SimpleEntry<String, UpdateCovData>> s = new ArrayList<>();
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			final UpdateCovData cov = new UpdateCovData(md.getName());
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			// get all the
			rules.forEach(r -> {
				if (r instanceof UpdateRule) {
					cov.tot++;
//					System.out.println(" update rule " + r.toString());
//					printer.visit(r);
					if (RuleEvalWCov.coveredUpdateRules.contains(r))
						cov.covered++;
				}
			});
			s.add(new AbstractMap.SimpleEntry<>(md.getAsmBody().getAsm().getName(), cov));
		}
		return s;
	}

}
