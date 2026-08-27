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

import static atgt.preferences.ATGToolPreferences.CollectTPS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestCondition;
import atgt.generator.SALGenerationUtil;
import atgt.generator.SALGenerationUtil.TP_ORDERING;
import atgt.generator.SALGenerationUtil.WHICH_MC;
import atgt.generator.testsuite.TestGeneratorCollectTP;
import atgt.specification.ASMSpecification;

public class SALTestGenPairwiseTest extends SALTestGeneratorTest {

	@BeforeClass
	public static void setLogger() {
		Logger.getLogger(TestGeneratorCollectTP.class).setLevel(Level.ALL);
		logger.setLevel(Level.ALL);
	}

	/**
	 * Test generate some cc.
	 */
	@Test
	public void testGenerateSomeCC() {

		AsmCoverage ct = AsmCombCovBuilder
				.makePairwiseCovBuilder().getTPTree(CruiseControl);
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
	 * Test generate some bbs no collect.
	 */
	@Test
	public void testGenerateSomeBBSNoCollect() {
		CollectTPS.setChecked(false);
		testSomeBBS();
	}

	/**
	 * Test generate some bbs collect.
	 */
	@Test
	public void testGenerateSomeBBSCollect() {
		CollectTPS.setChecked(true);
		testSomeBBS();
	}

	/**
	 * Test some bbs.
	 */
	private void testSomeBBS() {
		AsmCoverage ct = AsmCombCovBuilder
				.makePairwiseCovBuilder().getTPTree(BBS);
		// select some
		int i = 0;
		for (TestCondition tc : ct.allTPs()) {
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
	public void testGenerateAllCC() {
		generateAll(CruiseControl);
	}

	/**
	 * test with a tc which is infeasible.
	 */
	@Test
	public void testGenerateUnfeasibleBBS() {
		assertTrue(BBS.getAxiom().size()>0);
		System.out.println(BBS.getAxiom().iterator().next().getBody().toString());
		String option1 = SALGenerationUtil.setParameters(false,
				TP_ORDERING.AS_GENERATED, false, false);
		option1 += SALGenerationUtil.setModelChecker(WHICH_MC.sal_bmc);
		AsmCoverage ct = AsmCombCovBuilder
				.makePairwiseCovBuilder().getTPTree(BBS);
		boolean found = false;
		// select all
		for (TestCondition tc : ct.allTPs()) {
			logger.debug("tc:" + tc.getCondition().toString());
			if (tc.getCondition().toString().equals(
					"(calltype = INTERNATIONAL) and (billing = COLLECT)")) {
				tc.setToVerify(true);
				found = true;
				break;
			}

		}
		assertTrue("tc not found",found);
		AsmTestSuite result = generate(ct, BBS);
		System.out.println(result.toString());
		assertTrue(result.isEmpty());
	}

	/**
	 * Test generate all bbs.
	 */
	@Test
	public void testGenerateAllBBS() {
		generateAll(BBS);
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
	protected static AsmTestSuite generateAll(ASMSpecification spec) {
		AsmCoverage ct = AsmCombCovBuilder
				.makePairwiseCovBuilder().getTPTree(spec);
		// select all
		for (TestCondition tc : ct.allTPs()) {
			tc.setToVerify(true);
		}
		AsmTestSuite result = generate(ct, spec);
		assertFalse(result.isEmpty());
		return result;
	}

}
