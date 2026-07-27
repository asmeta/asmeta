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
 * an entire subexpression is removed from the expression. It generalizes the 
 * missing variable fault, by extending not only to 
 */
public class MissingSubExpressionFault extends
ExpressionFixedVisitMutator<MissingSubExpressionFault.MSEVisitor> {

	/**
	 * Instantiates a new missing literal fault.
	 */
	public MissingSubExpressionFault() {
		super(new MSEVisitor());
	}

	/** The MVF. */
	static final public MissingSubExpressionFault MSF = new MissingSubExpressionFault();

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

	static class MSEVisitor extends FaultExpressionEmptyVisitor {

		/*
		 * MSE (A and B) = MSE(A) and B + A and MSE(B) + B  + A
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
			return forBinaryExpression(e, Operator.AND);
		}

		private List<Pair<Integer, Expression>> forBinaryExpression(BinaryExpression e, Operator operator) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			// distribute over the subexpressions
			result.addAll(distribute(e, operator));
			//
			result.add(new Pair<Integer, Expression>(0, e.getFirstOperand()));
			result.add(new Pair<Integer, Expression>(0, e.getSecondOperand()));
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
			return forBinaryExpression(e, Operator.OR);
		}

		@Override
		public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
			return forBinaryExpression(e, Operator.XOR);
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
