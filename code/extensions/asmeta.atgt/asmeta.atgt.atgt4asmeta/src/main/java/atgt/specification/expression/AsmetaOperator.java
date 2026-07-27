/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.specification.expression;

import java.util.HashMap;
import java.util.Map;

import tgtlib.definitions.expression.Operator;

/**
 * the operators used by AsmetaL. The ATGT spec does not need operators, since
 * differentiates the expression by their type.
 */
public class AsmetaOperator extends Operator {

	/**
	 * strings for asmetal operators, in cas ethey differ from
	 * standrtadotherwise take tha standard notation
	 * 
	 */
	static Map<Operator, String> asmetaLOpStrg = new HashMap<Operator, String>();

	static {
		/** The NEQ. */
		setAsmetaLString(NEQ, "neq");

		/** The EQ. */
		setAsmetaLString(EQ, "eq");

		/** TO CHECK !! < or gt. */
		setAsmetaLString(LT, "lt");

		/** The LE. */
		setAsmetaLString(LE, "le");

		/** The GT. */
		setAsmetaLString(GT, "gt");

		/** The GE. */
		setAsmetaLString(GE, "ge");

		/** The PLUS. */
		setAsmetaLString(PLUS, "plus");

		/** The MINUS. */
		setAsmetaLString(MINUS, "minus");

		/** The MULT. */
		setAsmetaLString(MULT, "mult");

		/** The DIV. */
		setAsmetaLString(DIV, "idiv");

		/** The mod. */
		setAsmetaLString(MOD, "mod");

		/** The mod. */
		setAsmetaLString(OPPOSITE, "minus");

	}


	private AsmetaOperator() {
		super("");
	}

	/**
	 * convert a string 8as in Asmetal String to an operator.
	 * 
	 * @param op
	 *            the op
	 * 
	 * @return the operator (never null)
	 */
	public static Operator parseOperator(String op) {
		Operator result = getOperator(op);
		assert result != null : "operator " + op + " not recognized !!";
		return result;

	}

	/**
	 * can return null;
	 * 
	 * @param op
	 */
	private static Operator getOperator(String op) {
		if (getAsmetaLString(AND).equals(op))
			return AND;
		if (getAsmetaLString(DIV).equals(op))
			return DIV;
		if (getAsmetaLString(EQ).equals(op))
			return EQ;
		if (getAsmetaLString(GE).equals(op))
			return GE;
		if (getAsmetaLString(GT).equals(op))
			return GT;
		if (getAsmetaLString(LE).equals(op))
			return LE;
		if (getAsmetaLString(LT).equals(op))
			return LT;
		if (getAsmetaLString(MINUS).equals(op))
			return MINUS;
		if (getAsmetaLString(MULT).equals(op))
			return MULT;
		if (getAsmetaLString(NEQ).equals(op))
			return NEQ;
		if (getAsmetaLString(NOT).equals(op))
			return NOT;
		if (getAsmetaLString(OR).equals(op))
			return OR;
		if (getAsmetaLString(PLUS).equals(op))
			return PLUS;
		if (getAsmetaLString(XOR).equals(op))
			return XOR;
		if (getAsmetaLString(IMPLIES).equals(op))
			return IMPLIES;
		if (getAsmetaLString(MOD).equals(op))
			return MOD;
		return null;
	}

	/**
	 * return if op is an operator.
	 * 
	 * @param op
	 *            the op
	 * 
	 * @return true, if checks if is operator
	 */
	public static boolean isOperator(String op) {
		return getOperator(op) != null;
	}

	/**
	 * Gets the asmetal string.
	 * 
	 * @return the op
	 */

	public static String getAsmetaLString(Operator o) {
		String asmetaStr = asmetaLOpStrg.get(o);
		if (asmetaStr == null)
			return o.toString();
		else
			return asmetaStr;
	}

	/**
	 * set the string in asmetal
	 * 
	 * @param op
	 * @param string
	 */
	private static void setAsmetaLString(Operator op, String string) {
		assert !asmetaLOpStrg.containsKey(op);
		asmetaLOpStrg.put(op, string);
	}

}
