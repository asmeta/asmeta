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
package tgtlib.generator.ordering;

import java.util.Iterator;
import java.util.List;

import tgtlib.definitions.TestPredicate;

/**
 * take the tp without collecting In this case, an iterator is enough;
 * */
public abstract class NoCollectOrderWMonitoring<T extends TestPredicate>
		extends TPProcessor<T> {

		
	// TODO : it should be this that takes care of skipping test predicates
	// already covered
	private boolean skipCovered;

	/** the collection of tps */
	protected List<T> candidates;

	/** the iterator used to get the tps */
	protected Iterator<T> candidatesIter;

	/** collection must be a list*/
	public NoCollectOrderWMonitoring(List<T> cand) {
		// link the collection (do not copy, otherwise the remove wont' work) 
		candidates = cand;
		// and setup the iterator (reorder, shuffle ....)
		setUpIterator();
	}

	/** set up the iterator in accordance with the policy chosen */
	abstract protected void setUpIterator();

	@Override
	public T next() {
		if (! candidatesIter.hasNext())
			return null;
		else
			return candidatesIter.next();
	}

	@Override
	public void remove() {		
		candidatesIter.remove();
	}

	@Override
	public void reset() {
		// set up again the iterator
		setUpIterator();
	}

}
