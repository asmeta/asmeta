package org.asmeta.xt.validator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.NotCompatibleDomainsException;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.RuleSubstitution;
import org.asmeta.simulator.State;
import org.asmeta.simulator.TermAssignment;
import org.asmeta.simulator.TermSubstitution;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.ValueAssignment;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.asmeta.xt.validator.RuleExtractorFromMacroDecl.RuleExtractor;

import asmeta.definitions.RuleDeclaration;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

/** Questa classe valuta le regole
 * pero' tiene traccia delle macro valutate
 * it is now used!! nov 2023
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
	static Collection<MacroDeclaration> coveredMacros;
	// covered guards in conditional rules
	static Collection<ConditionalRule> coveredConRuleT;
	static Collection<ConditionalRule> coveredConRuleF;
	// covered updaterules
	static Collection<UpdateRule> coveredUpdateRules;
	// mapping between the original macro rules (with parameters) and the list of substitute rules created during the visit
	static Map<Rule, Set<Rule>> ruleSubstitutions;
	

	// this must be called only once for run
	public RuleEvalWCov(State state, Environment environment,
			RuleFactory factory) {
		super(state, environment, factory);
		// trying to build the new covered macro only if null (the first time)
		if (coveredMacros == null) coveredMacros = new HashSet<>();
		if (coveredConRuleT == null) coveredConRuleT = new HashSet<>();
		if (coveredConRuleF == null) coveredConRuleF = new HashSet<>();
		if (coveredUpdateRules == null) coveredUpdateRules = new HashSet<>();
		if (ruleSubstitutions == null) ruleSubstitutions = new HashMap<>();
	}

	// this is called when a new state requires a new evaluator
	private RuleEvalWCov(State state, Environment environment,
			ValueAssignment assignment) {
		super(state, environment, assignment);
	}
	
	// this can be called to start collecting coverage data from scratch without considering what has been covered before
	public static void reset() {
		coveredMacros = null;
		coveredConRuleT = null;
		coveredConRuleF = null;
		coveredUpdateRules = null;
		ruleSubstitutions = null;
	}

	@Override
	protected BooleanValue evalGuard(ConditionalRule condRule) {
		BooleanValue eval = super.evalGuard(condRule);
		if (logger.isDebugEnabled()) {
			StringWriter out = new StringWriter();
			PrintWriter st = new PrintWriter(out);
			AsmPrinter asmPrint = new AsmPrinter(st);
			asmPrint.visit(condRule);
			logger.debug("adding coverage conditional rule ==> " + out.toString() + " --- " + condRule);
		}
		if (eval.getValue())   coveredConRuleT.add(condRule);
		else coveredConRuleF.add(condRule);
		return eval;
	}

	@Override
	public UpdateSet visit(UpdateRule r){
		coveredUpdateRules.add(r);
		if (logger.isDebugEnabled()) {
			StringWriter out = new StringWriter();
			PrintWriter st = new PrintWriter(out);
			AsmPrinter asmPrint = new AsmPrinter(st);
			asmPrint.visit(r);
			logger.debug("adding coverage update rule ==> " + out.toString() + " --- " + r);
		}
		return super.visit(r);
	}
	

	@Override
	public UpdateSet visit(MacroCallRule macroRule) throws NotCompatibleDomainsException {
		// keep track of all the macro evaluated
		coveredMacros.add(macroRule.getCalledMacro());
		logger.debug("adding coverage " + macroRule.getCalledMacro().getName());
		return super.visit(macroRule);
	}

	@Override
	protected RuleEvalWCov createRuleEvaluator(State nextState, Environment environment, ValueAssignment assignment){
		RuleEvalWCov newREC =  new RuleEvalWCov(nextState,environment, assignment);
		return newREC;
	}
	
	@Override
	protected Rule buildNewRule(List<Term> arguments, List<VariableTerm> variables, Rule body, String signature) {
		Rule newRule = super.buildNewRule(arguments, variables, body, signature);
		//  Add, if not already present, the new rule substitution performed during the visit to the dictionary of rule substitutions
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
}
