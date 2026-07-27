/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.generator;

import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.TestSuiteFactory;
import tgtlib.specification.Specification;

/**
 * generates a test suite. It does not take the project, so the project can use
 * this but not the other way around
 *
 * @param <S> the generic type is still used?
 * @param <T> the generic type
 * @param <C> the generic type
 * @author garganti
 */
public abstract class TestSuiteGenerator<S extends Specification, T extends TestSuite<?, ?>, C extends CoverageTree<?>> {

	/** the thread responsible for test generation
	 * (assuming to create a thread)
	 */
	TestGeneratorThread currentThread;	

	// to build new test suites
	protected TestSuiteFactory<T> tsfactory;

	
	/**
	 * the last results computed by the test generator
	 * 
	 */
	T results;

	// the coverage tree for which computing the test suite
	protected C coverage;
	
	// is this used?
	protected S specification;

	/**
	 * Instantiates a new test suite generator.
	 *
	 * @param spec the specification
	 * @param cov the coverage
	 * @param tsfactory the test suite factory
	 */
	protected TestSuiteGenerator(S spec, C cov, TestSuiteFactory<T> tsfactory) {
		coverage = cov;
		specification = spec;
		this.tsfactory = tsfactory;
	}

	/**
	 * start the generation: now only one process can run at one time entry
	 * point of the test generation. The call of this method is not blocking
	 * (but only one method can run) it can be interrupted and it does not block
	 * the current thread. Use getTunResults to get access to the last test suite generated.
	 * 
	 * @return
	 */
	public void generateTests() {
		if (currentThread != null && currentThread.isAlive()) {
			throw new TSGenException("wait until this finishes");
		}
		//
		results = tsfactory.buildEmptyTestSuite();
		//
		currentThread = new TestGeneratorThread();
		// TODO replace with a more advanced e.execute(r);
		currentThread.start(); // execute run
	}

	public T getRunResult() {
		return this.results;
	}

	/**
	 * the blocking counterpart. If called it runs the test generation until is
	 * finished or it is interrupted. 
	 * 
	 * @param cov
	 * @return
	 */
	public T generateTestsWait() {
		generateTests();
		try {
			currentThread.join();
		} catch (InterruptedException e) {
			assert currentThread.isInterrupted();
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this.results;
		}
		return results;
	}

	/**
	 * add the test to the test suite if interrupted the tests already generated
	 * are safely stored
	 * 
	 * @param cov
	 */
	protected abstract void addTestsForCoverage(C cov, T testSuite);

	/**
	 * check if the generation has finished
	 * 
	 * @return
	 */
	public boolean isAlive() {
		return currentThread.isAlive();
	}

	/**
	 * wait until the generation has finished
	 * 
	 * @throws InterruptedException
	 */
	public void join() throws InterruptedException {
		currentThread.join();
	}

	class TestGeneratorThread extends Thread {
		/**
		 * run the generation process do not use directly. put in an inner class
		 */
		@Override
		public void run() {
			addTestsForCoverage(coverage, results);
		}
	}
}