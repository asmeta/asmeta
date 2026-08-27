package extgt.coverage.mcdc;

import java.util.Iterator;
import java.util.List;

import tgtlib.util.Pair;

/** given a list of pairs, return an Iterable over all the base elements
 * 
 * @author garganti
 *
 * @param <T>
 */
public class IterableFromPairList<T> implements Iterable<T> {

	List<Pair<T, T>> originalList;

	public IterableFromPairList(List<Pair<T, T>> accept) {
		originalList = accept;
	}

	@Override
	public Iterator<T> iterator() {
		// return a new iterable, converts from pair to single elements
		return new Iterator<T>() {
			
			Iterator<Pair<T, T>> i = originalList.iterator();
			boolean nextIsFirstInPair = true;
			Pair<T, T> currentPair;

			@Override
			public boolean hasNext() {
				return (nextIsFirstInPair && i.hasNext()) || !nextIsFirstInPair;
			}

			@Override
			public T next() {
				if (nextIsFirstInPair) {
					nextIsFirstInPair = false;
					currentPair = i.next();
					return currentPair.getFirst();
				} else {
					nextIsFirstInPair = true;
					return currentPair.getSecond();
				}
			}

			@Override
			public void remove() {
				throw new RuntimeException("not implemented");
			}
		};
	}

	@Override
	public String toString() {
		return originalList.toString();
	}
}
