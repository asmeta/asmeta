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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
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
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.Variable;

/**
 * evaluates an expression against a list of assignments and returns true if it
 * is satisfied by the expression The model must be completed: it must give the
 * value for every variable
 * 
 * It is implemented as ExpressionVisitor, which returns a boolean. The
 * assignments are a field of the visitor.
 */
public class MathExpressionEvaluator implements
		tgtlib.definitions.expression.ExpressionVisitor<Expression> {

	static private Logger logger = Logger.getLogger(MathExpressionEvaluator.class);

	/** The list of assignments variable -> value. represents a state */
	Map<IdExpression, String> state; 

	public MathExpressionEvaluator(Map<? extends Variable, String> list, Iterable<? extends Variable> vars) {
		logger.debug("evaluating over " + list + " with vars " + vars);
		state = new HashMap<IdExpression, String>();
		add(list);
		// get the initial values of controlled vars
		assert vars != null;
		for (tgtlib.definitions.expression.type.Variable v : vars) {
			if (v.isControlled()) {
				Expression e = v.getValue();
				if (e != null && e != Undef.UNDEF)
					state.put(v.getIdExpression(), e.toString());
			}
		}
	}
	
	public MathExpressionEvaluator(Map<? extends Variable, String> assignment) {
		this(assignment, assignment.keySet());
	}

	private void add(Map<? extends Variable, String> list) {
		for (Entry<? extends Variable, String> i : list.entrySet()) {
			state.put(i.getKey().getIdExpression(), i.getValue());
		}
	}

	/** call the evaluator : TODO use only this entry point
	 * 
	 * @param e
	 * @return
	 */
	public Expression evaluate(Expression e) {
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
	public Expression forAndExpression(AndExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.AND, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forDivExpression(atgt
	 * .specification.expression.DivExpression)
	 */
	@Override
	public Expression forDivExpression(DivExpression e) {
		throw new EvaluationNotSupported(e.getClass() + " not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression
	 * (atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public Expression forEqualsExpression(EqualsExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.EQ, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forGreaterEqualExpression
	 * (atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public Expression forGreaterEqualExpression(GreaterEqualExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.GE, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forGreaterThanExpression
	 * (atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public Expression forGreaterThanExpression(GreaterThanExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.GT, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt
	 * .specification.expression.IdExpression)
	 */
	@Override
	public Expression forIdExpression(IdExpression var) {
		if(state.containsKey(var)) {
			return IdExpressionCreator.createNewIdExpression(state.get(var));
		}
		return var;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessEqualExpression
	 * (atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public Expression forLessEqualExpression(LessEqualExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.LE, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessThanExpression
	 * (atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public Expression forLessThanExpression(LessThanExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.LT, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMinusExpression
	 * (atgt.specification.expression.MinusExpression)
	 */
	@Override
	public Expression forMinusExpression(MinusExpression e) {
		Expression e1 = e.getFirstOperand().accept(this);
		Expression e2 = e.getSecondOperand().accept(this);
		int diff = Integer.parseInt(e1.toString()) - Integer.parseInt(e2.toString());
		return IdExpressionCreator.createNewIdExpression(String.valueOf(diff));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMultExpression(
	 * atgt.specification.expression.MultExpression)
	 */
	@Override
	public Expression forMultExpression(MultExpression e) {
		Expression e1 = e.getFirstOperand().accept(this);
		Expression e2 = e.getSecondOperand().accept(this);
		long mult = Integer.parseInt(e1.toString()) * Integer.parseInt(e2.toString());
		return IdExpressionCreator.createNewIdExpression(String.valueOf(mult));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNegExpression(atgt
	 * .specification.expression.NegExpression)
	 */
	@Override
	public Expression forNegExpression(NegExpression e) {
		int n = -Integer.parseInt(e.getOperand().accept(this).toString());
		return IdExpressionCreator.createNewIdExpression(String.valueOf(n));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression
	 * (atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public Expression forNotEqualsExpression(NotEqualsExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.NEQ, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt
	 * .specification.expression.NotExpression)
	 */
	@Override
	public Expression forNotExpression(NotExpression e) {
		return UnaryExpression.mkUnExpr(Operator.NOT, e.getOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt
	 * .specification.expression.OrExpression)
	 */
	@Override
	public BinaryExpression forOrExpression(OrExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.OR, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forPlusExpression(
	 * atgt.specification.expression.PlusExpression)
	 */
	@Override
	public Expression forPlusExpression(PlusExpression e) {
		Expression e1 = e.getFirstOperand().accept(this);
		Expression e2 = e.getSecondOperand().accept(this);
		int sum = Integer.parseInt(e1.toString()) + Integer.parseInt(e2.toString());
		return IdExpressionCreator.createNewIdExpression(String.valueOf(sum));
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
	public Boolean forUnaryExpression(Expression e) {
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
	public Expression forXOrExpression(XOrExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.XOR, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression
	 * (atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public Expression forImpliesExpression(ImpliesExpression e) {
		return BinaryExpression.mkBinExpr(e.getFirstOperand().accept(this), Operator.IMPLIES, e.getSecondOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNextExpression(
	 * atgt.specification.expression.NextExpression)
	 */
	@Override
	public Expression forNextExpression(NextExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}


	// ///////////////////////
	/*
	 * for these it is not supported
	 */

	@Override
	public Expression forModuloExpression(ModuloExpression e) {
		Expression e1 = e.getFirstOperand().accept(this);
		Expression e2 = e.getSecondOperand().accept(this);
		int mod = Integer.parseInt(e1.toString()) % Integer.parseInt(e2.toString());
		return IdExpressionCreator.createNewIdExpression(String.valueOf(mod));
	}

	@Override
	public Expression forPrimedIdExpression(PrimedIdExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	@Override
	public Expression forFunctionTerm(FunctionTerm ft) {
		throw new EvaluationNotSupported(ft.getClass() + "not supported");
	}
	
	@Override
	public Expression forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}