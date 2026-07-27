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
package tgtlib.reduction;

import java.util.Collection;
import java.util.Comparator;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;

/**
 * compares two Test Sequences to see which one covers more "fresh"
 * TestConditions (do not consider the collection of test conditions given in
 * the constructor) which are already covered.
 * 
 * @author garganti
 */
public class CoversMore<Q extends TestPredicate<? extends P,?>, P extends TestSequence<? extends Q>>
		implements Comparator<P> {

	/** test condition already covers: do not consider them. */
	Collection<Q> alreadyCovered;

	/** test predicate to be covered: it can be null: ignore in that case */
	
	Collection<Q> toBeCovered;
	/**
	 * Instantiates a new covers more.
	 * 
	 * @param ctc
	 *            the alreadycovered test predicates
	 * @param toCover
	 * 			the test predicates to be covered (null = ignore)
	 */
	CoversMore(Collection<Q> ctc, Collection<Q> toCover) {
		alreadyCovered = ctc;
		toBeCovered = toCover;
	}

	/**
	 * Instantiates a new covers more.
	 * 
	 * @param ctc
	 *            the alreadycovered test predicates
	 */
	CoversMore(Collection<Q> ctc) {
		this(ctc,null);
	}

	/**
	 * returns > 0 if o1 covers more than o2.
	 * 
	 * @param o1
	 *            the o1
	 * @param o2
	 *            the o2
	 * 
	 * @return the int
	 */
	@Override
	public int compare(P o1, P o2) {
		int r1 = countCovered(o1, alreadyCovered,toBeCovered);
		int r2 = countCovered(o2, alreadyCovered,toBeCovered);
		return r1 - r2;
	}

	/**
	 * it counts the number of test condition covered by tr. This may differ
	 * from the number of tr.tpCovered() because it does not count the condition
	 * already covered
	 * 
	 * @param tr
	 *            : test sequence to evaluate
	 * @param alreadyCovered
	 *            the already covered
	 * 
	 * @return the number of test condition covered by tr, except the tc
	 *         alreadyCovered
	 */
	static <Q extends TestPredicate<? extends P,?>, P extends TestSequence<? extends Q>> int countCovered(P tr, Collection<Q> alreadyCovered, Collection<Q> tobeCovered) {
		int result = 0;
		for (Q tc : tr.tpCovered())
			if (!alreadyCovered.contains(tc) && (tobeCovered == null || tobeCovered.contains(tc)))
				result++;
		return result;
	}

}
