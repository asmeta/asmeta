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

import java.util.List;

/**
 * And expression.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class AndExpression extends LogicBinExpression {

	/**
	 * Instantiates a new and expression.
	 * 
	 * @param firstOperand
	 *            the first operand
	 * @param secondOperand
	 *            the second operand
	 */
	public AndExpression(Expression firstOperand, Expression secondOperand) {
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
		return ask.forAndExpression(this);
	}

	@Override
	public String toString() {
		return toString(Operator.AND);
	}

	/**
	 * given a list of expressions, return the and between them
	 * 
	 * @param ecl
	 * @return
	 */
	public static Expression makeAndExpression(List<Expression> ecl) {
		assert ecl.size() >= 1;
		Expression result = ecl.get(0);
		for (int i = 1; i < ecl.size(); i++) {
			result = new AndExpression(result, ecl.get(i));
		}
		return result;
	}

	public static Expression makeAndExpression(Expression op1, Expression op2) {
		return new AndExpression(op1, op2);
	}
}
