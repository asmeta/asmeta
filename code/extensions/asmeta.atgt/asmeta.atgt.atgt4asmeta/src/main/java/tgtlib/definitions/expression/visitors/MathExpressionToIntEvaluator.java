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
package tgtlib.definitions.expression.visitors;

import java.util.Map;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.XOrExpression;

/**
 * evaluates an expression against a list of assignments and returns true if it
 * is satisfied by the expression The model must be completed: it must give the
 * value for every variable
 * 
 * It is implemented as ExpressionVisitor, which returns a boolean. The
 * assignments are a field of the visitor.
 */
public class MathExpressionToIntEvaluator implements
		tgtlib.definitions.expression.ExpressionVisitor<Integer> {

	static private Logger logger = Logger.getLogger(MathExpressionToIntEvaluator.class);

	/** The list of assignments variable -> value. represents a state */
	Map<IdExpression, String> state; 

	public MathExpressionToIntEvaluator(Map<IdExpression, String> state) {
		this.state = state;
	}

	/** call the evaluator : TODO use only this entry point
	 * 
	 * @param e
	 * @return
	 */
	public Integer evaluate(Expression e) {
		logger.debug("evaluating " + e);
		return e.accept(this);
	}


	/*
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt
	 * .specification.expression.AndExpression)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt
	 * .specification.expression.AndExpression)
	 */
	@Override
	public Integer forAndExpression(AndExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forDivExpression(atgt
	 * .specification.expression.DivExpression)
	 */
	@Override
	public Integer forDivExpression(DivExpression e) {
		Integer e1 = e.getFirstOperand().accept(this);
		Integer e2 = e.getSecondOperand().accept(this);
		return e1/e2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression
	 * (atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public Integer forEqualsExpression(EqualsExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forGreaterEqualExpression
	 * (atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public Integer forGreaterEqualExpression(GreaterEqualExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forGreaterThanExpression
	 * (atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public Integer forGreaterThanExpression(GreaterThanExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt
	 * .specification.expression.IdExpression)
	 */
	@Override
	public Integer forIdExpression(IdExpression var) {
		if(state.containsKey(var)) {
			String value = state.get(var);
			try {
				return new Integer(value);
			}
			catch(NumberFormatException e) {
				throw new EvaluationNotSupported(var + " not an integer variable");
			}
		}
		else {
			try {
				return Integer.parseInt(var.toString());
			}
			catch(NumberFormatException e) {
				throw new EvaluationNotSupported(var + " not a number");
			}
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessEqualExpression
	 * (atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public Integer forLessEqualExpression(LessEqualExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessThanExpression
	 * (atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public Integer forLessThanExpression(LessThanExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMinusExpression
	 * (atgt.specification.expression.MinusExpression)
	 */
	@Override
	public Integer forMinusExpression(MinusExpression e) {
		Integer e1 = e.getFirstOperand().accept(this);
		Integer e2 = e.getSecondOperand().accept(this);
		return e1 - e2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMultExpression(
	 * atgt.specification.expression.MultExpression)
	 */
	@Override
	public Integer forMultExpression(MultExpression e) {
		Integer e1 = e.getFirstOperand().accept(this);
		Integer e2 = e.getSecondOperand().accept(this);
		return e1 * e2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNegExpression(atgt
	 * .specification.expression.NegExpression)
	 */
	@Override
	public Integer forNegExpression(NegExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression
	 * (atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public Integer forNotEqualsExpression(NotEqualsExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt
	 * .specification.expression.NotExpression)
	 */
	@Override
	public Integer forNotExpression(NotExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt
	 * .specification.expression.OrExpression)
	 */
	@Override
	public Integer forOrExpression(OrExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forPlusExpression(
	 * atgt.specification.expression.PlusExpression)
	 */
	@Override
	public Integer forPlusExpression(PlusExpression e) {
		Integer e1 = e.getFirstOperand().accept(this);
		Integer e2 = e.getSecondOperand().accept(this);
		return e1 + e2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forUnaryExpression
	 * (atgt.specification.expression.UnaryExpression)
	 */
	/**
	 * For unary expression.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the boolean
	 */
	public Integer forUnaryExpression(UnaryExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forXOrExpression(atgt
	 * .specification.expression.XOrExpression)
	 */
	@Override
	public Integer forXOrExpression(XOrExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression
	 * (atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public Integer forImpliesExpression(ImpliesExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNextExpression(
	 * atgt.specification.expression.NextExpression)
	 */
	@Override
	public Integer forNextExpression(NextExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}


	// ///////////////////////
	/*
	 * for these it is not supported
	 */

	@Override
	public Integer forModuloExpression(ModuloExpression e) {
		Integer e1 = e.getFirstOperand().accept(this);
		Integer e2 = e.getSecondOperand().accept(this);
		return e1.intValue()%e2.intValue();
	}

	@Override
	public Integer forPrimedIdExpression(PrimedIdExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	@Override
	public Integer forFunctionTerm(FunctionTerm ft) {
		throw new EvaluationNotSupported(ft.getClass() + "not supported");
	}
	
	@Override
	public Integer forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Integer forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}