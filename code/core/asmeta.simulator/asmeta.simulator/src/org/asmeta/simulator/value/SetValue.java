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
package org.asmeta.simulator.value;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * A set of values.
 * 
 */
public class SetValue<T> extends CollectionValue<T> {
	private Set<Value<T>> set;

	/**
	 * Creates a set.
	 * 
	 * @param set
	 *            a set of values
	 */
	public SetValue(Set<Value<T>> s) {
		this.set = s;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the set of values
	 */
	@Override
	public Set<Value<T>> getValue() {
		return set;
	}

	@Override
	public int hashCode() {
		return set.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SetValue) {
			return set.equals(((SetValue) o).getValue());
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		String[] elements = new String[set.size()];
		int i = 0;
		for (Value e : set) {
			elements[i++] = e.toString();
		}
		Arrays.sort(elements);
		if (elements.length > 0) {
			s.append(elements[0]);
			for (i = 1; i < elements.length; i++) {
				s.append("," + elements[i]);
			}
		}
		s.append("}");
		return s.toString();
	}

	@Override
	public Iterator<Value<T>> iterator() {
		return set.iterator();
	}

	// PA: 14 giugno 10
	@Override
	public Value clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
