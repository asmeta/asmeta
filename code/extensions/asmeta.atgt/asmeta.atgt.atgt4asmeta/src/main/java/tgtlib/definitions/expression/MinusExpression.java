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
 * Minus expression.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class MinusExpression extends MathExpression {

	/**
	 * Instantiates a new minus expression.
	 * 
	 * @param firstOperand
	 *            the first operand
	 * @param secondOperand
	 *            the second operand
	 */
	public MinusExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.Expression#accept(atgt.specification.expression
	 * .ExpressionVisitorI)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		return ask.forMinusExpression(this);
	}

	@Override
	public String toString() {
		return toString(Operator.MINUS);

	}

}
