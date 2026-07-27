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

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.EqualsExpression;
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
 * Implementa l'interfaccia AsmExpressionVisitor e fornisce i metodi per la
 * traduzione in SAL delle espressioni della specifica.
 * 
 * @author Angelo Gargantini
 */
public class ExpressionToSALVisitor extends MathExpressionTranslator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt.specification.expression.IdExpression)
	 */
	@Override
	public StringBuffer forIdExpression(IdExpression e) {
		return new StringBuffer(EnumConst.toStrCheckBool(e, "FALSE","TRUE"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public StringBuffer forAndExpression(AndExpression e) {
		return forBinaryExpression(e, "AND");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public StringBuffer forOrExpression(OrExpression e) {
		return forBinaryExpression(e, "OR");
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
		return forUnaryExpression(e, "NOT", true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public StringBuffer forEqualsExpression(EqualsExpression e) {
		return forBinaryExpression(e, "=");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public StringBuffer forNotEqualsExpression(NotEqualsExpression e) {
		return forBinaryExpression(e, "/=");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression(atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public StringBuffer forImpliesExpression(ImpliesExpression impliesExpression) {
		return forBinaryExpression(impliesExpression, "=>");
	}

	/**
	 * assuming that the translation is to LTL, otherwise I shoul use '.
	 * 
	 * @param nextExpression
	 *            the next expression
	 * 
	 * @return the string buffer
	 */
	@Override
	public StringBuffer forNextExpression(NextExpression nextExpression) {
		return forUnaryExpression(nextExpression, "X", true);
	}
	@Override
	public StringBuffer forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("operator next not supported in C");
	}

	@Override
	public StringBuffer forModuloExpression(ModuloExpression e) {
		throw new RuntimeException("modulo not supported in SAL ?? check the manula langauge not found");
	}

	@Override
	public StringBuffer forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("not implemented yet");
	}
	@Override
	public StringBuffer forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public StringBuffer forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
