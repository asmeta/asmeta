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
 * A class for handle binary expression. this object are immutable, their
 * creation could be optimized
 * 
 * @author Angelo Gargantini
 * @version $Revision: 1.0 $
 */

abstract public class BinaryExpression implements Expression {

	/** The first operand. */
	protected Expression firstOperand;

	/** The second operand. */
	protected Expression secondOperand;

	/**
	 * Instantiates a new binary expression.
	 * 
	 * @param _firstOperand
	 *            the _first operand
	 * @param _secondOperand
	 *            the _second operand
	 */
	protected BinaryExpression(Expression _firstOperand, Expression _secondOperand) {
		assert _firstOperand != null;
		assert _secondOperand != null;
		this.firstOperand = _firstOperand;
		this.secondOperand = _secondOperand;
	}

	/**
	 * Gets the first operand.
	 * 
	
	 * @return the first operand */
	public Expression getFirstOperand() {
		return this.firstOperand;
	}

	/**
	 * Gets the second operand.
	 * 
	
	 * @return the second operand */
	public Expression getSecondOperand() {
		return this.secondOperand;
	}

	/**
	 * join parts inserting parenthesis if necessary
	 * 
	 * @param operator
	 * @param e1Translation
	 * @param e2Translation
	
	 * @return StringBuffer
	 */
	protected StringBuffer joinParts(String operator,
			StringBuffer e1Translation, StringBuffer e2Translation) {
		StringBuffer result = new StringBuffer();
		// e1
		if (waivePar(firstOperand))
			result.append(e1Translation);
		else
			result.append('(').append(e1Translation).append(')');
		// op
		result.append(' ').append(operator).append(' ');
		// e2
		if (waivePar(secondOperand))
			result.append(e2Translation);
		else
			result.append('(').append(e2Translation).append(')');
		return result;
	}

	/**
	 * return true if the operand has priority over the binary operator and the
	 * parenthesis are useless TODO: use operators priority ...
	 * 
	 * @param operand
	
	 * @return boolean
	 */
	private boolean waivePar(Expression operand) {
		// a op ... and a is Id
		if (operand instanceof IdExpression)
			return true;
		// not(x) and ...
		if (operand instanceof NotExpression)
			return true;
		// next(x > 0) and ...
		if (operand instanceof NextExpression)
			return true;
		// prime(x) and ...
		if (operand instanceof PrimedIdExpression)
			return true;
		return false;
	}


	/**
	 * return the string buffer for this operator
	 * 
	 * @param o
	
	 * @return String
	 */
	protected final String toString(Operator o) {
		StringBuffer e1Translation = new StringBuffer(firstOperand.toString());
		StringBuffer e2Translation = new StringBuffer(secondOperand.toString());
		return joinParts(o.toString(), e1Translation, e2Translation).toString();
	}

	/**
	 * builds a Binary Expression given two expressions and an operator.
	 * 
	 * @param op
	 *            the op
	
	 * 
	
	 * @param a Expression
	 * @param b Expression
	 * @return the expression */
	public static BinaryExpression mkBinExpr(Expression a, Operator op, Expression b) {
		assert a != null;
		assert b != null;
		assert op != null : "null(" + a.toString() + "," + b.toString() + ")";
		assert op instanceof Operator.BinaryOperator : op.toString() + " not a binary opereator";
		return ((Operator.BinaryOperator)op).mkBinExpr(a, b);
	}

	/**
	 * Method equals.
	 * @param obj Object
	 * @return boolean
	 */
	@Override
	final public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof BinaryExpression) {
			BinaryExpression binExpression = (BinaryExpression) obj;
			// Too expensive
			/*Operator op1 = this.accept(GetOperator.INSTANCE);
			Operator op2 = binExpression.accept(GetOperator.INSTANCE);*/
			// must be the same subclass
			Class<? extends BinaryExpression> op1 = this.getClass();
			Class<? extends BinaryExpression> op2 = binExpression.getClass();
			if (op1 != op2) return false;
			boolean v = binExpression.firstOperand.equals(firstOperand);
			if (! v ) return false;
			v = binExpression.secondOperand.equals(secondOperand);
			return v;			
		} else {
			return false;
		}
	}
}
