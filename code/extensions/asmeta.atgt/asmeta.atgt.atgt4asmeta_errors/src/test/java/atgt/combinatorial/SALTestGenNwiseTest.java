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
package atgt.combinatorial;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestCondition;
import atgt.specification.ASMSpecification;

public class SALTestGenNwiseTest extends SALTestGeneratorTest {

	@Before
	public void init() {
		atgt.preferences.ATGToolPreferences.CollectTPS.setChecked(
				false);

	}

	/**
	 * Test generate some cc.
	 */
	@Test
	public void testGenerateSomeCC_3() {

		AsmCoverage ct = AsmCombCovBuilder.createNWiseCovBuilder(3).getTPTree(CruiseControl);
		// select some
		int i = 0;
		for (TestCondition tc : ct.allTPs()) {
			tc.setToVerify(true);
			if (i++ > 5)
				break;
		}
		generate(ct, CruiseControl);
	}

	/**
	 * Test some bbs.
	 */
	@Test
	public void testSomeBBS_3() {

		AsmCoverage ct = AsmCombCovBuilder.createNWiseCovBuilder(3).getTPTree(BBS);
		// select some
		int i = 0;
		for (TestCondition tc :ct.allTPs()) {
			tc.setToVerify(true);
			if (i++ > 5)
				break;
		}
		generate(ct, BBS);
	}

	/**
	 * Test generate all cc.
	 */
	@Test
	public void testGenerateAllCC_3() {
		generateAll(CruiseControl, 3);
	}

	/**
	 * Test generate all bbs.
	 */
	@Test
	public void testGenerateAllBBS_3() {
		generateAll(BBS, 3);
	}

	/**
	 * generate with sal all the tps in the coverage tree PAIRWISE of the ASM
	 * SPEC - search for common coverage - skip already covered - others options
	 * depend on the caller.
	 * 
	 * @param spec
	 *            the spec
	 * 
	 * @return the asm test suite
	 */
	protected static AsmTestSuite generateAll(ASMSpecification spec, int n) {
		AsmCoverage ct = AsmCombCovBuilder.createNWiseCovBuilder(n).getTPTree(spec);
		// select all
		for (TestCondition tc : ct.allTPs()) {
			tc.setToVerify(true);
		}
		AsmTestSuite result = generate(ct, spec);
		assertFalse(result.isEmpty());
		return result;
	}

}
