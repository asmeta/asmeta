/**
 * EnumValue.java
 *
 * Created on 04/ago/06, 11:28:30
 *
 */
package org.asmeta.simulator.value;

import asmeta.definitions.domains.EnumElement;
import asmeta.terms.furtherterms.EnumTerm;


/**
 * An enumerative value.
 * 
 */
public class EnumValue extends Value<String> {

	private String enumValue;
	
	/**
	 * Creates an enumerative value.
	 * 
	 * @param term an enumerative term
	 */
	public EnumValue(EnumTerm term) {
		this(term.getSymbol());
	}
	
	/**
	 * Creates an enumerative value.
	 * 
	 * @param enumElement an enumerative element
	 */
	public EnumValue(EnumElement enumElement) {
		this(enumElement.getSymbol());
	}
	
	/**
	 * Creates an enumerative value.
	 * 
	 * @param value a string
	 */
	public EnumValue(String value) {
		enumValue = value;
	}
	
	/**
	 * Gets the value.
	 * 
	 * @return the string representation of the value
	 */
	@Override
	public String getValue() {
		return enumValue;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof EnumValue) {
			return getValue().equals(((EnumValue) o).getValue());
		}
		//PA 19/02/10
		else if (o instanceof UndefValue) {
        	return false;
        }
		throw new IllegalArgumentException();
	}
	
	@Override
	public String toString() {
		return enumValue;
	}

	//PA: 14 giugno 10
	@Override
	public Value clone() {
		return new EnumValue(enumValue);
	}
		
}
