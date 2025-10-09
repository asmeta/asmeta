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
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

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
			int ruleCounter = 0;
			// Store the covered rules using their position in the rules list as if let
			// rules translated from choose (and null) were not in the rules list
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				boolean letFromChoose = letFroomChoose(r);
				boolean caseFromChoose = caseFromChoose(r);
				if (r instanceof ConditionalRule || r instanceof ForallRule || r instanceof ChooseRule
						|| caseFromChoose) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredBranchF))
						singleMacroCovData.coveredF.add(ruleCounter);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredBranchT))
						singleMacroCovData.coveredT.add(ruleCounter);
					tot++;
				}
				// Do not count let rules translated from choose (and null). This is required
				// when multiple scenarios are validated together and at least one choose rule
				// is picked in one scenario but not in another
				if (!letFromChoose && r != null)
					ruleCounter++;
			}
			singleMacroCovData.tot = tot;
		}
		return covData;
	}

	private static final List<Class<? extends Rule>> CONSIDERED_RULES = List.of(BlockRule.class, ChooseRule.class,
			ConditionalRule.class, ExtendRule.class, ForallRule.class, LetRule.class, MacroCallRule.class,
			SkipRule.class, UpdateRule.class, SeqRule.class);

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
			int ruleCounter = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				boolean letFromChoose = letFroomChoose(r);
				boolean caseFroomChoose = caseFromChoose(r);
				if (!letFromChoose && (caseFroomChoose
						|| CONSIDERED_RULES.stream().anyMatch(ruleType -> ruleType.isInstance(r)))) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredRules))
						singleMacroCovData.covered.add(ruleCounter);
					tot++;
				}
				if (!letFromChoose && r != null)
					ruleCounter++;
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
			int ruleCounter = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				boolean letFromChoose = letFroomChoose(r);
				if (r instanceof UpdateRule) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredUpdateRules))
						singleMacroCovData.covered.add(ruleCounter);
					tot++;
				}
				if (!letFromChoose && r != null)
					ruleCounter++;
			}
			singleMacroCovData.tot = tot;
		}
		return covData;
	}

	// return the coverage of the forall rules
	// NOTE: uses the loops of the modified ASM, not the original one.
	public Map<String, ForallCovData> getCoveredForallRules() {
		Map<String, ForallCovData> covData = new HashMap<>();
		Map<Rule, Set<Rule>> ruleSubstitutions = RuleEvalWCov.ruleSubstitutions;
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			String ruleCompleteName = RuleDeclarationUtils.getCompleteName(md);
			covData.putIfAbsent(ruleCompleteName, new ForallCovData());
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			ForallCovData singleMacroCovData = covData.get(ruleCompleteName);
			int tot = 0;
			int ruleCounter = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				boolean letFromChoose = letFroomChoose(r);
				if (r instanceof ForallRule) {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredZeroIterForRule))
						singleMacroCovData.zeroIterations.add(ruleCounter);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredOneIterForRule))
						singleMacroCovData.oneIteration.add(ruleCounter);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredMulIterForRule))
						singleMacroCovData.multipleIterations.add(ruleCounter);
					tot++;
				}
				if (!letFromChoose && r != null)
					ruleCounter++;
			}
			singleMacroCovData.tot = tot;
		}
		return covData;
	}

	/**
	 * Return true if the rule is a let rule and is the result of the translation of
	 * a picked choose rule (i.e. a choose rule for which at least a variable is
	 * picked at least once in the scenario)
	 */
	private boolean letFroomChoose(Rule r) {
		boolean letFromChoose = false;
		if (r instanceof LetRule) {
			EList<Term> initExpr = ((LetRule) r).getInitExpression();
			for (Term initTerm : initExpr) {
				if (initTerm instanceof LocationTermImpl) {
					String loc = ((LocationTermImpl) initTerm).getFunction().getName();
					if (loc.endsWith(AsmetaPrinterForAvalla.ACTUAL_VALUE)) {
						letFromChoose = true;
						break;
					}
				}
			}
		}
		return letFromChoose;
	}

	/**
	 * Return true if the rule is a case rule and is the result of the translation
	 * of a picked choose rule (i.e. a choose rule for which at least a variable is
	 * picked at least once in the scenario)
	 */
	protected boolean caseFromChoose(Rule r) {
		boolean caseFromChoose = false;
		if (r instanceof CaseRule) {
			CaseRule caseRule = (CaseRule) r;
			Term term = caseRule.getTerm();
			if (term instanceof LocationTermImpl) {
				String functionName = ((LocationTermImpl) term).getFunction().getName();
				if (functionName.endsWith(AsmetaPrinterForAvalla.STATUS)) {
					// Double check looking at the comparing terms
					EList<Term> caseComparingTerms = caseRule.getCaseTerm();
					if (caseComparingTerms.stream()
							.anyMatch(t -> t.toString().contains(AsmetaPrinterForAvalla.ASSIGNED)))
						caseFromChoose = true;
				}
			}
		}
		return caseFromChoose;
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
