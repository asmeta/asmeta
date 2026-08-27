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
 * Unary expression.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 * @version $Revision: 1.0 $
 */

public abstract class UnaryExpression implements Expression {

	/** use parenthesis when not (e.g. not a -> not(a) */
	public static boolean forceParOnNot = false;
	
	/** The operand. */
	protected Expression operand;

	/**
	 * Instantiates a new unary expression.
	 * 
	 * @param _operand
	 *            the _operand
	 */
	protected UnaryExpression(Expression _operand) {
		this.operand = _operand;
	}

	/**
	 * Gets the operand.
	 * 
	
	 * @return the operand */
	public Expression getOperand() {
		return this.operand;
	}

	/**
	 * Method toString.
	 * @param o Operator
	 * @param prefix boolean
	 * @return String
	 */
	protected String toString(Operator o, boolean prefix) {
		StringBuffer opeandStr = new StringBuffer(operand.toString());
		return joinParts(o.toString(), opeandStr, prefix).toString();
	}

/** prefix: operator prefixed
 * 
 * @param op
 * @param opndStr
 * @param prefix (e.g. ' is not prefixed

 * @return StringBuffer
 */
	protected StringBuffer joinParts(String op, StringBuffer opndStr, boolean prefix) {
		StringBuffer result = new StringBuffer("");
		if (prefix)	result.append(op);
		//TODO Il parser di ASMETA, in casi particolari,
		//non riesce a parsare il not senza parentesi.
		//Quindi lo mettiamo sempre. Quando sistemeremo il parser, potremmo
		//rimuovere  "|| op.equals("not")".
		//boolean addPar = !(operand instanceof IdExpression);
		boolean addPar = !(operand instanceof IdExpression) || (forceParOnNot && op.equals("not"));
		if (addPar)
			result.append('(').append(opndStr).append(')');
		else{
			// not ID
			if (prefix) result.append(' ');
			result.append(opndStr);
		}
		if (!prefix)
			result.append(op);
		return result;
	}

	/**
	 * create an Unary expression of the right type
	 * 
	 * @param o
	 * @param e
	
	 * @return Expression
	 */
	public static Expression mkUnExpr(Operator o, Expression e) {
		if (o == Operator.prime){
			if (e instanceof IdExpression) {
				// if op = ' => expr = IDexp
				return new PrimedIdExpression((IdExpression) e);
			} else{
				return new NextExpression(e);
			}
		} else if (o == Operator.NOT) {
			return NotExpression.createNotExpression(e);
		} else if (o == Operator.OPPOSITE) {
			return new NegExpression(e);
		}
		throw new RuntimeException("Operator " + o + " is not an unary operator");
	}

	/**
	 * Method equals.
	 * @param obj Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof UnaryExpression) {
			UnaryExpression expr2 = (UnaryExpression) obj;
			/** to expensive
			Operator op1 = this.accept(GetOperator.INSTANCE);
			Operator op2 = expr2.accept(GetOperator.INSTANCE);
			*/
			Class<? extends UnaryExpression> op1 = this.getClass();
			Class<? extends UnaryExpression> op2 = expr2.getClass();
			if (op1 != op2) return false;
			return  expr2.operand.equals(operand);
		} else{
			return false;
		}
	}

	
}
