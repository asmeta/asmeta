/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.definitions.expression;

import tgtlib.definitions.expression.type.BoolType;

/**
 * return true if the expression is a boolean expression.
 * 
 * @author garganti
 */
public final class IsLogicExpression implements ExpressionVisitor<Boolean> {

	/**
	 * Instantiates a new checks if is logic expression.
	 */
	private IsLogicExpression(boolean tol) {
		acceptIDExpressionAsLogic = tol;
	}
	
	private boolean acceptIDExpressionAsLogic;

	/** The is logic. */
	static public IsLogicExpression isLogic = new IsLogicExpression(false);

	static public IsLogicExpression isLogicTolerant = new IsLogicExpression(true);

	// Math Expression
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forPlusExpression(atgt.specification.expression.PlusExpression)
	 */
	@Override
	public Boolean forPlusExpression(PlusExpression e) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forMinusExpression(atgt.specification.expression.MinusExpression)
	 */
	@Override
	public Boolean forMinusExpression(MinusExpression e) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forDivExpression(atgt.specification.expression.DivExpression)
	 */
	@Override
	public Boolean forDivExpression(DivExpression e) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forMultExpression(atgt.specification.expression.MultExpression)
	 */
	@Override
	public Boolean forMultExpression(MultExpression e) {
		return false;
	}

	/**
	 * For unary expression.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the boolean
	 */
	public Boolean forUnaryExpression(Expression e) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forNegExpression(atgt.specification.expression.NegExpression)
	 */
	@Override
	public Boolean forNegExpression(NegExpression e) {
		return false;
	}

	//
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forIdExpression(atgt.specification.expression.IdExpression)
	 */
	@Override
	public Boolean forIdExpression(IdExpression e) {
		// do not know if boolean or not
		if (acceptIDExpressionAsLogic) return true;
		if (e.getType() == BoolType.BOOLTYPE) return true;
		else throw new RuntimeException("Is "+ e + " a logic ID??");
	}

	// Logic Expression
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public Boolean forAndExpression(AndExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public Boolean forOrExpression(OrExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forNotExpression(atgt.specification.expression.NotExpression)
	 */
	@Override
	public Boolean forNotExpression(NotExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forXOrExpression(atgt.specification.expression.XOrExpression)
	 */
	@Override
	public Boolean forXOrExpression(XOrExpression e) {
		return true;
	}

	//
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public Boolean forEqualsExpression(EqualsExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public Boolean forNotEqualsExpression(NotEqualsExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forLessThanExpression(atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public Boolean forLessThanExpression(LessThanExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forLessEqualExpression(atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public Boolean forLessEqualExpression(LessEqualExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forGreaterThanExpression(atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public Boolean forGreaterThanExpression(GreaterThanExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forGreaterEqualExpression(atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public Boolean forGreaterEqualExpression(GreaterEqualExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forImpliesExpression(atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public Boolean forImpliesExpression(ImpliesExpression impliesExpression) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forNextExpression(atgt.specification.expression.NextExpression)
	 */
	@Override
	public Boolean forNextExpression(NextExpression nextExpression) {
		return true;
	}

	@Override
	public Boolean forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		// do not know if boolean or not
		throw new RuntimeException("logic ID??");
	}

	@Override
	public Boolean forModuloExpression(ModuloExpression moduloExpression) {
		return false;
	}

	@Override
	public Boolean forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("not implemented yet");
	}
	
	@Override
	public Boolean forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Boolean forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	
	
}
