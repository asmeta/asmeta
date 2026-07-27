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
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionFixedVisitMutator;
import extgt.coverage.fault.mutators.FaultExpressionVisitor;

/**
 * stack at the boolean value passed in the constructor
 */
public abstract class StuckAt extends ExpressionFixedVisitMutator<StuckAt.STVisitor>{

	/**
	 * Instantiates a new stuck at.
	 * 
	 * @param at
	 *            the at
	 */
	protected StuckAt(boolean at) {
		// TODO make more elegant
		super(new STVisitor());
		if (at)
			fev.stuckAt = BoolType.TRUE_CONST;
		else
			fev.stuckAt = BoolType.FALSE_CONST;
	}

	/** The Stuck at0. */
	static public StuckAt STUCK_AT0 = new StuckAt0();

	/** The Stuck at1. */
	static public StuckAt STUCK_AT1 = new StuckAt1();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Stuck at " + fev.stuckAt.getIdString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		if (fev.stuckAt == BoolType.TRUE_CONST)
			return "SA1";
		else
			return "SA0";
	}

	
	static class STVisitor extends FaultExpressionVisitor{
	
		/** The stuck at. */
		private IdExpression stuckAt;
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forUnaryExpression(atgt.specification.expression.UnaryExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forUnaryExpression(Expression e) {
		return makeSingletonStuckAt(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forNegExpression(atgt.specification.expression.NegExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNegExpression(NegExpression e) {
		return makeSingletonStuckAt(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forIdExpression(atgt.specification.expression.IdExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forIdExpression(IdExpression e) {
		return makeSingletonStuckAt(e);
	}

	// Logic Expression
	// not boolean
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forEqualsExpression(EqualsExpression e) {
		return makeSingletonStuckAt(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNotEqualsExpression(NotEqualsExpression e) {
		return makeSingletonStuckAt(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forLessThanExpression(atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forLessThanExpression(LessThanExpression e) {
		return makeSingletonStuckAt(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forLessEqualExpression(atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forLessEqualExpression(LessEqualExpression e) {
		return makeSingletonStuckAt(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forGreaterThanExpression(atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forGreaterThanExpression(GreaterThanExpression e) {
		return makeSingletonStuckAt(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forGreaterEqualExpression(atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forGreaterEqualExpression(GreaterEqualExpression e) {
		return makeSingletonStuckAt(e);
	}

	/**
	 * Make singleton stuck at.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the list< expression>
	 */
	private List<Pair<Integer, Expression>> makeSingletonStuckAt(Expression e) {
		return Collections.singletonList(getStackAtFault());
	}

	/*
	 * MVF ( A and B) = MVF(A) and B + A and MVF(B) + if MVF(A) is empty, add B
	 * an dthe same for A
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forAndExpression(AndExpression e) {
		List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
		// add the stack at in any case
		result.add(getStackAtFault());
		// add al the others
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
		// ad the stack at in any case
		result.add(getStackAtFault());
		result.addAll(distribute(e,Operator.OR));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {		
		List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
		// ad the stack at in any case
		result.add(getStackAtFault());
		result.addAll(distribute(e,Operator.XOR));
		return result;
	}

	private Pair<Integer, Expression> getStackAtFault() {
		return new Pair<Integer, Expression>(1,this.stuckAt);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#forNotExpression(atgt.specification.expression.NotExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNotExpression(NotExpression e) {	
		List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
		 Expression e1 = e.getOperand();
		// do some simplification
		for (Pair<Integer, Expression> r1 : e1.accept(this)) {
			Expression ea = r1.getSecond();
			if (ea == BoolType.FALSE_CONST) {
				result.add(new Pair<Integer, Expression>(1,BoolType.TRUE_CONST));
			} else if (ea == BoolType.TRUE_CONST) {
				result.add(new Pair<Integer, Expression>(1,BoolType.FALSE_CONST));
			} else if (ea instanceof NotExpression) {
				result.add(new Pair<Integer, Expression>(getNextNodePos(r1.getFirst(), false),((NotExpression) ea).getOperand()));
			} else {
				result.add(new Pair<Integer, Expression>(getNextNodePos(r1.getFirst(), false),NotExpression.createNotExpression(ea)));
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
