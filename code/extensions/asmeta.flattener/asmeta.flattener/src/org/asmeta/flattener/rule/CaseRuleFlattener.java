package org.asmeta.flattener.rule;

import java.util.ArrayList;

import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.simulator.util.StdlFunction;
import org.eclipse.emf.common.util.EList;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;

public class CaseRuleFlattener extends RuleFlattener {
	private StdlFunction stdlFunction;
	public static boolean DO_STATS = false;

	public CaseRuleFlattener(Asm asm) {
		super(asm);
		stdlFunction = new StdlFunction(asm);
	}

	@Override
	public Rule visit(CaseRule caseRule) {
		//Statistics.getInstance().increaseValue(this.getClass().getName());
		Statistics.getInstance().increaseValue(this.getCode());

		BlockRule par = ruleFact.createBlockRule();
		EList<Rule> parRules = par.getRules();
		EList<Rule> rules = parRules;

		Term term = caseRule.getTerm();
		EList<Term> values = caseRule.getCaseTerm();

		ArrayList<Rule> rulesCase = new ArrayList<>();
		for (Rule r : caseRule.getCaseBranches()) {
			rulesCase.add(r);
		}

		ArrayList<Term> a = null;
		Rule otherwise = caseRule.getOtherwiseBranch();
		if (otherwise != null) {
			a = new ArrayList<>();
		}
	
		for (int i = 0; i < rulesCase.size(); ++i) {
			ConditionalRule conditionalRule = ruleFact.createConditionalRule();
			Term value = values.get(i);
			Rule visitedRule = visit(rulesCase.get(i));
			// value == term, the result is the "if" guard
			Term eq = stdlFunction.eq(value, term);
			conditionalRule.setGuard(eq);
			conditionalRule.setThenRule(visitedRule);
			rules.add(conditionalRule);

			// add(and) to otherwiseGuard the negated guard of this case.
			if (otherwise != null) {
				a.add(stdlFunction.not(eq));
			}
		}
		// se non e' uguale ad ogni di prima in and tra loro allora fai l'altro
		if (otherwise != null) {
			ConditionalRule conditionalRule = ruleFact.createConditionalRule();
			conditionalRule.setGuard(stdlFunction.and(a));
			conditionalRule.setThenRule(otherwise);
			rules.add(conditionalRule);
		}
		if (parRules.size() > 1) {
			return par;
		} else {
			assert parRules.size() == 1;
			return parRules.get(0);
		}

	}

	@Override
	public String getCode() {
		return "CaR";
	}
}
