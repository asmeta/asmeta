package tgtlib.generator.ordering;

import java.util.List;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSuite;
import tgtlib.specification.Specification;

/** builder of TPProcessor
 * 
 * @author garganti
 *
 * @param <T>
 * @param <S>
 */
public abstract class TPProcessorFactory<T extends  TestPredicate<S,?>, S extends TestSequence<T>> {

	abstract public <P extends Specification,Q extends TestSuite<T,S>> TPProcessor<T> getTPPRocessor(P spec, Q testsuite, List<T> candidates);
	
	//FIXME for static memeber parametric is difficult to implement
	static  public TPProcessorFactory chooseRndFactory = new ChooseRndFactory();
	
}

class ChooseRndFactory<T extends  TestPredicate<S,?>, S extends TestSequence<T>> extends TPProcessorFactory<T,S> {

	@Override
	public <P extends Specification, Q extends TestSuite<T, S>> TPProcessor<T> getTPPRocessor(P spec, Q testsuite, List<T> candidates) {
		return new ChooseRnd<T>(candidates);
	}

}