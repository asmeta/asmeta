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
 * Equals expression. It is also used as <=> among boolean operators
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class EqualsExpression extends CompareExpression {

	/**
	 * Instantiates a new equals expression.
	 * 
	 * @param firstOperand
	 *            the first operand
	 * @param secondOperand
	 *            the second operand
	 */
	public EqualsExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.Expression#accept(atgt.specification.expression.ExpressionVisitorI)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		return ask.forEqualsExpression(this);
	}

	@Override
	public String toString() {
		return toString(Operator.EQ);
	}

	//In order to have equals here, you cannot have final in
	//the equals of BinaryExpression
	/*@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof EqualsExpression) {
			EqualsExpression eqExpression = (EqualsExpression) obj;
			return (eqExpression.firstOperand.equals(firstOperand) &&
					eqExpression.secondOperand.equals(secondOperand)) ||
					(eqExpression.firstOperand.equals(secondOperand) &&
							eqExpression.secondOperand.equals(firstOperand));			
		}
		else {
			return false;
		}
	}*/
}