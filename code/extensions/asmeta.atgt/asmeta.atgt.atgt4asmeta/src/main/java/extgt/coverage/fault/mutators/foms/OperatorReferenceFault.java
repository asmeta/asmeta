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
 * chen: Operator Reference Fault (ORF). An occurrence of a logical connective
 * \/ replaced by /\ or vice versa. For example, (x1 \/ not x2) \/ (x3 /\ x4) is
 * an ORF of (x1 \/ not x2) /\ (x3 /\ x4). OR is substituted with AND and
 * viceversa
 */
public class OperatorReferenceFault extends
ExpressionFixedVisitMutator<OperatorReferenceFault.ORFVisitor> {

	/**
	 * Instantiates a new operator reference fault.
	 */
	public OperatorReferenceFault() {
		super(new ORFVisitor());
	}

	/** The ORF. */
	static final public OperatorReferenceFault ORF = new OperatorReferenceFault();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Operator Reference Fault";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		return "ORF";
	}

	static class ORFVisitor extends FaultExpressionEmptyVisitor {
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
			addSwitch(e, result,Operator.OR);
			// add all the others
			result.addAll(distribute(e, Operator.AND));
			return result;

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
			addSwitch(e, result,Operator.AND);
			result.addAll(distribute(e,Operator.OR));
			return result;
		}

		@Override
		public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			addSwitch(e, result,Operator.AND);
			result.addAll(distribute(e,Operator.XOR));
			return result;
		}

		private void addSwitch(BinaryExpression e,
				List<Pair<Integer, Expression>> result, Operator op) {
			Expression e1 = e.getFirstOperand();
			Expression e2 = e.getSecondOperand();
			result.add(new Pair<Integer, Expression>(1,BinaryExpression.mkBinExpr(e1, op, e2)));
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
			return distribute(e,Operator.NOT);
		}

		@Override
		public List<Pair<Integer, Expression>> forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			throw new RuntimeException("not implemented yet");
		}
	}
}
