/**
 * 
 */
package org.asmeta.simulator.value;

import asmeta.terms.furtherterms.StringTerm;

/**
 * A string value.
 *
 */
public class StringValue extends Value<String> {
	private String value;

	/**
	 * Creates a string.
	 * 
	 * @param s a string
	 */
	public StringValue(String s) {
		//PA 2018/07/25. now this seems wrong
		/*if(s.startsWith("\"") && s.endsWith("\"")) {
			value = s;
		}
		else {
			value = "\"" + s + "\"";
		}*/
		value = s;
	}
	
	/**
	 * Creates a string.
	 * 
	 * @param term a string term
	 */
	public StringValue(StringTerm term) {
		this(term.getSymbol());
	}

    /**
     * Gets the value.
     * 
     * @return the value
     */
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof StringValue) {
			return getValue().equals(((StringValue) o).getValue());
		}
		//PA 19/02/10
		else if (o instanceof UndefValue) {
        	return false;
        }
		throw new IllegalArgumentException();
	}
	
	@Override
	public String toString() {
		return value;
	}

	//PA: 2010/06/10
	@Override
	public Value clone() {
		return null;
	}

}
