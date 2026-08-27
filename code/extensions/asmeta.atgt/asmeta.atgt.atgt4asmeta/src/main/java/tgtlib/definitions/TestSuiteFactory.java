package tgtlib.definitions;


/**
 * A factory for creating empty TestSuite objects.
 *
 * @param <PR> the generic type
 * @param <TS> the generic type
 */
//public interface TestSuiteFactory<PR extends TestPredicate<? extends TS,?>, TS extends TestSequence<? extends PR>>{
public interface TestSuiteFactory<T extends TestSuite<?,?>>{

	
	/** build an empty test suite */	
	//TestSuite<PR,TS> buildEmptyTestSuite();
	T buildEmptyTestSuite();

}
