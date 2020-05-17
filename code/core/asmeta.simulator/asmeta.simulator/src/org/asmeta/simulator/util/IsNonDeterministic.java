package org.asmeta.simulator.util;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

/**
 * PA: 2/11/2010
 * It visits a rule in depth and checks if it is non deterministic
 * (it contains a choose rule).
 *
 */
public class IsNonDeterministic extends ReflectiveVisitor<Boolean> {

	public boolean visit(BlockRule blockRule) {
		for (Rule rule: blockRule.getRules()) {
			if(visit(rule)) {
				return true;
			}
		}
		return false;
	}

	public boolean visit(ForallRule forallRule) {
		if(visit(forallRule.getDoRule())) {
			return true;
		}
		return false;
	}

	public boolean visit(ChooseRule chooseRule) {
		return true;
	}

	public boolean visit(MacroCallRule macroCallRule) {
		if(visit(macroCallRule.getCalledMacro().getRuleBody())) {
			return true;
		}
		return false;
	}

	public boolean visit(ConditionalRule conditionalRule) throws Exception {
		if(visit(conditionalRule.getThenRule())) {
			return true;
		}
		Rule elseRule = conditionalRule.getElseRule();
		if(elseRule != null) {
			if(visit(elseRule)) {
				return true;
			}
		}
		return false;
	}

	public boolean visit(CaseRule caseRule) {
		for(Rule rule: caseRule.getCaseBranches()) {
			if(visit(rule)) {
				return true;
			}
		}
		Rule otherBranch = caseRule.getOtherwiseBranch();
		if(otherBranch != null) {
			if(visit(otherBranch)) {
				return true;
			}
		}
		return false;
	}

	public boolean visit(LetRule letRule) {
		return visit(letRule.getInRule());
	}

	public boolean visit(UpdateRule updateRule) {
		return false;
	}

	public boolean visit(SeqRule seqRule) {
		for(Rule rule: seqRule.getRules()) {
			if(visit(rule)) {
				return true;
			}
		}
		return false;
	}

	public boolean visit(SkipRule skipRule) {
		return false;
	}

	public boolean visit(ExtendRule extendRule) {
		return visit(extendRule.getDoRule());
	}
}
