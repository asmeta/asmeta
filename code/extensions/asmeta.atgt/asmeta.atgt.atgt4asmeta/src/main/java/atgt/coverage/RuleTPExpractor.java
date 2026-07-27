package atgt.coverage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import atgt.specification.statement.BasicRule;
import atgt.specification.statement.CaseStatement;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.RuleDeclarationVisitor;
import atgt.specification.statement.RuleExprReplacerVisitor;
import atgt.specification.statement.RuleVisitor;
import extgt.coverage.mcdc.ShallowExpressionNegator;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;

abstract public class RuleTPExpractor implements RuleVisitor<List<NamedTerm>>,
RuleDeclarationVisitor<List<NamedTerm>>{
	
	
	protected List<NamedTerm> distributechhose(ChooseRule chooseRule) {
		List<NamedTerm> result = new ArrayList<NamedTerm>();
		// covers the internal rule for all the values to be chosen
		for(Expression value : chooseRule.getTerms()){
			// build a new rule
			RuleExprReplacerVisitor replacer = new RuleExprReplacerVisitor(chooseRule.getVar(), (IdExpression) value);
			BasicRule newrule = chooseRule.getDoRule().accept(replacer);
			result.addAll(newrule.accept(this));
		}
		return result;
	}

	/** distribute the visitor over the two conditions
	 * 
	 * @param ite
	 * @param ignoreElse: ignore the else (also if else is not null). if not ignore, consider also if it si null
	 * @param visitor
	 * @return
	 */
	protected List<NamedTerm> distributeOverConditional(ConditionalRule ite, boolean ignoreElse) {
		List<NamedTerm> result = new Vector<NamedTerm>();

		// get the guard
		Expression guard = ite.getGuard();
		List<NamedTerm> then_list = ite.getThenPart().accept(this);
		if (!then_list.isEmpty()) {
			for (NamedTerm tc : then_list) {
				NamedTerm tc_i = new NamedTerm("T" + tc.getName(),
						new AndExpression(guard, tc.getCondition()));
				result.add(tc_i);
			}
		} else {
			// if the then part does not contain extra rules, just add the guard
			result.add(new NamedTerm("T", guard));
		}
		// for else part: get the decisions in the else part
		BasicRule elseR = ite.getElsePart();
		if (!ignoreElse) {
			// else 
			Expression notGuard = guard.accept(ShallowExpressionNegator.negate);
			List<NamedTerm> else_list = null;
			// if contains else
			if (elseR != null) {
				else_list = elseR.accept(this);
				for (NamedTerm tc : else_list) {
					// add not guard and tc
					NamedTerm tc_i = new NamedTerm("F" + tc.getName(),
						new AndExpression(notGuard, tc.getCondition()));
					result.add(tc_i);
				}
				
			} 
			// if else list was empty because 
			// if else was something but gave an empty list 
			if (else_list == null || else_list.isEmpty()) {
				result.add(new NamedTerm("F", notGuard));
			}
		}
		return result;
	}

	@Override
	public List<NamedTerm> forCaseStatement(CaseStatement caseStatement) {
		Iterator<Entry<IdExpression, BasicRule>> cases = caseStatement.allCases();
		//
		List<NamedTerm> result = new ArrayList<NamedTerm>();
		// default (not g1 and not g2 ...)
		Expression defaultCase = null;
		while(cases.hasNext()) {
			Entry<IdExpression, BasicRule> currCase = cases.next();
			// build the conditional
			BinaryExpression guard = BinaryExpression.mkBinExpr(caseStatement.getSelector(), Operator.EQ, currCase.getKey());
			//
			ConditionalRule cr = new ConditionalRule(guard, currCase.getValue());
			//
			//List<NamedTerm> resultForCase = cr.accept(this);
			//result.addAll(resultForCase);
			for(NamedTerm nt: cr.accept(this)) {
				result.add(new NamedTerm("_" + currCase.getKey().toString() + "_" + nt.getName(), nt.getCondition()));
			}
			// build the default
			NotExpression notGuard = NotExpression.createNotExpression(guard);
			if (defaultCase == null) {
				defaultCase = notGuard;
			} else {
				defaultCase = new AndExpression(defaultCase, notGuard);
			}
		}
		//
		if (caseStatement.getDefaultRule() != null){
			ConditionalRule cr = new ConditionalRule(defaultCase, caseStatement.getDefaultRule());
			//
			//result.addAll(cr.accept(this));
			for(NamedTerm nt: cr.accept(this)) {
				result.add(new NamedTerm("_default_" + nt.getName(), nt.getCondition()));
			}
		}
		//
		return result;
	}
}