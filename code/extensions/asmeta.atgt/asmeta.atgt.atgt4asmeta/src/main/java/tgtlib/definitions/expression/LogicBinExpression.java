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
 * A logic binary operation The result of operation is a boolean value.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

abstract public class LogicBinExpression extends BinaryExpression {

	/**
	 * Instantiates a new logic bin expression.
	 * 
	 * @param _firstOperand
	 *            the _first operand
	 * @param _secondOperand
	 *            the _second operand
	 */
	public LogicBinExpression(Expression _firstOperand,
			Expression _secondOperand) {
		super(_firstOperand, _secondOperand);
	}
}
