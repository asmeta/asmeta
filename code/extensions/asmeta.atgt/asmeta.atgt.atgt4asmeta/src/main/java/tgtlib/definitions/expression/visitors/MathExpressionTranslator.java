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

import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.ExpressionTranslator;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.PlusExpression;

/**
 * translation of math expressions by using the usual mathematical operators.
 * @author garganti
 * @version $Revision: 1.0 $
 */
public abstract class MathExpressionTranslator extends ExpressionTranslator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forPlusExpression(atgt.specification.expression.PlusExpression)
	 */
	@Override
	public StringBuffer forPlusExpression(PlusExpression e) {
		return forBinaryExpression(e, "+");
	}

	/**
	 * Method forModuloExpression.
	 * @param e ModuloExpression
	 * @return StringBuffer
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forModuloExpression(ModuloExpression)
	 */
	@Override
	public StringBuffer forModuloExpression(ModuloExpression e) {
		return forBinaryExpression(e, "%");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forMinusExpression(atgt.specification.expression.MinusExpression)
	 */
	@Override
	public StringBuffer forMinusExpression(MinusExpression e) {
		return forBinaryExpression(e, "-");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forDivExpression(atgt.specification.expression.DivExpression)
	 */
	@Override
	public StringBuffer forDivExpression(DivExpression e) {
		return forBinaryExpression(e, "/");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forMultExpression(atgt.specification.expression.MultExpression)
	 */
	@Override
	public StringBuffer forMultExpression(MultExpression e) {
		return forBinaryExpression(e, "*");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forLessThanExpression(atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public StringBuffer forLessThanExpression(LessThanExpression e) {
		return forBinaryExpression(e, "<");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forLessEqualExpression(atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public StringBuffer forLessEqualExpression(LessEqualExpression e) {
		return forBinaryExpression(e, "<=");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forGreaterThanExpression(atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public StringBuffer forGreaterThanExpression(GreaterThanExpression e) {
		return forBinaryExpression(e, ">");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forGreaterEqualExpression(atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public StringBuffer forGreaterEqualExpression(GreaterEqualExpression e) {
		return forBinaryExpression(e, ">=");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.ExpressionVisitorI#forNegExpression(atgt.specification.expression.NegExpression)
	 */
	@Override
	public StringBuffer forNegExpression(NegExpression e) {
		return forUnaryExpression(e, "-", true);
	}

}
