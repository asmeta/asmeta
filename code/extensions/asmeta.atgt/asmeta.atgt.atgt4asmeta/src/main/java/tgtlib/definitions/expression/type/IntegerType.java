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
package tgtlib.definitions.expression.type;

/**
 * An infinite Integer Tyep. It cannot be used with Model checkers of course, 
 * but sometimes is useful (whn using with Yices, for example
 * @author Angelo Gargantini
 */

public class IntegerType extends Type {

	/** standard Integer type*/
	public final static IntegerType INTEGER_TYPE = new IntegerType("Integer");
	
	/**
	 * Instantiates a Integer type.
	 * 
	 * @param _name
	 *            the _name
	 * @param _low
	 *            the _low
	 * @param _up
	 *            the _up
	 */
	public IntegerType(String _name) {
		super(_name);
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
	public <T> T accept(TypeVisitorI<T> ask) {
		return ask.forIntegerType(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString()+ "(Integer)";
	}

	@Override
	public int range() {
		throw new RuntimeException("TOO BIG!!!!");
	}
}
