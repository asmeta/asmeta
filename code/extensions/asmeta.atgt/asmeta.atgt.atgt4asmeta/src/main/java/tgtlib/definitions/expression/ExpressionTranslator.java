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


/**
 * visits the expressions and return a StringBuffer this kind of visitor is
 * called traslator.
 */
public abstract class ExpressionTranslator implements
		ExpressionVisitor<StringBuffer> {

	/**
	 * return the translation (e1 op e2) dove ep1 ed ep2 sono la traduzione
	 * usando lo stesso visitatore. Priority should be sonsidered !!!
	 * 
	 * @param e
	 *            the e
	 * @param op
	 *            the op
	 * 
	 * @return the string buffer
	 */
	protected StringBuffer forBinaryExpression(BinaryExpression e, String op) {
		return e.joinParts(op, e.getFirstOperand().accept(this), e.getSecondOperand().accept(this));
	}

	/**
	 * For unary expression.
	 * 
	 * @param e
	 *            the e
	 * @param op
	 *            the op
	 * @param prefix
	 *            TODO
	 * @return the string buffer
	 */
	protected StringBuffer forUnaryExpression(UnaryExpression e, String op,
			boolean prefix) {
		return e.joinParts(op, e.getOperand().accept(this), prefix);
	}

}
