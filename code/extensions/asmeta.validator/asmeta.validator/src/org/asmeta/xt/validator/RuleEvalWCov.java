package org.asmeta.xt.validator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.avallaxt.validation.RuleExtractorFromMacroDecl.RuleExtractor;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.NotCompatibleDomainsException;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.ValueAssignment;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.Value;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
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

/**
 * Questa classe valuta le regole pero' tiene traccia delle macro valutate it is
 * now used!! nov 2023
 * 
 * @author AG
 *
 */
public class RuleEvalWCov extends RuleEvaluator {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RuleEvalWCov.class);

	// covered macros
	// FIXME: use of static is due to the fact that several RuleEvaluator
	// are created for the same run;
	static Set<MacroDeclaration> coveredMacros = new HashSet<>();
	// covered branches (guards in decision points from Conditional, Forall and
	// Choose rules)
	static Set<Rule> coveredBranchT = new HashSet<>();
	static Set<Rule> coveredBranchF = new HashSet<>();
	// covered rules
	static Set<Rule> coveredRules = new HashSet<>();
	// covered update rules
	static Set<UpdateRule> coveredUpdateRules = new HashSet<>();
	// covered forall rules
	static Set<ForallRule> coveredZeroIterForRule = new HashSet<>();
	static Set<ForallRule> coveredOneIterForRule = new HashSet<>();
	static Set<ForallRule> coveredMulIterForRule = new HashSet<>();
	// mapping between the original macro rules (with parameters) and the list of
	// substitute rules created during the visit
	static Map<Rule, Set<Rule>> ruleSubstitutions = new HashMap<>();

	// this must be called only once for run
	public RuleEvalWCov(State state, Environment environment, RuleFactory factory) {
		super(state, environment, factory);
	}

	// this is called when a new state requires a new evaluator
	private RuleEvalWCov(State state, Environment environment, ValueAssignment assignment) {
		super(state, environment, assignment);
	}

	// this can be called to start collecting coverage data from scratch without
	// considering what has been covered before
	public static void reset() {
		coveredMacros.clear();
		coveredBranchT.clear();
		coveredBranchF.clear();
		coveredRules.clear();
		coveredUpdateRules.clear();
		coveredZeroIterForRule.clear();
		coveredOneIterForRule.clear();
		coveredMulIterForRule.clear();
		ruleSubstitutions.clear();
	}

	@Override
	protected BooleanValue evalGuard(ConditionalRule condRule) {
		logRuleVisit("adding coverage conditional rule ==> ", condRule);
		coveredRules.add(condRule);
		BooleanValue eval = super.evalGuard(condRule);
		if (eval.getValue())
			coveredBranchT.add(condRule);
		else
			coveredBranchF.add(condRule);
		return eval;
	}

	@Override
	public UpdateSet visit(UpdateRule r) {
		logRuleVisit("adding coverage update rule ==> ", r);
		coveredRules.add(r);
		coveredUpdateRules.add(r);
		return super.visit(r);
	}

	// Counts iterations (guard-true evaluations) for THIS visit(ForallRule).
	// Do NOT make static: each evaluator instance keeps its own counter.
	// Note: nested foralls inside a doRule are evaluated by child evaluators
	// created via createRuleEvaluator(...)
	private int nIter;

	@Override
	public UpdateSet visit(ForallRule forRule) {
		logRuleVisit("adding coverage forall rule ==> ", forRule);
		coveredRules.add(forRule);
		nIter = 0;
		UpdateSet updateSet = super.visit(forRule);
		if (nIter == 0) {
			coveredZeroIterForRule.add(forRule);
			coveredBranchT.add(forRule);
		} else {
			coveredBranchF.add(forRule);
			if (nIter == 1)
				coveredOneIterForRule.add(forRule);
			else
				coveredMulIterForRule.add(forRule);
		}
		return updateSet;
	}

	@Override
	public void onForallGuardTrue() {
		nIter++;
	}

	@Override
	public UpdateSet visit(ChooseRule chooseRule) {
		logRuleVisit("adding coverage choose rule ==> ", chooseRule);
		coveredRules.add(chooseRule);
		return super.visit(chooseRule);
	}

	@Override
	public void onChooseGuardTrue(ChooseRule chooseRule) {
		coveredBranchT.add(chooseRule);
	}

	@Override
	public void onChooseGuardAlwaysFalse(ChooseRule chooseRule) {
		coveredBranchF.add(chooseRule);
	}

	// True if the LetRule visited by this visit(LetRule) is derived from a
	// ChooseRule.
	// Do NOT make static: each evaluator instance keeps its own flag.
	// Note: nested let inside a inRule are evaluated by child evaluators
	// created via createRuleEvaluator(...)
	private boolean letFromChoose;

	@Override
	public UpdateSet visit(LetRule letRule) {
		logRuleVisit("adding coverage let rule ==> ", letRule);
		letFromChoose = false;
		return super.visit(letRule);
	}

	@Override
	protected void checkInitTerm(LetRule letRule, Value initValue, Term initTerm) {
		if (initTerm instanceof LocationTermImpl) {
			String loc = ((LocationTermImpl) initTerm).getFunction().getName();
			if (loc.contains(AsmetaPrinterForAvalla.ACTUAL_VALUE))
				letFromChoose = true;
		}
	}

	@Override
	protected void afterInitExpressionVisit(LetRule letRule) {
		if (!letFromChoose)
			coveredRules.add(letRule);
	}

	@Override
	public UpdateSet visit(CaseRule caseRule) {
		logRuleVisit("adding coverage case rule from choose rule ==> ", caseRule);
		return super.visit(caseRule);
	}

	@Override
	protected void checkComparedValue(CaseRule caseRule, Value comparedValue) {
		// We look at the compared value and not at the comparing ones because the case
		// rule might not have the NOT_PICKED_NOT_ASSIGNED case
		Term term = caseRule.getTerm();
		if (term instanceof LocationTermImpl) {
			String functionName = ((LocationTermImpl) term).getFunction().getName();
			if (functionName.endsWith(AsmetaPrinterForAvalla.STATUS)) {
				String val = comparedValue.toString();
				if (val.equals(AsmetaPrinterForAvalla.ASSIGNED)) {
					coveredRules.add(caseRule);
					coveredBranchT.add(caseRule);
				}
				if (val.equals(AsmetaPrinterForAvalla.NONE)) {
					coveredRules.add(caseRule);
					coveredBranchF.add(caseRule);
				}
			}
		}
	}

	@Override
	public UpdateSet visit(BlockRule blockRule) {
		logRuleVisit("adding coverage block rule ==> ", blockRule);
		coveredRules.add(blockRule);
		return super.visit(blockRule);
	}

	@Override
	public UpdateSet visit(ExtendRule extendRule) {
		logRuleVisit("adding coverage extend rule ==> ", extendRule);
		coveredRules.add(extendRule);
		return super.visit(extendRule);
	}

	@Override
	public UpdateSet visit(SkipRule rule) {
		logRuleVisit("adding coverage skip rule ==> ", rule);
		coveredRules.add(rule);
		return super.visit(rule);
	}

	@Override
	public UpdateSet visit(SeqRule seqRule) {
		logRuleVisit("adding coverage seq rule ==> ", seqRule);
		coveredRules.add(seqRule);
		return super.visit(seqRule);
	}

	@Override
	public UpdateSet visit(MacroCallRule macroRule) throws NotCompatibleDomainsException {
		logger.debug("adding coverage " + macroRule.getCalledMacro().getName());
		coveredRules.add(macroRule);
		// keep track of all the macro evaluated
		coveredMacros.add(macroRule.getCalledMacro());
		return super.visit(macroRule);
	}

	@Override
	protected RuleEvalWCov createRuleEvaluator(State nextState, Environment environment, ValueAssignment assignment) {
		RuleEvalWCov newREC = new RuleEvalWCov(nextState, environment, assignment);
		return newREC;
	}

	@Override
	protected Rule buildNewRule(List<Term> arguments, List<VariableTerm> variables, Rule body, String signature) {
		Rule newRule = super.buildNewRule(arguments, variables, body, signature);
		// Add, if not already present, the new rule substitution performed during the
		// visit to the dictionary of rule substitutions
		if (body != null && newRule != null) {
			List<Rule> oldRules = new RuleExtractor().visit(body);
			List<Rule> newRules = new RuleExtractor().visit(newRule);
			for (int i = 0; i < oldRules.size(); i++) {
				if (ruleSubstitutions.containsKey(oldRules.get(i)))
					ruleSubstitutions.get(oldRules.get(i)).add(newRules.get(i));
				else {
					Set<Rule> ruleSet = new HashSet<>();
					ruleSet.add(newRules.get(i));
					ruleSubstitutions.put(oldRules.get(i), ruleSet);
				}
			}
		}
		return newRule;
	}

	private void logRuleVisit(String s, Rule r) {
		if (logger.isDebugEnabled()) {
			StringWriter out = new StringWriter();
			PrintWriter st = new PrintWriter(out);
			AsmPrinter asmPrint = new AsmPrinter(st);
			asmPrint.visit(r);
			logger.debug(s + out.toString() + " --- " + r);
		}
	}
}
