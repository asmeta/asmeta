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
 * The Class ImpliesExpression.
 */
public class ImpliesExpression extends LogicBinExpression {

	/**
	 * Instantiates a new implies expression.
	 * 
	 * @param firstOperand
	 *            the first operand
	 * @param secondOperand
	 *            the second operand
	 */
	public ImpliesExpression(Expression firstOperand, Expression secondOperand) {
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
		return ask.forImpliesExpression(this);
	}

	@Override
	public String toString() {
		return toString(Operator.IMPLIES);

	}

	/** useful to remove the implies
	 * 
	 * @return
	 */
	public OrExpression getEquivalent() {
		return new OrExpression(NotExpression.createNotExpression(firstOperand), secondOperand);
	}

}
