/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.coverage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.TestSuiteFactory;

/**
 * a collection of test sequences (AsmTestSequence), related to a TestCondition 
 * 
 */
public class AsmTestSuite extends TestSuite<AsmTestCondition,AsmTestSequence> implements TestSuiteChangeObservable<AsmTestSuite>{

	
	/** number of actual test cases: an AsmTestSequence may not be a real test case in case of run with unfeasible test predicates
	 *  
	 */
	private int nActualTests = 0;
	
	
	public static TestSuiteFactory<AsmTestSuite> getAsmTestSuiteFactory() {
		return new TestSuiteFactory<AsmTestSuite>() {

			@Override
			public AsmTestSuite buildEmptyTestSuite() {
				return new AsmTestSuite();
			}
		}; 
	}
	
	
	
	/**
	 * Gets the empty test suite.
	 * 
	 * @return an empty test suite it is intended not to be modified (no add
	 *         elements to it), otherwise use the constructor TODO redefine add
	 *         method for safety .... here because not able to return the same
	 *         for generic classes
	 */
	// ATTENZIONE: se ho una sottoclasse come faccio a restituire quello
	// vuoto???
	public static AsmTestSuite getEmptyTestSuite() {
		return EMPTY_TEST_SUITE;// instance;
	}

	/** The EMPTY test suite in which cannot be added test sequences */
	private final static AsmTestSuite EMPTY_TEST_SUITE = new AsmTestSuite() {
		@Override
		public boolean addTest(AsmTestSequence e) {
			throw new RuntimeException(
					" adding to an empty test suite not allowed !!!");
		}
	};

	/** complete description of the test suite, including the content
	 * 
	 */
	@Override
	public String toString(){
		StringBuffer s = new StringBuffer();
		for (AsmTestSequence seq: this.content) 
			s.append(seq.toVideo()).append("\n");
		return s.toString(); 
	}
	
	/**
	 * Singleton.
	 * 
	 * @param ts
	 *            the ts
	 * 
	 * @return the asm test suite
	 */
	public static AsmTestSuite singleton(AsmTestSequence ts) {
		AsmTestSuite result = new AsmTestSuite();
		// call the add method and not the addTest, no need to fire events
		result.content.add(ts);
		return result;
	}
	// this part regards the listener to changes at the AsmTestSuite:
	// the state can change or the status of TPs,
	// it is here and not in the project so a listener does not need the entire proejct
	// and the generator can add to a AsmTestSuite and the lister are notified
	
	/** The listeners. */
	protected transient List<TestSuiteChangeListener> listeners = new ArrayList<TestSuiteChangeListener>();

	/**
	 * Adds a property-change listener.
	 * 
	 * @param l
	 *            the listener
	 */
	@Override
	public void addTestSuiteChangeListener(TestSuiteChangeListener l) {
		if (l == null) {
			throw new IllegalArgumentException();
		}
		this.listeners.add(l);
	}

	/**
	 * Removes the test suite change listener.
	 * 
	 * @param l
	 *            the l
	 */
	@Override
	public void removeTestSuiteChangeListener(TestSuiteChangeListener l) {
		this.listeners.remove(l);
	}

	/**
	 * Fire test suite changed: status of the tests ...)
	 */
	@Override
	public void fireTestsStatusChanged() {
		for (TestSuiteChangeListener testList : listeners) {
			testList.testSuiteChange(new TestsStatusChangeEvent(this));
		}
	}

	/**
	 * Fire test suite changed (its size, status of the tests ...)
	 * 
	 */
	@Override
	public void fireTestSuiteAddedChanged(AsmTestSuite tests) {
		for (TestSuiteChangeListener testList : listeners) {
			testList.testSuiteChange(new AddTestsEvent(this,tests));
		}
	}
	

	// overwrite add methods to fire the events
	/** add a test */
	@Override
	public boolean addTest(AsmTestSequence ts) {
		boolean addResult = addSingleTest(ts);
		fireTestSuiteAddedChanged(singleton(ts));		
		return addResult;
	}

	/** add an entire test suite */
	@Override
	public boolean addAllTest(TestSuite<AsmTestCondition,AsmTestSequence> ts) {		
		AsmTestSuite ats = (AsmTestSuite)ts;
		boolean addAllResult = true;
		for(AsmTestSequence t: ats.content){
			addAllResult &= addSingleTest(t);
		}
		fireTestSuiteAddedChanged(ats);
		return addAllResult;
	}

	/** add a single test and account if it is a real test !*/
	private boolean addSingleTest(AsmTestSequence t){
		boolean addResult = this.content.add(t);
		List<Map<Location, String>> actualTest = t.getContent().allInstructions();
		// if the test has the first state and the first state in not empty
		if (actualTest.size()>0 && actualTest.get(0).size() > 0 ) nActualTests++;
		return addResult;
	}
	
	public boolean isEmpty() {
		return content.isEmpty();
	}
	
	public int getNActualTest(){
		return nActualTests;
	}

}



