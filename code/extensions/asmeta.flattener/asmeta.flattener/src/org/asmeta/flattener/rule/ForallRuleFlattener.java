package org.asmeta.flattener.rule;

import java.util.ArrayList;
import java.util.Stack;

import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.flattener.term.DomainVisitor;
import org.asmeta.flattener.term.ReplaceValueInTerm;
import org.asmeta.flattener.term.ValuesCombinator;
import org.eclipse.emf.common.util.EList;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.Rule;

public class ForallRuleFlattener extends RuleFlattener {

	public ForallRuleFlattener(Asm asm) {
		super(asm);
	}

	@Override
	public Rule visit(ForallRule forallRule) {
		Rule rule = forallRule.getDoRule();
		Term guard = forallRule.getGuard();
		EList<VariableTerm> vars = forallRule.getVariable();

		if (dv == null) {
			dv = new DomainVisitor(asm);
			dv.visitDomains();
		}

		// "Term[]" array includes one of all combinations, such as "001"
		// "ArrayList" includes all combination of the values, such as "000,001,..."
		ArrayList<Term[]> result = new ArrayList<Term[]>();
		try {
			ValuesCombinator.combineValuesFromVars(vars, 0, result, new Stack<Term>(), dv.getDomainSet());
		} catch (Exception e) {
			e.printStackTrace();
			assert false : "All the domains in a forall should be finite. Therefore, it should be possible to combine them.";
			return forallRule;
		}

		// Statistics.getInstance().increaseValue(this.getClass().getName());
		Statistics.getInstance().increaseValue(this.getCode());
		BlockRule newParRule = ruleFact.createBlockRule();
		EList<Rule> newRules = newParRule.getRules();
		ReplaceValueInTerm replaceValueInTerm = null;
		ReplaceValue rv = null;
		Rule ruleWithValue = null;
		rename = true;
		for (Term[] values : result) {
			replaceValueInTerm = new ReplaceValueInTerm(values, vars);
			rv = new ReplaceValue(replaceValueInTerm);
			ConditionalRule conditionalRule = ruleFact.createConditionalRule();
			Term newGuard = replaceValueInTerm.visit(guard);
			// we have to recursively visit the new rule as it could contain
			// another forall rule
			ruleWithValue = rv.visit(rule);
			Rule visitedRule = visit(ruleWithValue);
			if (!isTrue(newGuard)) {
				conditionalRule.setGuard(newGuard);
				conditionalRule.setThenRule(visitedRule);
				newRules.add(conditionalRule);
			} else {
				newRules.add(visitedRule);
			}
		}
		rename = false;
		// LIMITING CASE: the combination of the values is only one
		if (result.size() == 1) {
			return newRules.get(0);
		}
		return newParRule;
	}

	private boolean isTrue(Term term) {
		if (term instanceof BooleanTerm) {
			return ((BooleanTerm) term).getSymbol().equals("true");
		}
		return false;
	}

	@Override
	public String getCode() {
		return "FR";
	}
}
