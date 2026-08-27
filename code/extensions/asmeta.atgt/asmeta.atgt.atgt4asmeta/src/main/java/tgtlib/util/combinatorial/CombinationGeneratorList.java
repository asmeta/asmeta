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
package tgtlib.util.combinatorial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * computes the list of combinations of the elements given in the list. Every
 * combination is a list of the original elements
 * 
 * @author garganti
 * 
 * @param <T>
 */
public class CombinationGeneratorList<T> implements Iterator<List<T>>, Iterable<List<T>> {

	private CombinationGenerator generator;

	private List<T> elements;

	/**
	 * the number of elements must be >= n and n >= 1 otherwise thorws an exception
	 */
	public CombinationGeneratorList(List<T> elements, int n) {
		generator = new CombinationGenerator(elements.size(), n);
		this.elements = elements;
	}

	@Override
	public List<T> next() {
		int[] nextComb = generator.next();
		List<T> result = new ArrayList<T>();
		for (int i : nextComb)
			result.add(elements.get(i));
		return result;
	}

	@Override
	public boolean hasNext() {
		return generator.hasNext();
	}

	@Override
	public void remove() {
		throw new RuntimeException("not supported");
	}

	@Override
	public Iterator<List<T>> iterator() {
		return this;
	}
}
