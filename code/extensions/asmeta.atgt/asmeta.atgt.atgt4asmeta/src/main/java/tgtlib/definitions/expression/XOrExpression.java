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
 * XOr expression.
 * 
 * @author Angelo Gargantini
 */

public class XOrExpression extends LogicBinExpression {

	/**
	 * Instantiates a new x or expression.
	 * 
	 * @param firstOperand
	 *            the first operand
	 * @param secondOperand
	 *            the second operand
	 */
	public XOrExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.Expression#accept(atgt.specification.expression.ExpressionVisitorI)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		return ask.forXOrExpression(this);
	}
	@Override
	public String toString() {
		return toString(Operator.XOR);

	}

}
