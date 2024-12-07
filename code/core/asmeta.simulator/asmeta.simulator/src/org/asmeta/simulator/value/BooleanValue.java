/*
 * BooleanValue.java
 *
 * Created on 23 maggio 2006, 14.18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator.value;

import org.asmeta.simulator.TermEvaluator;

import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.Term;

/**
 * A boolean value.
 * 
 */
public class BooleanValue extends Value<Boolean> {
	
	
	/**
	 * The constant True. 
	 */
	public static final BooleanValue TRUE = new BooleanValue(true);
	
	/**
	 * The constant False. 
	 */
	public static final BooleanValue FALSE = new BooleanValue(false);
	
	/**
	 * The value.
	 */
	protected Boolean boolValue = null;
    

    /**
     * Creates a new boolean.
     * 
     * @param bool a boolean value
     */
    private BooleanValue(boolean bool) {
        boolValue = bool;
    }

	// without the value to allow lazy evaluation
    protected BooleanValue() {
    }
    
	/**
     * Creates a new boolean.
     * 
     * @param term a boolean term
     */
    public static BooleanValue parseBooleanValue(BooleanTerm term) {
        return parserBooleanValue(term.getSymbol());
    }
    
    /**
     * Gets a boolean value.
     * 
     * @param bvalue a boolean value
     * @return the same value
     */
    public static BooleanValue parseBooleanValue(boolean bvalue) {
    	if (bvalue) return TRUE;
        else return FALSE;
    }
    
    /**
     * Parses a string and convert it into a boolean value.
     * 
     * @param value a string
     */
    public static BooleanValue parserBooleanValue(String value) {
        boolean bvalue = Boolean.parseBoolean(value);
        return parseBooleanValue(bvalue);
    }
        
    /**
     * Gets the value.
     * 
     * @return the value
     */
    @Override
	public Boolean getValue() {
    	assert boolValue != null : "Boolean Value cannot be null";
        return boolValue;
    }
    
	@Override
	public int hashCode() {
		return Boolean.hashCode(boolValue);
	}
	
    @Override
	public boolean equals(Object object) {
        if (object instanceof BooleanValue) {
            return getValue() == ((BooleanValue) object).getValue();
        }
        //PA 19/02/10
        else if (object instanceof UndefValue) {
        	return false;
        }
        throw new IllegalArgumentException();
    }
    
    @Override
	public String toString() {
        return Boolean.toString(getValue());
    }

    //PA: 14 giugno 10
	@Override
	public Value clone() {
		return new BooleanValue(boolValue);
	}
}
