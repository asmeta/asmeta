/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.reduction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSuite;

/**
 * The Class TestSuiteRed.to perform the reduction of a test suite
 * 
 */
public class TestSuiteRed<Q extends TestPredicate<? extends P,?>, P extends TestSequence<? extends Q>> {

	/** The log. */
	static final Logger log = Logger.getLogger(TestSuiteRed.class);
	
	
	public TestSuiteRed(){
		
	}

	/**
	 * reduce the test suite by the optimization algorithm
	 * 
	 * @return true if the reduction has actually reduce the test suite
	 */
	public static <PR extends TestPredicate<? extends TS,?>, TS extends TestSequence<? extends PR>> boolean reduce(TestSuite<PR, TS> testSuite) {
		int originalSize = testSuite.size();
		int necessary = new TestSuiteRed<PR, TS>().analyzeAsmTestSuite(testSuite).size();
		if (necessary < originalSize) {
			return true;
		} else {
			return false;
		}
	}

	Collection<P> getSufficient(Collection<P> original) {
		return getSufficient(original, null);
	}

	/**
	 * Given a set of test sequences, finds the set of sufficient tests to get
	 * the same set of covered test conditions. All the test condition covered 
	 * by original are considered
	 * 
	 * @param original :
	 *            the starting point of tests
	 * @param coverage 
	 * 	null: evaluate against all the test predicates.
	 * @return the reduced collection
	 */
	Collection<P> getSufficient(Collection<P> original, Collection<Q> coverage) {

		// copy the original set of TestSequence
		// toEvaluate contains the TS still to be evaluated
		Collection<P> toEvaluate = new HashSet<P>(original);

		// S initial set is empty: Test Conditions already covered
		Collection<Q> alreadyCovered = new HashSet<Q>();

		// final result: initially empty
		Collection<P> result = new ArrayList<P>();

		while (!toEvaluate.isEmpty()) {
			// take the first (which covers most tc not already covered)
			P toAdd = coversMost(toEvaluate, alreadyCovered,coverage );
			log.debug("found the ts which covers more: " + toAdd.toString());
			// check if it is really necessary
			if (CoversMore.countCovered(toAdd, alreadyCovered,coverage) == 0) {
				log.debug("actually it covers 0 test conditions: finish");
				break;
			}
			// move the test from toEvaluate to result
			result.add(toAdd);
			toEvaluate.remove(toAdd);
			// add tc covered by this ts to S
			for (Q i : toAdd.tpCovered()) {
				if (!alreadyCovered.contains(i)) {
					alreadyCovered.add(i);
					log.debug("covers tp : " + i.toString());
				}
			}
		}
		return result;
	}
	
	/**
	 * assume that among has at least one element.
	 * 
	 * @param among :
	 *            TestSuite in which search the most covering test
	 * @param covered
	 *            the test already covered covered
	 * @param coverage 
	 * 
	 * @return the test sequence (P) which covers more tp
	 */
	private P coversMost(Collection<P> among, Collection<Q> covered, Collection<Q> coverage) {
		assert (!among.isEmpty());
		return Collections.max(among, new CoversMore<Q, P>(covered,coverage));
	}

	/**
	 * analyze a set of test sequences.
	 * 
	 * @param tests
	 *            the tests originally in the test suite
	 * 
	 * @return the collection of really necessary tests
	 */
	public Collection<P> analyzeAsmTestSuite(TestSuite<Q,P> tests) {
		return analyzeAsmTestSuite(tests,null);
	}

	/**
	 * Analyze asm test suite.
	 *
	 * @param tests the tests to be analyzed
	 * @param coverage the coverage wanted (evaluate the tests against this: if null, than consider the whole tree)
	 * @return the collection
	 */
	public Collection<P> analyzeAsmTestSuite(TestSuite<Q,P> tests, Collection<Q> coverage) {
		log.debug("analyzing test suite with " + tests.size() + " tests");
		log.debug("against "+ (coverage == null? "the whole ": " partial ") + " coverage ");
		// gather all the tests
		List<P> testsFound = new ArrayList<P>();
		for(P test: tests) testsFound.add(test);
		// get only those necessary
		Collection<P> necessary = getSufficient(testsFound, coverage);
		log.debug("found necessary " + necessary.size() + " over "	+ testsFound.size());
		// set test conditions state
		for (P ts : tests) {
			if (!necessary.contains(ts)) {
				log.debug("discard " + ts.toString());
				ts.discardTest();
				// ts.fireTestSequenceStateChanged();
			} else{
				log.debug("necessary test :"+ ts.toString());
			}
		}
		return necessary;
	}
	
	/**
	 * check the minimality of the collection of tests containing both
	 * assertVilated (retain the test), and useless (discarded) DA COMPLETARE:
	 * molto difficile da scrivere !!!.
	 * 
	 * @param tests
	 *            the tests
	 * 
	 * @return true, if check minimality
	 */
	private boolean checkMinimality(TestSuite<Q,P> tests) {
		// scan tests list
		for (tgtlib.definitions.TestSequence<? extends TestPredicate> tr : tests) {
			//
			// 1. quelle necessario sono realmente necessarie !!!
			// not all the test condition covered by tc are covered by another
			// test result
			boolean foundNecessary = false;
			// exists c : condition covered by tc such that
			// all test results covering c are
			for (TestPredicate covered : tr.tpCovered()) {
				// covered is a tc covered by the test result of tc
				// for (TestSequence tr2 : covered.allCoveredBy()) {
				// tr2 covers covered
				// if tr2 is not in the set, then covered is ok
				// if (tr2.getGeneratedForTC().getStatus() !=
				// TestConditionState.AssertViolated) {
				// foundNecessary = true;
				// break;
				// }
				// }
			}
			if (!foundNecessary) {
				log.info("every tc covered by " + tr.toString());
				log.info(" is also covered by others ");
				return false;
			}
		}
		return true;
	}
}
