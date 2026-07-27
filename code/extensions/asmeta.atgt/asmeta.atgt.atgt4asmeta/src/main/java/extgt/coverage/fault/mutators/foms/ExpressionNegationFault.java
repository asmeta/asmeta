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
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionFixedVisitMutator;
import extgt.coverage.fault.mutators.FaultExpressionEmptyVisitor;

/**
 * Expression Negation Fault (ENF). An ENF is a mutant with a subexpression
(except conditions) replaced by its negation. For example, not (x1 \/
not x2) /\ (x3 /\ x4) is an ENF of (x1 \/ not x2) /\ (x3 /\ x4).
 */
public class ExpressionNegationFault  extends ExpressionFixedVisitMutator<ExpressionNegationFault.ENFVisitor> {

	/**
	 * metto public per utilizzarla come plugin.
	 */
	public ExpressionNegationFault() {
		super(new ENFVisitor());
	}

	/** The ENF. */
	static final public ExpressionNegationFault ENF = new ExpressionNegationFault();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Expression Nagation Fault";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		return "ENF";
	}

	static class ENFVisitor extends FaultExpressionEmptyVisitor {
		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forAndExpression(AndExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			result.add(new Pair<Integer, Expression>(1,NotExpression.createNotExpression(e)));
			result.addAll(distribute(e, Operator.AND));
			return result;
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forOrExpression(OrExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			result.add(new Pair<Integer, Expression>(1,NotExpression.createNotExpression(e)));
			result.addAll(distribute(e, Operator.OR));
			return result;
		}
	
		@Override
		public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			result.add(new Pair<Integer, Expression>(1,NotExpression.createNotExpression(e)));
			result.addAll(distribute(e, Operator.XOR));
			return result;
		}
		
		/*
		 * ENF(not A) = not ENF(A), except ENF(A) is notExpression
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forNotExpression(atgt.specification.expression.NotExpression)
		 */
		@Override
		public List<Pair<Integer, Expression>> forNotExpression(NotExpression e) {
			List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
			// e = not e1
			Expression e1 = e.getOperand();
			for (Pair<Integer, Expression> r1 : e1.accept(this)) {
				Expression enf_e1 = r1.getSecond();
				if (enf_e1 instanceof NotExpression) {
					// ENF(e1) = not a => return a (simplify the not)
					result.add(new Pair<Integer, Expression>(1,((NotExpression) enf_e1).getOperand()));
				} else {
					// ENF(e1) = K => return not 
					result.add(new Pair<Integer, Expression>(getNextNodePos(r1.getFirst(), false), NotExpression.createNotExpression(enf_e1)));
				}
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
