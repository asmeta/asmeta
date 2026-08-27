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
 * represents the next(operand) operand must be a boolean (is it cannot be a
 * next of a variable integer this to permit the translation into CTL/LTL with
 * X.
 * 
 * @author garganti
 * @version $Revision: 1.0 $
 */
public class NextExpression extends UnaryExpression {

	/**
	 * Instantiates a new next expression.
	 * 
	 * @param _operand
	 *            the _operand
	 */
	public NextExpression(Expression _operand) {
		super(_operand);
		assert ! (_operand instanceof IdExpression) : " use PrimediD instead"; 
		if (!_operand.accept(IsLogicExpression.isLogic))
			throw new RuntimeException("next of a non logic expression !!!");
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
		return ask.forNextExpression(this);
	}
	
	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString() {
		return super.toString(Operator.prime,false);
	}

}
