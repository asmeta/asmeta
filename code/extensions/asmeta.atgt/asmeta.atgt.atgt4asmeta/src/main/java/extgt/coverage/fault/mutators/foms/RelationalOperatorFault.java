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
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionFixedVisitMutator;
import extgt.coverage.fault.mutators.FaultExpressionEmptyVisitor;



/**
 * The Class RelationalOperatorFault.
 * replaces a relational operator with another one
 */
public class RelationalOperatorFault extends ExpressionFixedVisitMutator<RelationalOperatorFault.ROFVisitor>{

	/**
	 * Instantiates a new relational operator fault.
	 */
	private RelationalOperatorFault() {
		super(new ROFVisitor());
	}

	/** The ROF. */
	static public RelationalOperatorFault ROF = new RelationalOperatorFault();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Relational Operator Fault";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		return "ROF";
	}

    static class ROFVisitor extends FaultExpressionEmptyVisitor{ 
	
	// Logic Expression
	// not boolean
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forEqualsExpression(EqualsExpression e) {
		return allOtherOperator(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNotEqualsExpression(NotEqualsExpression e) {
		return allOtherOperator(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forLessThanExpression(atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forLessThanExpression(LessThanExpression e) {
		return allOtherOperator(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forLessEqualExpression(atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forLessEqualExpression(LessEqualExpression e) {
		return allOtherOperator(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forGreaterThanExpression(atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forGreaterThanExpression(GreaterThanExpression e) {
		return allOtherOperator(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forGreaterEqualExpression(atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forGreaterEqualExpression(GreaterEqualExpression e) {
		return allOtherOperator(e);
	}

	/**
	 * All the other operators different from the operator of e
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the list< expression>
	 */
	private static List<Pair<Integer, Expression>> allOtherOperator(BinaryExpression e) {

		Expression e1 = e.getFirstOperand();
		Expression e2 = e.getSecondOperand();
		// temporary: is Math,
		boolean isMath = !((e instanceof EqualsExpression) || (e instanceof NotEqualsExpression));
		List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
		if (!(e instanceof EqualsExpression))
			result.add(new Pair<Integer, Expression>(0,new EqualsExpression(e1, e2)));
		if (!(e instanceof NotEqualsExpression))
			result.add(new Pair<Integer, Expression>(0,new NotEqualsExpression(e1, e2)));
		if (!(e instanceof LessThanExpression) && isMath)
			result.add(new Pair<Integer, Expression>(0,new LessThanExpression(e1, e2)));
		if (!(e instanceof LessEqualExpression) && isMath)
			result.add(new Pair<Integer, Expression>(0,new LessEqualExpression(e1, e2)));
		if (!(e instanceof GreaterThanExpression) && isMath)
			result.add(new Pair<Integer, Expression>(0,new GreaterThanExpression(e1, e2)));
		if (!(e instanceof GreaterEqualExpression) && isMath)
			result.add(new Pair<Integer, Expression>(0,new GreaterEqualExpression(e1, e2)));
		return result;
	}

	/* */
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forAndExpression(AndExpression e) {
		return distribute(e, Operator.AND);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forOrExpression(OrExpression e) {
		return distribute(e,Operator.OR);
	}

	@Override
	public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
		return distribute(e,Operator.XOR);
	}

	
	// ORF(not A) = not ORF(A)
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forNotExpression(atgt.specification.expression.NotExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNotExpression(NotExpression e) {
		List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
		Expression e1 = e.getOperand();
		for (Pair<Integer, Expression> r1 : e1.accept(this)) {
			Expression er1 = r1.getSecond();
			if (er1 instanceof NotExpression) {
				result.add(new Pair<Integer, Expression>(0,((NotExpression) er1).getOperand()));
			} else {
				result.add(new Pair<Integer, Expression>(0,NotExpression.createNotExpression(er1)));
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
