package org.asmeta.xt.validator;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.NotCompatibleDomainsException;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.ValueAssignment;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

/** Questa classe valuta le regole
 * pero' tiene traccia delle macro valutate
 * 
 * NO longer used !!!
 * @author AG
 *
 */
public class RuleEvalWCov extends RuleEvaluator {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RuleEvalWCov.class);

	private Collection<MacroDeclaration> coveredMacros;

	public RuleEvalWCov(State state, Environment environment,
			RuleFactory factory) {
		super(state, environment, factory);
		coveredMacros = new HashSet<MacroDeclaration>();
	}
	
	public RuleEvalWCov(State state, Environment environment,
			ValueAssignment assignment) {
		super(state, environment, assignment);
	}
 
	
	@Override
	public UpdateSet visit(MacroCallRule macroRule) throws NotCompatibleDomainsException {
		// keep track of all the macro evaluated
		coveredMacros.add(macroRule.getCalledMacro());
		logger.debug("addding coverage " + macroRule.getCalledMacro().getName());
		return super.visit(macroRule);
	}

	/**public void printCoveredMacro(PrintStream ps){
		for (MacroDeclaration md: coveredMacros){
			ps.println(md.getName());
		}
	}*/
	protected RuleEvaluator createNewEvaluator(State nextState){
		RuleEvalWCov newREC =  new RuleEvalWCov(nextState,termEval.getEnv(), termEval.getAssignment());
		newREC.coveredMacros = this.coveredMacros;
		return newREC;
	}	
}
