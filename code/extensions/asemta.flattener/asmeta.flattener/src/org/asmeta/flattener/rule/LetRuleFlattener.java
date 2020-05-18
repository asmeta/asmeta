package org.asmeta.flattener.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.flattener.term.DomainVisitor;
import org.asmeta.flattener.term.ValuesCombinator;
import org.asmeta.flattener.util.StdlFunction;
import org.eclipse.emf.common.util.EList;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.Rule;

public class LetRuleFlattener extends RuleFlattener {

	public LetRuleFlattener(Asm asm) {
		super(asm);
	}

	@Override
	public Rule visit(LetRule letRule) {
		Rule rule = letRule.getInRule();
		EList<Term> inits = letRule.getInitExpression();
		EList<VariableTerm> vars = letRule.getVariable();
		assert inits.size() == vars.size() : inits + "\n" + vars;
		if (dv == null) {
			dv = new DomainVisitor(asm);
			dv.visitDomains();
		}

		ArrayList<Term[]> result = new ArrayList<Term[]>();
		try {
			ValuesCombinator.combineValuesFromVars(vars, 0, result, new Stack<Term>(), dv.getDomainSet());
		} catch (Exception e) {
			LetRule newLetRule = ruleFact.createLetRule();
			newLetRule.getVariable().addAll(letRule.getVariable());
			newLetRule.getInitExpression().addAll(letRule.getInitExpression());
			newLetRule.setInRule(visit(letRule.getInRule()));
			return newLetRule;
			
			//it should work also in this way
			//letRule.setInRule(visit(letRule.getInRule()));
			//return letRule;
		}

		// Statistics.getInstance().increaseValue(this.getClass().getName());
		Statistics.getInstance().increaseValue(this.getCode());
		StdlFunction stdlFunction = new StdlFunction(asm);
		BlockRule newParRule = ruleFact.createBlockRule();
		EList<Rule> newRules = newParRule.getRules();
		// ReplaceValueInTerm replaceValueInTerm = null;
		// ReplaceValue rv = null;
		Rule ruleWithValue = null;
		rename = true;
		for (Term[] values : result) {
			Map<VariableTerm, Term> map = new HashMap<>();
			for (int i = 0; i < values.length; i++) {
				map.put(vars.get(i), values[i]);
				// System.err.println(vars.get(i) + " -> " + values[i]);
			}

			ConditionalRule newCondRule = ruleFact.createConditionalRule();

			// build guard
			List<Term> eqs = new ArrayList<>();
			assert values.length == inits.size() : Arrays.toString(values) + "\n" + inits;
			trv.addMap(map);
			for (int i = 0; i < values.length; i++) {
				FunctionTerm eq = stdlFunction.eq(trv.visit(inits.get(i)), values[i]);
				eqs.add(eq);
			}
			newCondRule.setGuard(stdlFunction.and(eqs));
			ruleWithValue = visit(rule);
			trv.removeMap(map);
			newCondRule.setThenRule(ruleWithValue);
			if (result.size() == 1) {
				return newCondRule;
			}
			newRules.add(newCondRule);
		}
		rename = false;
		return newParRule;
	}

	@Override
	public String getCode() {
		return "LR";
	}
}
