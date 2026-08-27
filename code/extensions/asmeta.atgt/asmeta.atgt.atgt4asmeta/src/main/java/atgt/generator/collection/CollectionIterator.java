package atgt.generator.collection;

import tgtlib.definitions.TestPredicate;

/** return the next Test predicate in the collection to be considered as candidate for collection
 * 
 * @author garganti
 *
 */
public abstract class CollectionIterator<T extends TestPredicate<?,?>, R extends CollectedTestCondition>  {

	/** find the next candidate to be collected
	 * @param collect: the candidate to go into collect
	 * the candidate could also not be correct (to be later checked)
	 * it cannot be already covered, it must be "queued" 
	 * 
	 * @return null if finished (no more candidates for collect)
	 */
	public abstract T getNextCandidate(R collect);

	/** remove the current element*/
	public abstract void remove();

	/** restart*/
	public abstract void reset();

}

