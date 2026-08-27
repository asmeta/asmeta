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
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionFixedVisitMutator;
import extgt.coverage.fault.mutators.FaultExpressionVisitor;

/**
 * replaces an atomic boolean expression with its negation. AKA variable
 * negation fault.
 * 
 * Chen: Variable Negation Fault (VNF). An occurrence of a condition is replaced
 * by its negation. For example, (not x1 \/ not x2) /\ (x3 /\ x4) is a VNF of
 * (x1 \/ not x2) /\ (x3 /\ x4). Okun: Clause Negation Fault (CNF) - replace a
 * clause c by its negation not c.
 */
public class VariableNegationFault extends
		ExpressionFixedVisitMutator<VariableNegationFault.VNFVisitor> {

	/**
	 * Instantiates a new literal negation fault.
	 */
	public VariableNegationFault() {
		super(new VNFVisitor());
	}

	/** The VNF. */
	static public VariableNegationFault VNF = new VariableNegationFault();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Literal Negation Fault";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		return "VNF";
	}

	public static class VNFVisitor extends FaultExpressionVisitor {
		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
		 * forUnaryExpression (atgt.specification.expression.UnaryExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forUnaryExpression(Expression e) {
			return makeSingletonNot(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forNegExpression
		 * (atgt.specification.expression.NegExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forNegExpression(NegExpression e) {
			return makeSingletonNot(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forIdExpression
		 * (atgt.specification.expression.IdExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forIdExpression(IdExpression e) {
			return makeSingletonNot(e);
		}

		// Logic Expression
		// not boolean
		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
		 * forEqualsExpression (atgt.specification.expression.EqualsExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forEqualsExpression(
				EqualsExpression e) {
			return makeSingletonNot(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
		 * forNotEqualsExpression
		 * (atgt.specification.expression.NotEqualsExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forNotEqualsExpression(
				NotEqualsExpression e) {
			return makeSingletonNot(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
		 * forLessThanExpression
		 * (atgt.specification.expression.LessThanExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forLessThanExpression(
				LessThanExpression e) {
			return makeSingletonNot(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
		 * forLessEqualExpression
		 * (atgt.specification.expression.LessEqualExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forLessEqualExpression(
				LessEqualExpression e) {
			return makeSingletonNot(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
		 * forGreaterThanExpression
		 * (atgt.specification.expression.GreaterThanExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forGreaterThanExpression(
				GreaterThanExpression e) {
			return makeSingletonNot(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
		 * forGreaterEqualExpression
		 * (atgt.specification.expression.GreaterEqualExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forGreaterEqualExpression(
				GreaterEqualExpression e) {
			return makeSingletonNot(e);
		}

		/**
		 * Make singleton not.
		 * 
		 * @param e
		 *            the e
		 * 
		 * @return the list< expression>
		 */
		private static List<Pair<Integer, Expression>> makeSingletonNot(Expression e) {
			List<Pair<Integer, Expression>> le = new Vector<Pair<Integer, Expression>>();
			NotExpression notE = NotExpression.createNotExpression(e);
			le.add(new Pair<Integer, Expression>(1, notE));
			return le;
		}

		/* VNF (A AND B) = VNF(A) and B + A and VNF(B) */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forAndExpression
		 * (atgt.specification.expression.AndExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forAndExpression(AndExpression e) {
			return distribute(e, Operator.AND);
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
			return distribute(e,Operator.OR);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forOrExpression
		 * (atgt.specification.expression.OrExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
			return distribute(e,Operator.XOR);
		}

		
		/*
		 * VNF(not A) = not VNF(A) VNF(not (A and B)) = not VNF( A and B) = [!
		 * (!A and B), ! (A and !B)] a particular case if VNF(A) contains a not
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * atgt.specification.faultcoverage.FaultExpressionVisitor#forNotExpression
		 * (atgt.specification.expression.NotExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forNotExpression(NotExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			Expression e1 = e.getOperand();
			for (Pair<Integer, Expression> r1 : e1.accept(this)) {
				Expression er1 = r1.getSecond();
				Expression noter1 = null;
				int nextNodePos = 0;
				if (er1 instanceof NotExpression) {
					noter1 = ((NotExpression) er1).getOperand();
					nextNodePos = r1.getFirst();
				} else {
					noter1 = NotExpression.createNotExpression(er1);
					nextNodePos = FaultExpressionVisitor.getNextNodePos(r1.getFirst(), false);
				}
				result.add(new Pair<Integer, Expression>(nextNodePos, noter1));				
			}
			return result;
		}

		@Override
		public List<Pair<Integer, Expression>> forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			throw new RuntimeException("not implemented yet");
		}
	}
}
