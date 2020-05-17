/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import asmeta.terms.basicterms.VariableTerm;

/**
 * A set of variables.
 * 
 */
public class VariableSet {
	
	/**
	 * Set of variable names.
	 * 
	 */
	Set<String> set;
	
	/**
	 * Creates an empty set.
	 * 
	 */
	public VariableSet() {
		set = new HashSet<String>();
	}
	
	/**
	 * Copy constructor.
	 * 
	 */
	public VariableSet(VariableSet anotherSet) {
		set = new HashSet<String>(anotherSet.set);
	}
	
	/**
	 * Creates a set with the given variable names.
	 * 
	 * @param values a list of variable names
	 */
	public VariableSet(String... values) {
		set = new HashSet<String>(Arrays.asList(values));
	}
	
	/**
	 * Adds a variable name.
	 * 
	 * @param var a variable
	 */
	public void add(VariableTerm var) {
		set.add(var.getName());
	}
	
	/**
	 * Adds a set of variable names.
	 * 
	 * @param anotherSet a set of variables
	 */
	public void addAll(VariableSet anotherSet) {
		set.addAll(anotherSet.set);
	}
		
	/**
	 * Is the given variable contained?
	 * 
	 * @param var a variable
	 * @return true if contained, otherwise false
	 */
	public boolean contains(VariableTerm var) {
		return set.contains(var.getName());
	}
	
	/**
	 * Removes all the variable names contained in the given set.
	 * 
	 * @param anotherSet a variable set
	 */
	public void removeAll(VariableSet anotherSet) {
		set.removeAll(anotherSet.set);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((set == null) ? 0 : set.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final VariableSet other = (VariableSet) obj;
		if (set == null) {
			if (other.set != null)
				return false;
		} else if (!set.equals(other.set))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return set.toString();
	}

}
