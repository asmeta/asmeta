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
 * only one level, to be extended
 */
public class AssociativeShiftFault extends ExpressionFixedVisitMutator<AssociativeShiftFault.ASFvisitor> {

	/**
	 * messo public per permettere la creazione mediante reflection del plugin
	 * di eclipse.
	 */
	public AssociativeShiftFault() {
		super(asfVisitor);
	}

	static private ASFvisitor asfVisitor = new ASFvisitor();
	
	/** The ASF. */
	static final public AssociativeShiftFault ASF = new AssociativeShiftFault();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Assocative Shift Fault";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		return "ASF";
	}

	static class ASFvisitor extends FaultExpressionEmptyVisitor {

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
			Expression e1 = e.getFirstOperand();
			Expression e2 = e.getSecondOperand();
			if (e1 instanceof OrExpression) {
				// e1.1 or e1.2 
				// the new top operator (switch)
				switchOperators(result, e1, e2, true, true, Operator.OR);
			}
			if (e2 instanceof OrExpression) {
				// in this case I have e2 = a or b
				// e = e1 and e2
				// -> (e1 and a) or b
				// the new top operator (switch)
				switchOperators(result, e1, e2, true, false, Operator.OR);
			}
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
			return forDisjunctiveExpression(e, Operator.OR);
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
			// switch AND with XOR 
			return forDisjunctiveExpression(e, Operator.XOR);
		}

		
		private List<Pair<Integer, Expression>> forDisjunctiveExpression(
				BinaryExpression e, Operator orOp) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			Expression e1 = e.getFirstOperand();
			Expression e2 = e.getSecondOperand();
			if (e1 instanceof AndExpression) {
				// in this case I have e1 = a and b
				// e = e1 or e2
				// -> a and (b or e2)
				// the new top operator (switch)
				switchOperators(result, e1, e2, false, true, orOp);
			}
			if (e2 instanceof AndExpression) {
				// in this case I have e2 = a and b
				// e = e1 or e2
				// -> (e1 or a) and b
				// the new top operator (switch)
				switchOperators(result, e1, e2, false, false, orOp);
			}
			// go inside
			result.addAll(distribute(e, orOp));
			return result;
		}
		
		// four cases
		// 1. (e1.1 OR e1.2) AND e2 -> e1.1 OR (e1.2 AND e2)  | topAND e splitFirst
		// 2. e1  AND (e2.1 OR e2.2) -> (e1 AND e2.1) OR e2.2  | topAND e not splitFirst
		// 3. (e1.1 AND e1.2) OR e2 -> e1.1 AND (e1.2 OR e2)  | not topAND e splitFirst
		// 4. e1  OR (e2.1 AND e2.2) -> (e1 OR e2.1) AND e2.2
		//
		//
		private void switchOperators(
				final List<Pair<Integer, Expression>> result,
				final Expression e1, final Expression e2, boolean topAND,
				boolean divideFirst, Operator orOp) {
			Operator top = topAND ? orOp : Operator.AND;
			// the new indise operator
			Operator bottom = topAND ? Operator.AND : orOp;
			Expression finalExpr;
			if (divideFirst){
				Expression e11 = ((BinaryExpression)e1).getFirstOperand();
				Expression e12 = ((BinaryExpression)e1).getSecondOperand();
				Expression e12_opDeep_e2 = BinaryExpression.mkBinExpr(e12, bottom, e2);
				finalExpr = BinaryExpression.mkBinExpr(e11,top, e12_opDeep_e2);
			} else{
				Expression e21 = ((BinaryExpression)e2).getFirstOperand();
				Expression e22 = ((BinaryExpression)e2).getSecondOperand();
				Expression e1_opDeep_e21 = BinaryExpression.mkBinExpr(e1, bottom, e21);
				finalExpr = BinaryExpression.mkBinExpr(e1_opDeep_e21,top,e22);				
			}
			result.add(new Pair<Integer, Expression>(1, finalExpr));
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
