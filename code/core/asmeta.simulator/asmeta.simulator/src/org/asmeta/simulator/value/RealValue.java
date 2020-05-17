/*
 * RealValue.java
 *
 * Created on 25 maggio 2006, 16.40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator.value;

import asmeta.terms.furtherterms.RealTerm;

/**
 * A real value.
 * 
 */
public class RealValue extends Value<Double> {

	private double realValue;

	/**
	 * Creates a real.
	 * 
	 * @param term a real term
	 */
	public RealValue(RealTerm term) {
		this(term.getSymbol());
	}

	/**
	 * Creates a real.
	 * 
	 * @param value a double
	 */
	public RealValue(double value) {
		realValue = value;
	}

	/**
	 * Creates a real.
	 * 
	 * @param value a string
	 */
	public RealValue(String value) {
		realValue = Double.parseDouble(value);
	}

	/**
	 * gets the value.
	 * 
	 * @return the value
	 */
	@Override
	public Double getValue() {
		return realValue;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof RealValue) {
			return getValue().equals(((RealValue) object).getValue());
		}
		//PA 19/02/10
		else if (object instanceof UndefValue) {
        	return false;
        }
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return Double.toString(realValue);
	}

	//PA: 14 giugno 10
	@Override
	public Value clone() {
		return new RealValue(realValue);
	}

}
