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
package atgt.translator;

import java.util.Iterator;
import java.util.Map.Entry;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.visitors.MathExpressionTranslator;

/**
 * The Class ExpressionToC.
 */
public class ExpressionToC extends MathExpressionTranslator {

	/** The EXP r_ t o_ c. */
	public static ExpressionToC EXPR_TO_C = new ExpressionToC();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt.specification.expression.IdExpression)
	 */
	@Override
	public StringBuffer forIdExpression(IdExpression e) {
		String strCheckBool = EnumConst.toStrCheckBool(e, "0", "1");
		// local logical variables
		strCheckBool = strCheckBool.replace("$", "__l_");
		return new StringBuffer(strCheckBool);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public StringBuffer forAndExpression(AndExpression e) {
		return forBinaryExpression(e, "&&");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public StringBuffer forOrExpression(OrExpression e) {
		return forBinaryExpression(e, "||");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forXOrExpression(atgt.specification.expression.XOrExpression)
	 */
	@Override
	public StringBuffer forXOrExpression(XOrExpression e) {
		return forBinaryExpression(e, "^");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt.specification.expression.NotExpression)
	 */
	@Override
	public StringBuffer forNotExpression(NotExpression e) {
		return forUnaryExpression(e, "!", true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public StringBuffer forEqualsExpression(EqualsExpression e) {
		return forBinaryExpression(e, "==");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public StringBuffer forNotEqualsExpression(NotEqualsExpression e) {
		return forBinaryExpression(e, "!=");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression(atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public StringBuffer forImpliesExpression(ImpliesExpression ie) {
		OrExpression oe = ie.getEquivalent();
		return oe.accept(this);
	}

	/**
	 * assuming that the translation is to LTL, otherwise I should use '.
	 * 
	 * @param nextExpression
	 *            the next expression
	 * 
	 * @return the string buffer
	 */
	@Override
	public StringBuffer forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("operator next not supported in C");
	}

	@Override
	public StringBuffer forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("PrimedIdExpression not supported in C");
	}

	@Override
	public StringBuffer forModuloExpression(ModuloExpression e) {
		return forBinaryExpression(e, "%");
	}

	@Override
	public StringBuffer forFunctionTerm(FunctionTerm ft) {
		// TODO it may function with more arguments, to check
		assert ft.getArguments().size() == 1;
		// function[][]...
		StringBuffer result = new StringBuffer(ft.getFunction().getIdString());
		for(Expression e: ft.getArguments()){
			result.append("[").append(e.accept(this)).append("]");
		}
		return result;
	}
	
	@Override
	public StringBuffer forCaseExpression(CaseExpression caseExpression) {
		String id = caseExpression.getIdSwitch().getIdString();
		int numCases = caseExpression.getCases().size();
		Iterator<Entry<IdExpression, Expression>> cases = caseExpression.getCases().entrySet().iterator();
		StringBuffer result = new StringBuffer("(");
		int openParenthesis = 1;
		for(int i = 0; i < numCases; i++) {
			Entry<IdExpression, Expression> currCase = cases.next();
			String eq = id + " == " + currCase.getKey().getIdString();
			String thenExp = currCase.getValue().accept(this).toString();
			if (i < numCases - 1) {
				result.append(eq + getCfrOperator() + thenExp + " : ");
				result.append("(");
				openParenthesis++;
			} else {
				// last case, no guard is needed
				Expression defaultCase = caseExpression.getDefaultCase();
				if(defaultCase != null) {
					// default case
					result.append(eq + getCfrOperator() + thenExp + " : " + defaultCase.accept(this));
					result.append(defaultCase.accept(this));
				} else {
					//then expression if not defined default
					result.append(thenExp); //NOTE THAT THE FUNCTION MUST BE TOTAL
				}
			}
		}
		for (int i = 0 ; i < openParenthesis ; i++)
			result.append(")");
		return result;
	}

	protected String getCfrOperator() {
		return " ? ";
	}

	@Override
	public StringBuffer forConditionalExpression(CondExpression cond) {
		// translate to ? :
		StringBuffer result = new StringBuffer("(");
		StringBuffer guard = cond.getCondition().accept(this);
		result.append(guard);
		result.append(getCfrOperator());
		StringBuffer thenE = cond.getThenE().accept(this);
		result.append(thenE);
		result.append(" : ");
		StringBuffer elseE = cond.getElseE().accept(this);
		result.append(elseE);
		result.append(")");
		return result;
	}
}
