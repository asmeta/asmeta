package atgt.generator.testsuite.ordering;

import atgt.coverage.TestCondition;

public interface TPCollectedChangeListener<T extends TestCondition> {

	/** a TP has been added 
	 * @param tc 
	 */
	void TPAdded(T tc);
	
	
	
}
