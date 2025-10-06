package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.avallaxt.validation.RuleExtractorFromMacroDecl;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.impl.LocationTermImpl;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
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
	 * Initialize the rule evaluator with the one that also computes the coverage
	 *
	 * @param state the initial state
	 * @return the initialized rule evaluator
	 */
	@Override
	protected void initEvaluator(State state) {
		logger.info("init the simulator with coverage evaluator");
		RuleFactory factory = new RuleFactory();
		ruleEvaluator = new RuleEvalWCov(state, environment, factory);
		return;
	}

	// return the coverage of the branches (conditional rules)
	// NOTE: uses the branches of the modified ASM, not the original one.
	public Map<String, BranchCovData> getCoveredBranches() {
		Map<String, BranchCovData> covData = new HashMap<>();
		Map<Rule, Set<Rule>> ruleSubstitutions = RuleEvalWCov.ruleSubstitutions;
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			String ruleCompleteName = RuleDeclarationUtils.getCompleteName(md);
			covData.putIfAbsent(ruleCompleteName, new BranchCovData());
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			BranchCovData singleMacroCovData = covData.get(ruleCompleteName);
			int tot = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				boolean letFromChoose = false;
				if (r instanceof LetRule) {
					EList<Term> initExpr = ((LetRule) r).getInitExpression();
					for (Term initTerm : initExpr) {
						if (initTerm instanceof LocationTermImpl) {
							String loc = ((LocationTermImpl) initTerm).getFunction().getName();
							if (loc.contains(AsmetaPrinterForAvalla.ACTUAL_VALUE)) {
								letFromChoose = true;
								break;
							}
						}
					}
				}
				if (r instanceof ConditionalRule || r instanceof ForallRule || r instanceof ChooseRule
						|| letFromChoose) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredBranchF))
						singleMacroCovData.coveredF.add(i);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredBranchT))
						singleMacroCovData.coveredT.add(i);
					tot++;
				}
			}
			singleMacroCovData.tot = tot;
		}
		return covData;
	}
	
	private static final List<Class<? extends Rule>> CONSIDERED_RULES = List.of(
			BlockRule.class, 
			ChooseRule.class, 
			ConditionalRule.class,
			ExtendRule.class,
			ForallRule.class,
			LetRule.class,
			MacroCallRule.class,
			SkipRule.class,
			UpdateRule.class
		);
	
	// return the coverage of the rules
	// NOTE: uses the branches of the modified ASM, not the original one.
	public Map<String, RuleCovData> getCoveredRules() {
		Map<String, RuleCovData> covData = new HashMap<>();
		Map<Rule, Set<Rule>> ruleSubstitutions = RuleEvalWCov.ruleSubstitutions;
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			String ruleCompleteName = RuleDeclarationUtils.getCompleteName(md);
			covData.putIfAbsent(ruleCompleteName, new RuleCovData());
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			RuleCovData singleMacroCovData = covData.get(ruleCompleteName);
			int tot = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				if (CONSIDERED_RULES.stream().anyMatch(ruleType -> ruleType.isInstance(r))) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredRules))
						singleMacroCovData.covered.add(i);
					tot++;
				}
			}
			singleMacroCovData.tot = tot;
		}
		return covData;
	}

	// return the coverage of the update rules
	// NOTE: uses the update rules of the modified ASM, not the original one.
	public Map<String, UpdateCovData> getCoveredUpdateRules() {
		Map<String, UpdateCovData> covData = new HashMap<>();
		Map<Rule, Set<Rule>> ruleSubstitutions = RuleEvalWCov.ruleSubstitutions;
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			String ruleCompleteName = RuleDeclarationUtils.getCompleteName(md);
			covData.putIfAbsent(ruleCompleteName, new UpdateCovData());
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			UpdateCovData singleMacroCovData = covData.get(ruleCompleteName);
			int tot = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				if (r instanceof UpdateRule) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredUpdateRules))
						singleMacroCovData.covered.add(i);
					tot++;
				}
			}
			singleMacroCovData.tot = tot;
		}
		return covData;
	}

	// return the coverage of the loops (forall rules)
	// NOTE: uses the loops of the modified ASM, not the original one.
	public Map<String, LoopCovData> getCoveredLoops() {
		Map<String, LoopCovData> covData = new HashMap<>();
		Map<Rule, Set<Rule>> ruleSubstitutions = RuleEvalWCov.ruleSubstitutions;
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			String ruleCompleteName = RuleDeclarationUtils.getCompleteName(md);
			covData.putIfAbsent(ruleCompleteName, new LoopCovData());
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			LoopCovData singleMacroCovData = covData.get(ruleCompleteName);
			int tot = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				if (r instanceof ForallRule) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredZeroIterForRule))
						singleMacroCovData.zeroIterations.add(i);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredOneIterForRule))
						singleMacroCovData.oneIteration.add(i);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredMulIterForRule))
						singleMacroCovData.multipleIterations.add(i);
					tot++;
				}
			}
			singleMacroCovData.tot = tot;
		}
		return covData;
	}

	/**
	 * Check if a rule is covered. <br>
	 * If a rule obtained as a result of a substitution is covered, then the
	 * original rule from which it was derived is considered covered.
	 */
	private static <T extends Rule> boolean isCovered(Rule r, Map<Rule, Set<Rule>> ruleSubs, Set<T> coveredRules) {
		return coveredRules.contains(r)
				|| (ruleSubs.containsKey(r) && coveredRules.stream().anyMatch(ruleSubs.get(r)::contains));
	}

}
