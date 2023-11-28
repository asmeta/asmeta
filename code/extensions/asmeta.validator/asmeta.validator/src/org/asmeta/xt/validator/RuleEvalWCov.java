package org.asmeta.xt.validator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
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

	// this must be called only once for run
	public RuleEvalWCov(State state, Environment environment,
			RuleFactory factory) {
		super(state, environment, factory);
		// TODO check that coverage is not lost - since the rule evaluator is rebuilt e new one
		coveredMacros = new HashSet<>();
	}
	
	// this is called when a new state requires a new wvaluator
	private RuleEvalWCov(State state, Environment environment,
			ValueAssignment assignment) {
		super(state, environment, assignment);
	}
	
	@Override
	protected BooleanValue evalGuard(ConditionalRule condRule) {
		BooleanValue eval = super.evalGuard(condRule);
		//TODO store info about the coverage
		return eval;
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
