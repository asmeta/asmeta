package org.asmeta.simulator.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Provides a random iteration over the given list.
 * 
 * This effect can be achieved by using Collections.shuffle, which shuffles the
 * entire collection in linear time.
 * 
 * If the iteration process may end before all items are processed, this class
 * may give a speed increase because the shuffling process is performed as items
 * are requested rather than in the beginning.
 */
public class RandomIterator<E> implements Iterator<E> {

	/**
	 * Mapping indicating which items were served (by index). if served[i] then
	 * the item with index i in the list has already been served.
	 * 
	 * Note it is possible to save memory here by using BitSet rather than a
	 * boolean array, however it will increase the running time slightly.
	 */
	private final boolean[] served;

	/** The amount of items served so far */
	private int servedCount = 0;

	private final List<E> list;
	private final int LIST_SIZE;

	/**
	 * The random number generator has a great influence on the running time of
	 * this iterator.
	 * 
	 * See, for instance, http://www.qbrundage.com/michaelb/pubs/e� for some
	 * implementations, which are faster than java.util.Random.
	 */
	private final Random rand;

	/** Used to nar-row the range to take random indexes from */
	private int lower, upper;

	public RandomIterator(Collection<E> coll, Random rand) {
		if (coll instanceof List)
			this.list = (List<E>) coll;
		else 
			this.list = new ArrayList<E>(coll);
		this.rand = rand;
		LIST_SIZE = list.size();
		served = new boolean[LIST_SIZE];
		lower = 0;
		upper = LIST_SIZE - 1;
	}

	@Override
	public boolean hasNext() {
		return servedCount < LIST_SIZE;
	}

	private int index, range;

	@Override
	public E next() {

		range = upper - lower + 1;

		do {
			index = lower + rand.nextInt(range);
		} while (served[index]);

		// check if the range from which random values
		// are taken can be reduced
		if (index == lower)
			lower++;
		else if (index == upper)
			upper--;

		served[index] = true;
		servedCount++;

		return list.get(index);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}