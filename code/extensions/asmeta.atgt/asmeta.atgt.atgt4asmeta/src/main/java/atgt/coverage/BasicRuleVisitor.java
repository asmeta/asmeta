/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.coverage;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import atgt.specification.expression.IdReplacerVisitor;
import atgt.specification.statement.BasicRule;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.RuleExprReplacerVisitor;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.type.BoolType;

/**
 * Basic Rule Coverage. Esegue una scansione della specifica e genera una
 * collezione di casi di test.
 * 
 * SPEC -> Coverage funziona SPEC -> RuleBasedCoverageBuilder su Rule ed
 * eXpressions -> coverage
 * 
 * Basic RULE COVERAGE for every if (Guard) tc = {guard , not guard }
 * 
 * usa il decision visitor (composizione) di fatto estende il decision visitor
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class BasicRuleVisitor extends RuleBasedCoverageBuilder {

	/**
	 * costruisce un nuovo basic rule visitor messo public per permettere la
	 * creazione da parte del plugin di eclipse.
	 */
	public BasicRuleVisitor() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.RuleBasedCoverageBuilder#getName()
	 */
	@Override
	public String getName() {
		return "Basic Rule Coverage";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.RuleBasedCoverageBuilder#getAbbrName()
	 */
	@Override
	public String getAbbrName() {
		return "BR";
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata la lista vuota. Non ci sono <I>Test
	 * Predicate</I> da generare.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forSkip(Skip s) {
		return new Vector<NamedTerm>();
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata la lista vuota. Non ci sono <I>Test
	 * Predicate</I> da generare.
	 * 
	 * @param a
	 *            the a
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forAssignment(UpdateRule a) {
		return new Vector<NamedTerm>();
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata una lista contenente due <I>Test
	 * Predicate</I>
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forIfThenElse(ConditionalRule ite) {
		// get the decision for this
		// do not ignore the else
		return distributeOverConditional(ite, false);
	}

	@Override
	public List<NamedTerm> forChooseRule(ChooseRule chooseRule) {
		// for every internal tp build an external TP
		Integer internalTps = null;
		List<NamedTerm> result = new ArrayList<NamedTerm>();
		IdExpression var = chooseRule.getVar();
		for (Expression value : chooseRule.getTerms()) {
			// condition assignment variable = value
			// build new guard
			Expression tpguard = null;
			if (chooseRule.getCondition() != BoolType.TRUE_CONST) {
				tpguard = chooseRule.getCondition().accept(
						new IdReplacerVisitor(var, (IdExpression) value));
			}
			// build a new rule
			// variable <- value
			RuleExprReplacerVisitor replacer = new RuleExprReplacerVisitor(var,
					(IdExpression) value);
			BasicRule newrule = chooseRule.getDoRule().accept(replacer);
			// get the tps
			List<NamedTerm> internalList = newrule.accept(this);
			// the number of internal TPS is always the same
			assert internalTps == null || internalTps == internalList.size();
			// if there is no test predicate
			if (internalList.size() == 0) {
				if (tpguard == null)
					continue;
				String _name = "G" + var + "<-" + value;
				if (internalTps == null) {
					// add the first one
					result.add(new NamedTerm(_name, tpguard));
				} else {
					// put in or
					BinaryExpression newTp = BinaryExpression.mkBinExpr(
							tpguard, Operator.OR, result.get(0).getCondition());
					result.set(0, new NamedTerm(
							result.get(0).getName() + _name, newTp));
				}
			} else {
				for (int i = 0; i < internalList.size(); i++) {
					Expression newTp;
					String _name = "";
					// is there a guard???
					if (tpguard == null) {
						newTp = internalList.get(i).getCondition();
					} else {
						// guard AND tp_i
						newTp = BinaryExpression.mkBinExpr(tpguard,
								Operator.AND, internalList.get(i)
										.getCondition());
					}
					// to unite with the other
					if (internalTps == null) {
						result.add(new NamedTerm(_name, newTp));
					} else {
						newTp = BinaryExpression.mkBinExpr(newTp, Operator.OR,
								result.get(i).getCondition());
						result.set(i, new NamedTerm(result.get(i).getName(),
								newTp));
					}
				}
			}
			internalTps = internalList.size();
		}
		return result;
	}
}
