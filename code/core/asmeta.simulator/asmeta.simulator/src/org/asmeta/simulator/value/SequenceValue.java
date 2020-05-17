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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A sequence of values.
 *
 */
public class SequenceValue<T> extends CollectionValue<T> {
	
	private List<Value<T>> sequence;
	
	/**
	 * Copy constructor.
	 *  
	 */
	public SequenceValue(SequenceValue<T> sv) {
		sequence = new ArrayList<>(sv.sequence);
	}
	
	/**
	 * Creates a sequence.
	 * 
	 * @param sequence a list of values
	 */
	public SequenceValue(List<Value<T>> sequence) {
		this.sequence = sequence;
	}
	
    /**
     * Gets the value.
     * 
     * @return the list of values
     */
	@Override
	public List<Value<T>> getValue() {
		return sequence;
	}
		
	@Override
	public int hashCode() {
		return sequence.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof SequenceValue) {
			return sequence.equals(((SequenceValue) o).getValue());
		}
		throw new IllegalArgumentException();
	}
		
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("[");
		Iterator<Value<T>> i = sequence.iterator();
		if (i.hasNext()) {
			s.append(i.next());
			while (i.hasNext()) {
				s.append("," + i.next());
			}
		}
		s.append("]");
		return s.toString();
	}

	@Override
	public Iterator<Value<T>> iterator() {
		return sequence.iterator();
	}

	//PA: 14 giugno 10
	@Override
	public Value clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
