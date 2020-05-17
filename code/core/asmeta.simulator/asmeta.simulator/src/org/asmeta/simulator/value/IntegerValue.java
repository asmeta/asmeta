/*
 * IntegerValue.java
 *
 * Created on 25 maggio 2006, 16.38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator.value;

import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;

/**
 * An integer value.
 * 
 */
public class IntegerValue extends Value<Long> {
	private long integerValue;

	/**
	 * Creates an integer.
	 * 
	 * @param term
	 *            a natural term
	 */
	public IntegerValue(NaturalTerm term) {
		// removes the 'n' at the end of the string
		String value = term.getSymbol();
		value = value.substring(0, value.indexOf('n'));
		integerValue = Long.parseLong(value);
	}

	/**
	 * Creates an integer.
	 * 
	 * @param term
	 *            an integer term
	 */
	public IntegerValue(IntegerTerm term) {
		this(term.getSymbol());
	}

	/**
	 * Creates an integer.
	 * 
	 * @param value
	 *            an integer
	 */
	public IntegerValue(long value) {
		integerValue = value;
	}

	/**
	 * Creates an integer.
	 * 
	 * @param value
	 *            a string
	 */
	public IntegerValue(String value) {
		integerValue = Long.parseLong(value);
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	@Override
	public Long getValue() {
		return integerValue;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof IntegerValue) {
			return integerValue == (((IntegerValue) object)).integerValue;
		}
		// PA 19/02/10
		else if (object instanceof UndefValue) {
			return false;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return Long.toString(integerValue);
	}

	// PA: 14 giugno 10
	@Override
	public Value clone() {
		return new IntegerValue(integerValue);
	}
}