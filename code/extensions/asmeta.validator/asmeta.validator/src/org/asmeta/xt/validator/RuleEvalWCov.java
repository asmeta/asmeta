package org.asmeta.xt.validator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.NotCompatibleDomainsException;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.ValueAssignment;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.wrapper.RuleFactory;

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
	// FIXME: l'uso di static is due to the fact that several RuleEvaluator
	// are created for the same run;
	static Collection<MacroDeclaration> coveredMacros;
	// covered guards in conditional rules
	static Collection<ConditionalRule> coveredConRuleT;
	static Collection<ConditionalRule> coveredConRuleF;
	// covered updaterules
	static Collection<UpdateRule> coveredUpdateRules;
	

	// this must be called only once for run
	public RuleEvalWCov(State state, Environment environment,
			RuleFactory factory) {
		super(state, environment, factory);
		// TODO check that coverage is not lost - since the rule evaluator is rebuilt e new one
		// trying to build the new covered macro only if null (the first time)
		if (coveredMacros == null) coveredMacros = new HashSet<>();
		if (coveredConRuleT == null) coveredConRuleT = new HashSet<>();
		if (coveredConRuleF == null) coveredConRuleF = new HashSet<>();
		if (coveredUpdateRules == null) coveredUpdateRules = new HashSet<>();
	}

	// this is called when a new state requires a new evaluator
	private RuleEvalWCov(State state, Environment environment,
			ValueAssignment assignment) {
		super(state, environment, assignment);
	}

	@Override
	protected BooleanValue evalGuard(ConditionalRule condRule) {
		BooleanValue eval = super.evalGuard(condRule);
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
			logger.debug("adding coverage update rule ==> " + out.toString());
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
}
