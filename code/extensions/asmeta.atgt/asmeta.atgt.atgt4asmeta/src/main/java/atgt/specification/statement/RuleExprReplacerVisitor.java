/*******************************************************************************
 * Copyright (c) 2012 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.specification.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import atgt.specification.expression.IdReplacerVisitor;
import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.LogicalVariable;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;

/**
 * replace an id with another. if it does not change, return the same (==)
 * 
 * @author garganti
 *
 */
public class RuleExprReplacerVisitor implements RuleVisitor<BasicRule> {
	IdExpression tobereplaced;
	IdExpression replacement;
	IdReplacerVisitor idreplacer;

	/**
	 * R[a <- b]
	 * 
	 * @param a
	 *            to be replaced
	 * @param b
	 *            replacement
	 */
	public RuleExprReplacerVisitor(IdExpression a, IdExpression b) {
		tobereplaced = a;
		replacement = b;
		idreplacer = new IdReplacerVisitor(a, b);
	}

	@Override
	public BasicRule forIfThenElse(ConditionalRule ite) {
		Expression newGuard = ite.guard.accept(idreplacer);
		BasicRule newThen = ite.getThenPart().accept(this);
		BasicRule newElse = ite.getElsePart() == null ? null : ite.getElsePart().accept(this);
		if (newGuard == ite.guard && newThen == ite.getThenPart() && newElse == ite.getElsePart())
			return ite;
		// build new if rule
		return new ConditionalRule(newGuard, newThen, newElse);
	}

	@Override
	public BasicRule forSkip(Skip s) {
		return s;
	}

	@Override
	public BasicRule forAssignment(UpdateRule a) {
		// the left
		IdExpression leftId = a.getVar().getIdExpression();
		IdExpression newId = (IdExpression) leftId.accept(idreplacer);
		Location location;
		if (newId != leftId) {
			// System.out.println(" leftId " + leftId + " <- "+ newId + "
			// assigned to " + a.value);
			location = new Variable(newId, a.getVar().getValue());
		} else {
			location = a.getVar();
		}
		// the right value
		Expression rvalue = a.value;
		Expression newRval = rvalue.accept(idreplacer);
		// arguments
		if (a.getArg() == null) {
			if (rvalue == newRval && leftId == newId)
				return a;
			UpdateRule result;
			// build new rule
			if (location instanceof Variable)
				result = new UpdateRule((Variable) location, newRval);
			else
				result = new UpdateRule((LogicalVariable) location, newRval);
			return result;
		} else {
			// function name cannot change;
			assert leftId == newId;
			// arguments
			assert a.getArg().size() == 1;
			Expression arg = a.getArg().get(0);
			Expression newArg = arg.accept(idreplacer);
			if (rvalue == newRval && arg == newArg)
				return a;
			return new UpdateRule((Function) a.getVar(), newRval, Arrays.asList(newArg));
		}
	}

	@Override
	public BasicRule forDoStatement(DoStatement d) {
		boolean changed = false;
		// List<BasicRule> newRules = new
		// ArrayList<BasicRule>(d.statements.size());
		DoStatement doS = new DoStatement();
		for (BasicRule s : d.statements) {
			BasicRule nd = s.accept(this);
			doS.addStatement(nd);
			if (nd != s)
				changed = true;
		}
		if (changed) {
			return doS;
		} else {
			return d;
		}
	}

	@Override
	public BasicRule forMacroCallRule(MacroCallRule mcr) {
		boolean changed = false;
		List<IdExpression> newParams = new ArrayList<>();
		for (IdExpression p:mcr.getParamters()){
			IdExpression newId = (IdExpression) p.accept(idreplacer);
			changed = changed || newId != p;
			newParams.add(newId);
		}
		if (! changed) return mcr;
		return new MacroCallRule(mcr.getRuleDeclaration(), newParams);
	}

	@Override
	public BasicRule forChooseRule(ChooseRule chooseRule) {
		// a choose inside a choose????
		throw new RuntimeException("replace a choose an ID not implemented yet");
		// BasicRule newrule = chooseRule.getDoRule().accept(this);
		// if (newrule == chooseRule.getDoRule()) return chooseRule;
		// // TODO compute the condition
		// Expression newCondition;
		// return new ChooseRule(chooseRule.getVar(), chooseRule.getTerms(),
		// newCondition, newrule);
	}

	@Override
	public BasicRule forCaseStatement(CaseStatement caseStatement) {
		throw new RuntimeException("not implemented yet");
	}
}