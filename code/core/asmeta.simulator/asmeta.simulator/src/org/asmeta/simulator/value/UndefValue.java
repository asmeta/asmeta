/*
 * UndefValue.java
 *
 * Created on 1 giugno 2006, 7.21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator.value;

/**
 * An undefined value.
 * 
 * @author Alessandro Carioni [acarioni@tele2.it]
 *
 */
public class UndefValue extends Value {
    
    /**
     * The singleton undefined value.
     * 
     */
    public static final UndefValue UNDEF = new UndefValue();
    
    /**
     * Private because the class is singleton.
     * 
     */
    private UndefValue() {
    }
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof UndefValue) {
			return UNDEF == o;
		}
		//TODO is it correct or should return false?
		//see mail from user on Nov 14, 2018
		//throw new IllegalArgumentException(o.getClass().getSimpleName());
		//Nov 30, 2018
		return false;
	}
    
    @Override
	public String toString() {
		return "undef";
	}

	@Override
	public Value clone() {
		return this;
	}

	@Override
	public Object getValue() {
		return null;
	}

}
