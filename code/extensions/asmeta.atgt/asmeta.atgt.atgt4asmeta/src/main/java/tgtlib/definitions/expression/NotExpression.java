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

import tgtlib.definitions.expression.type.BoolType.BoolConst;

/**
 * Unary expression: not operand.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class NotExpression extends UnaryExpression {

	/** creates a new notexpression, which could be a subclass of this
	 * 
	 * @param operand
	 * @return
	 */
	public static NotExpression createNotExpression(Expression operand) {
		if (operand instanceof IdExpression)
			return new NotIDExpression((IdExpression) operand);
		else
			return new NotExpression(operand);
	}

	/**
	 * Instantiates a new not expression.
	 * 
	 * @param operand
	 *            the operand
	 */
	protected NotExpression(Expression operand) {
		super(operand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.Expression#accept(atgt.specification.expression.ExpressionVisitorI)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		return ask.forNotExpression(this);
	}

	@Override
	public String toString() {
		return super.toString(Operator.NOT,true);
	}

	/**
	 * Method hashCode.
	 * @return int
	 */
	@Override
	public int hashCode() {
		return 13 * operand.hashCode();
	}
}
