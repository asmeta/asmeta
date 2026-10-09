package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

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
	 * Initialize the rule evaluator with the one that also computes the coverage.
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


	@FunctionalInterface
	private static interface RuleTypeChecker {
		/**
		 * Decides whether a rule should be included in coverage counting.
		 *
		 * @param r              the rule being checked
		 * @param letFromChoose  true if the rule is a 'let' from a 'choose'
		 * @param caseFromChoose true if the rule is a 'case' from a 'choose'
		 * @return true if the rule is relevant for this coverage type
		 */
		boolean checkType(Rule r, boolean letFromChoose, boolean caseFromChoose);
	}

	@FunctionalInterface
	private static interface CoverageRecorder<C extends CovData> {
	    /**
	     * Updates the given coverage data with information about a covered rule.
	     *
	     * @param data         the coverage data object to update
	     * @param r            the rule being processed
	     * @param visibleIndex index of the rule in the visible list
	     * @param ruleSubs     rule substitution mappings
	     */
		void recordCoverage(C data, Rule r, int visibleIndex, Map<Rule, Set<Rule>> ruleSubs);
	}

	/**
	 * Shared logic for all getCovered_() methods.
	 * Filters rules using {@code checker} and records coverage with {@code recorder}.
	 *
	 * @param <C>          type of coverage data
	 * @param dataFactory  factory for creating new coverage data objects
	 * @param checker      decides if a rule counts toward total coverage
	 * @param recorder     records coverage for counted rules
	 * @return a map from macro rule names to their computed coverage data
	 */
	private <C extends CovData> Map<String, C> computeCoverage(Supplier<C> dataFactory, RuleTypeChecker checker,
			CoverageRecorder<C> recorder) {
		Map<String, C> covDataMap = new HashMap<>();
		Map<Rule, Set<Rule>> ruleSubstitutions = RuleEvalWCov.ruleSubstitutions;
		for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
			String ruleCompleteName = RuleDeclarationUtils.getCompleteName(md);
			covDataMap.putIfAbsent(ruleCompleteName, dataFactory.get());
			List<Rule> rules = RuleExtractorFromMacroDecl.getAllContainedRules(md);
			C covData = covDataMap.get(ruleCompleteName);
			int tot = 0;
			// Index of visible rules (skips let-from-choose and null rules)
			int visibleIndex = 0;
			for (int i = 0; i < rules.size(); i++) {
				Rule r = rules.get(i);
				boolean letFromChoose = letFroomChoose(r);
				boolean caseFromChoose = caseFromChoose(r);
				// Count and record only rules accepted by the checker
				if (checker.checkType(r, letFromChoose, caseFromChoose)) {
					recorder.recordCoverage(covData, r, visibleIndex, ruleSubstitutions);
					tot++;
				}
				// Skip let-rules generated from choose (and nulls) so index stays consistent
	            // across scenarios where choose rules may or may not be picked
				if (!letFromChoose && r != null)
					visibleIndex++;
			}
			covData.tot = tot;
		}
		return covDataMap;
	}

	/**
	 * Computes branch coverage for all covered macros.
	 *
	 * @return a map with branch coverage data per macro
	 */
	public Map<String, BranchCovData> getCoveredBranches() {
		return computeCoverage(
				BranchCovData::new, 
				(r, letFromChoose, caseFromChoose) -> 
					r instanceof ConditionalRule	|| r instanceof ForallRule || r instanceof ChooseRule || caseFromChoose,
				(covData, r, ruleCounter, ruleSubstitutions) -> {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredBranchF))
						covData.coveredF.add(ruleCounter);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredBranchT))
						covData.coveredT.add(ruleCounter);
			});
	}

	public static final List<Class<? extends Rule>> CONSIDERED_RULES = List.of(
			BlockRule.class,
			ChooseRule.class,
			ConditionalRule.class,
			ExtendRule.class,
			ForallRule.class,
			LetRule.class,
			MacroCallRule.class,
			SkipRule.class,
			UpdateRule.class,
			SeqRule.class
		);

	/**
	 * Computes rule coverage for all covered macros.
	 *
	 * @return a map with rule coverage data per macro
	 */
	public Map<String, RuleCovData> getCoveredRules() {
		return computeCoverage(
				RuleCovData::new,
				(r, letFromChoose, caseFromChoose) -> 
					!letFromChoose && (caseFromChoose || CONSIDERED_RULES.stream().anyMatch(ruleType -> ruleType.isInstance(r))),
				(covData, r, ruleCounter, ruleSubstitutions) -> {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredRules))
						covData.covered.add(ruleCounter);
			});
	}

	/**
	 * Computes update rule coverage for all covered macros.
	 *
	 * @return a map with update rule coverage data per macro
	 */
	public Map<String, UpdateCovData> getCoveredUpdateRules() {
		return computeCoverage(
				UpdateCovData::new, 
				(r, letFromChoose, caseFromChoose) -> r instanceof UpdateRule,
				(covData, r, ruleCounter, ruleSubstitutions) -> {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredUpdateRules))
						covData.covered.add(ruleCounter);
			});
	}

	/**
	 * Computes forall rule coverage for all covered macros.
	 *
	 * @return a map with forall rule coverage data per macro
	 */
	public Map<String, ForallCovData> getCoveredForallRules() {
		return computeCoverage(
				ForallCovData::new, 
				(r, letFromChoose, caseFromChoose) -> r instanceof ForallRule,
				(covData, r, ruleCounter, ruleSubstitutions) -> {
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredZeroIterForRule))
						covData.zeroIterations.add(ruleCounter);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredOneIterForRule))
						covData.oneIteration.add(ruleCounter);
					if (isCovered(r, ruleSubstitutions, RuleEvalWCov.coveredMulIterForRule))
						covData.multipleIterations.add(ruleCounter);
			});
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
