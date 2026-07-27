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

import java.util.ArrayList;
import java.util.List;

/**
 * A Finite Set Type. Allow to define a lower and an upper bound for the type.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini, Sergio Galati
 */

public class BoundType extends EnumerableType {

	/** Lower bound of interval. */
	private int low;

	/** Upper bound of interval. */
	private int up;

	/**
	 * Maximum value change: it can be null. if null or 1 means no delta
	 */
	protected Integer delta = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.type.Type#range()
	 */
	@Override
	public int range() {
		return this.up - this.low;
	}

	/**
	 * Instantiates a new bound type.
	 * 
	 * @param _name
	 *            the _name
	 * @param _low
	 *            the _low
	 * @param _up
	 *            the _up
	 */
	public BoundType(String _name, int _low, int _up) {
		super(_name);
		this.low = _low;
		this.up = _up;
	}

	/**
	 * Instantiates a new bound type.
	 * 
	 * @param _name
	 *            the _name
	 * @param _low
	 *            the _low
	 * @param _up
	 *            the _up
	 * @param _delta
	 *            the _delta
	 */
	public BoundType(String _name, int _low, int _up, Integer _delta) {
		this(_name, _low, _up);
		this.delta = _delta;
	}

	/**
	 * Returns the lower bound.
	 * 
	 * @return Lower bound
	 */
	public int getLow() {
		return this.low;
	}

	/**
	 * Returns the upper bound.
	 * 
	 * @return the up
	 */
	public int getUp() {
		return this.up;
	}

	/**
	 * Returns the delta: null if not defined: it means that any value in the domain is good
	 * 
	 * @return the delta
	 */
	public Integer getDelta() {
		return this.delta;
	}

	/**
	 * Set the delta.
	 * 
	 * @param delta
	 *            the _delta
	 */
	public void setDelta(int delta) {
		this.delta = delta;
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
		return ask.forBoundType(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "[" + low + "," + up + "]";
	}

	@Override
	public List<?> allElements() {
		List<Integer> elements = new ArrayList<Integer>();
		int step = delta==null?1:delta;
		for(int i = low; i <= up; i+=step) {
			elements.add(i);
		}
		return elements;
	}
}
