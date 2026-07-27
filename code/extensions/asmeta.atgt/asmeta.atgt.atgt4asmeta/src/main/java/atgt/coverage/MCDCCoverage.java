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

import atgt.specification.statement.BasicRule;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import extgt.coverage.mcdc.MaskMCDCTPBuilder;
import extgt.coverage.mcdc.ShallowExpressionNegator;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;

/**
 * The Class MCDCCoverage for masking MCDC coverage
 */
public class MCDCCoverage extends RuleBasedCoverageBuilder {

	/**
	 * Instantiates a new mCDC coverage.
	 */
	private MCDCCoverage() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.BasicRuleVisitor#getName()
	 */
	@Override
	public String getName() {
		return "MCDC Coverage";
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata una lista contenente due <I>Test Predicate</I>
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the list<atgt.coverage. named expression>
	 */
	@Override
	public java.util.List<NamedTerm> forIfThenElse(ConditionalRule ite) {
		List<NamedTerm> result = new ArrayList<NamedTerm>();
		Expression guard = ite.getGuard();
		// get all the MCDC from the guard
		for (NamedTerm nt : MaskMCDCTPBuilder.getMCDCVisitor().analyze(guard)){
			result.add(nt);
		}
		//
		// for the true part (MCDC of the inner parts)
		BasicRule thenPart = ite.getThenPart();
		List<NamedTerm> then_list = thenPart.accept(this);
		// add guard
		for (NamedTerm tc : then_list) {
			// add not guard and tc
			NamedTerm tc_i = new NamedTerm("T_" + tc.getName(),
					new AndExpression(guard, tc.getCondition()));
			result.add(tc_i);
		}
		//    	
		// for the else part the not condition is covered by MCDC of the guard
		BasicRule elsePart = ite.getElsePart();
		if (elsePart != null) {
			// get the MCDC of the else part
			List<NamedTerm> else_list = elsePart.accept(this);
			// add not
			Expression notGuard = ite.getGuard().accept(ShallowExpressionNegator.negate);
			for (NamedTerm tc : else_list) {
				// add not guard and tc
				NamedTerm tc_i = new NamedTerm("F_" + tc.getName(),
						new AndExpression(notGuard, tc.getCondition()));
				result.add(tc_i);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.BasicRuleVisitor#getAbbrName()
	 */
	@Override
	public String getAbbrName() {
		return "MCDC";
	}

	/**
	 * Gets the coverage.
	 * 
	 * @return the coverage
	 */
	static final public MCDCCoverage getCoverage() {
		return new MCDCCoverage();
	}

	@Override
	public List<NamedTerm> forSkip(Skip s) {
		return new Vector<NamedTerm>();
	}

	@Override
	public List<NamedTerm> forAssignment(UpdateRule a) {
		return new Vector<NamedTerm>();
	}

	@Override
	public List<NamedTerm> forChooseRule(ChooseRule chooseRule) {
		return distributechhose(chooseRule);
	}

}
