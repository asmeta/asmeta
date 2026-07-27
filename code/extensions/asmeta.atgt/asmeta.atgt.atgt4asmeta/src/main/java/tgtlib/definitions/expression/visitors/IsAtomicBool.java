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
package tgtlib.definitions.expression.visitors;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MathExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.NumericLiteral;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;



/**
 * computes if the expression is atomic (cannot be split in other atomic booleans).
 * 
 * @author garganti
 */
public final class IsAtomicBool implements ExpressionVisitor<Boolean> {

	/**
	 * Instantiates a new checks if is atomic bool.
	 */
	private IsAtomicBool() {
	}
	
	/** The ss atomic bool. */
	static public IsAtomicBool isAtomicBool = new IsAtomicBool();

	// Math Expression
	// TODO: true or false
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forPlusExpression(atgt.specification.expression.PlusExpression)
	 */
	@Override
	public Boolean forPlusExpression(PlusExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forMinusExpression(atgt.specification.expression.MinusExpression)
	 */
	@Override
	public Boolean forMinusExpression(MinusExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forDivExpression(atgt.specification.expression.DivExpression)
	 */
	@Override
	public Boolean forDivExpression(DivExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forMultExpression(atgt.specification.expression.MultExpression)
	 */
	@Override
	public Boolean forMultExpression(MultExpression e) {
		return true;
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
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNegExpression(atgt.specification.expression.NegExpression)
	 */
	@Override
	public Boolean forNegExpression(NegExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt.specification.expression.IdExpression)
	 */
	@Override
	public Boolean forIdExpression(IdExpression e) {
		return true;
	}

	// Logic Expression
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public Boolean forAndExpression(AndExpression e) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public Boolean forOrExpression(OrExpression e) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt.specification.expression.NotExpression)
	 */
	@Override
	public Boolean forNotExpression(NotExpression e) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forXOrExpression(atgt.specification.expression.XOrExpression)
	 */
	@Override
	public Boolean forXOrExpression(XOrExpression e) {
		return false;
	}

	//
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public Boolean forEqualsExpression(EqualsExpression e) {
		return checkNEqNotBooleanIds(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public Boolean forNotEqualsExpression(NotEqualsExpression e) {
		return checkNEqNotBooleanIds(e);
	}
	
	/** return true if the subexpressions are not boolean*/
	// TODO: it may be a = b which a and b boolean expressions
	// TODO: it may be a /= b which a and b boolean expressions
	private boolean checkNEqNotBooleanIds(BinaryExpression e){
		assert e instanceof EqualsExpression || e instanceof NotEqualsExpression;
		Expression l = e.getFirstOperand();
		Expression r = e.getSecondOperand();
		// useles if I ken the type of the expression
		// first particular case: a = enum 
		if(r instanceof EnumConst || l instanceof EnumConst){
			return true;
		}
		// second particular case: a = 4, or a = math expression
		if(r instanceof NumericLiteral || l instanceof NumericLiteral || r instanceof MathExpression || l instanceof MathExpression){
			return true;
		}
		// try with the type information
		if(r instanceof IdExpression && ((IdExpression) r).getType() != null 
				&& l instanceof IdExpression && ((IdExpression) l).getType() != null){
			// if boolean, than it is equivalent to <=> must be visited inside
			 if (((IdExpression) r).getType() instanceof BoolType) {				 
				 return false;
			 }
			return true;
		}
		System.out.println(((IdExpression) r).getType());
		System.out.println(((IdExpression) l).getType());
		throw new RuntimeException("not applicable for " + e.toString() + " classes: " + l.getClass() + "=" + r.getClass());
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forLessThanExpression(atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public Boolean forLessThanExpression(LessThanExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forLessEqualExpression(atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public Boolean forLessEqualExpression(LessEqualExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forGreaterThanExpression(atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public Boolean forGreaterThanExpression(GreaterThanExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forGreaterEqualExpression(atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public Boolean forGreaterEqualExpression(GreaterEqualExpression e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression(atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public Boolean forImpliesExpression(ImpliesExpression impliesExpression) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNextExpression(atgt.specification.expression.NextExpression)
	 */
	@Override
	public Boolean forNextExpression(NextExpression nextExpression) {
		return true;
	}

	@Override
	public Boolean forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		return true;
	}

	@Override
	public Boolean forModuloExpression(ModuloExpression moduloExpression) {
		return forMathExpression();
	}

	private Boolean forMathExpression() {
		// what return??
		throw new RuntimeException("not applicable");
	}

	@Override
	public Boolean forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
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
