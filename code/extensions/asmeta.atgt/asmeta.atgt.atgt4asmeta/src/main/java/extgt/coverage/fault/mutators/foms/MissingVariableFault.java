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
package extgt.coverage.fault.mutators.foms;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionFixedVisitMutator;
import extgt.coverage.fault.mutators.FaultExpressionEmptyVisitor;

/**
 * chen: Missing Variable Fault (MVF). An occurrence of a condition is omitted
 * in the expression. For example, (x1 \/not x2)/\(x3) is an MV F of (x1 \/not
 * x2)/\(x3 /\ x4). Note that a condition of MV F may be connected by /\ or \/.
 */
public class MissingVariableFault extends
ExpressionFixedVisitMutator<MissingVariableFault.MVFVisitor> {

	/**
	 * Instantiates a new missing literal fault.
	 */
	public MissingVariableFault() {
		super(new MVFVisitor());
	}

	/** The MVF. */
	static final public MissingVariableFault MVF = new MissingVariableFault();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Missing Literal Fault";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		return "MVF";
	}

	static class MVFVisitor extends FaultExpressionEmptyVisitor {

		/*
		 * MVF (A and B) = MVF(A) and B + A and MVF(B) + if MVF(A) is empty,
		 * add B and the same for A
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forAndExpression
		 * (atgt.specification.expression.AndExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forAndExpression(AndExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			// distribute over the subexpressions
			result.addAll(distribute(e, Operator.AND));
			// ID1 AND e2 ... or e1 AND ID2
			return checkOneOperator(e, result);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forOrExpression
		 * (atgt.specification.expression.OrExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forOrExpression(OrExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			// distribute over the subexpressions
			result.addAll(distribute(e, Operator.OR));
			// get this one
			checkOneOperator(e, result);
			return result;
		}

		@Override
		public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			// distribute over the subexpressions
			result.addAll(distribute(e, Operator.XOR));
			// get this one
			checkOneOperator(e, result);
			return result;
		}

		private List<Pair<Integer, Expression>> checkOneOperator(BinaryExpression e, List<Pair<Integer, Expression>> result) {
			Expression e1 = e.getFirstOperand();
			Expression e2 = e.getSecondOperand();
			List<Pair<Integer, Expression>> le1 = e1.accept(this);
			if(le1.isEmpty()) {
				result.add(new Pair<Integer, Expression>(1, e2));
			} 
			List<Pair<Integer, Expression>> le2 = e2.accept(this);
			if(le2.isEmpty()) {
				result.add(new Pair<Integer, Expression>(1, e1));
			}			
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forNotExpression
		 * (atgt.specification.expression.NotExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forNotExpression(NotExpression e) {
			// it can be not ID
			Expression e1 = e.getOperand();			
			//
			List<Pair<Integer, Expression>> le = e1.accept(this);
			if(!le.isEmpty()) {
				return distribute(e, Operator.NOT);
			} else {
				return Collections.EMPTY_LIST;
			}
		}

		@Override
		public List<Pair<Integer, Expression>> forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			throw new RuntimeException("not implemented yet");
		}
	}
}
