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
 * negative expression - operand.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 * @version $Revision: 1.0 $
 */

public class NegExpression extends UnaryExpression {

	/**
	 * Instantiates a new neg expression.
	 * 
	 * @param operand
	 *            the operand
	 */
	public NegExpression(Expression operand) {
		super(operand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.Expression#accept(atgt.specification.expression.ExpressionVisitorI)
	 */
	/**
	 * Method accept.
	 * @param ask ExpressionVisitor<T>
	 * @return T
	 * @see tgtlib.definitions.expression.Expression#accept(ExpressionVisitor<T>)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		return ask.forNegExpression(this);
	}

	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString() {
		return super.toString(Operator.OPPOSITE,true);
	}
	
}
