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
package atgt.specification.location;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.Type;

/**
 * A generic variable. Ordered by the cardinality of the domain.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class Variable extends Location implements Comparable<Variable>{

	/** The Constant primeSuffix. */
	public static final String primeSuffix = "___P";

	/**
	 * Instantiates a new variable (by default is monitored).
	 * 
	 * @param _name
	 *            the _name
	 * @param _type
	 *            the _type
	 * @param _value
	 *            the initial value: it can be null
	 */
	@Deprecated
	public Variable(IdExpression _name, Type _type, Expression _value) {
		super(_name, _type, _value);
		this.setMonitored();
	}

	/**
	 * Instantiates a new variable (by default is monitored).
	 * 
	 * @param _name
	 *            the _name
	 * @param _value
	 *            the initial value: it can be null
	 */
	public Variable(IdExpression _name, Expression _value) {
		super(_name, _value);
		this.setMonitored();
	}

	
	/**
	 * A method for visitor pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the T
	 */
	@Override
	public <T> T accept(LocationVisitorI<T> ask) {
		return ask.forVariable(this);
	}

	/**
	 * Variable name of primed variable.
	 * 
	 * @return the primed name
	 */
	public String getPrimedName() {
		return this.name + primeSuffix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Variable v) {
		// compares the ranges
		return v.getType().range() - this.getType().range();
	}

	@Override
	public String toString() {
		return getName();
	}

}
