package atgt.coverage;

import tgtlib.definitions.TestSuite;

/**
 * 
 * @author garganti
 *
 */
public interface TestSuiteChangeObservable<TS extends TestSuite<?,?>> {
	
	public void addTestSuiteChangeListener(TestSuiteChangeListener l);

	/**
	 * Removes the test suite change listener.
	 * 
	 * @param l
	 *            the l
	 */
	public void removeTestSuiteChangeListener(TestSuiteChangeListener l);
	
	/**
	 * Fire test suite changed: status of the tests ...)
	 */
	public void fireTestsStatusChanged();

	/**
	 * Fire test suite changed (its size, status of the tests ...)
	 * 
	 */
	public void fireTestSuiteAddedChanged(TS tests);

}
