/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package tgtlib.reduction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import tgtlib.generator.TestPredicate4Test;

import org.junit.jupiter.api.Test;
import tgtlib.generator.TestSequence4Test;

/**
 */
class TestSuiteRedTest extends ReductionTest{

///////////////////////////////// WITHOUT coverage tree	
	/**
	 * overlapping with one useless.
	 */
	@Test void overLap() {
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(tr1);
		start.add(tr2);
		start.add(tr3);
		assertEquals(2, new TestSuiteRed<TestPredicate4Test, TestSequence4Test>().getSufficient(start).size());
	}

	/**
	 * overlapping all useful.
	 */
	@Test void overLapUseful() {
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(tr1);
		start.add(tr2);
		Collection actual = new TestSuiteRed<TestPredicate4Test, TestSequence4Test>()
				.getSufficient(start);
		assertEquals(2, actual.size());
		assertTrue(actual.contains(tr1));
		assertTrue(actual.contains(tr2));
	}

	/**
	 * overlapping all useful.
	 */
	@Test void subSume() {
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(tr1);
		start.add(tr4);
		/*assertArrayEquals(new TestSuiteRed<TestPredicate4Test, TestSequence4Test>()
				.getSufficient(start).toArray(), Collections.singleton(tr1)
				.toArray());*/
	}

	/**
	 * alla independents.
	 */
	@Test void indep() {
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(tr2);
		start.add(tr4);
		TestSuiteRed<TestPredicate4Test, TestSequence4Test> red = new TestSuiteRed<TestPredicate4Test, TestSequence4Test>();
		Collection x = red.getSufficient(start);
		System.out.println(x.size());
		//assertArrayEquals(x.toArray(), start.toArray());
	}

	/**
	 * one test condition has not a test result.
	 */
	@Test void oneNotInTR() {
		// test condition withou test result
		TestPredicate4Test tcx = new TestPredicate4Test("tcx",null);
		// trx1 -> tc1 e tc2
		TestSequence4Test trx1 = getTS(tc1,tc2);
		// trx2 -> tc1 e tcx
		TestSequence4Test trx2 = getTS(tcx,tc1);
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(trx1);
		start.add(trx2);
		Collection<TestSequence4Test> res = new TestSuiteRed<TestPredicate4Test, TestSequence4Test>()
				.getSufficient(start);
		assertEquals(2, res.size());

	}

/////////////////// WITH TO BE COVERED
	/**
	 * with only one that covers the only one wanted.
	 */
	@Test void onlyOne() {
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(tr1);
		start.add(tr2);
		Collection<TestPredicate4Test> tobeCovered = new ArrayList<TestPredicate4Test>();
		tobeCovered.add(tc1);
		Collection<TestSequence4Test> necs = new TestSuiteRed<TestPredicate4Test, TestSequence4Test>().getSufficient(start,tobeCovered);
		assertEquals(1, necs.size());
		assertTrue(necs.contains(tr1));
		assertFalse(necs.contains(tr2));
	}

	/**
	 * with only one that covers the only one wanted.
	 */
	@Test void againOne() {
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(tr1);
		start.add(tr2);
		start.add(tr3);
		Collection<TestPredicate4Test> tobeCovered = new ArrayList<TestPredicate4Test>();
		tobeCovered.add(tc1);
		tobeCovered.add(tc2);
		Collection<TestSequence4Test> necs = new TestSuiteRed<TestPredicate4Test, TestSequence4Test>().getSufficient(start,tobeCovered);
		assertEquals(1, necs.size());
		assertTrue(necs.contains(tr1));
		assertFalse(necs.contains(tr2));
		assertFalse(necs.contains(tr3));
	}

	/**
	 * with only one that covers the only one wanted.
	 */
	@Test void two() {
		Collection<TestSequence4Test> start = new ArrayList<TestSequence4Test>();
		start.add(tr4);
		start.add(tr2);
		Collection<TestPredicate4Test> tobeCovered = new ArrayList<TestPredicate4Test>();
		tobeCovered.add(tc1);
		tobeCovered.add(tc2);
		Collection<TestSequence4Test> necs = new TestSuiteRed<TestPredicate4Test, TestSequence4Test>().getSufficient(start,tobeCovered);
		assertEquals(2, necs.size());
		assertTrue(necs.contains(tr4));
		assertTrue(necs.contains(tr2));
	}
	
}
