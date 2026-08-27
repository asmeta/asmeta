package atgt.generator.collection;

import tgtlib.definitions.TestPredicate;
import tgtlib.generator.ordering.TPProcessor;

/** standard collection iterator, just take the next in the processor
 * 
 * @author garganti
 *
 * @param <T>
 * @param <R>
 */
public class StandardCollectionIterator<T extends TestPredicate<?,?>, R extends CollectedTestCondition> extends CollectionIterator<T, R>{

	/**
	 * the tp processor collector uses this auxiliary tp processor which can be a list
	 * (natural order) or whatever
	 */
	protected TPProcessor<T> testCondsSeq;

	public StandardCollectionIterator(TPProcessor<T> testCons){
		testCondsSeq = testCons;
	}

	@Override
	public T getNextCandidate(R collect) {
		T next = testCondsSeq.next();
		//assert next == null || next.getStatus() == TestConditionState.Queued;
		return next;		
	}

	@Override
	public void remove() {
		testCondsSeq.remove();		
	}

	@Override
	public void reset() {
		testCondsSeq.reset();
	}
}