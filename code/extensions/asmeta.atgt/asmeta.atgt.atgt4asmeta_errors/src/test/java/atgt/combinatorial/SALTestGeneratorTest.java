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

import org.apache.log4j.Logger;
import org.junit.Test;

import atgt.generator.SalTSuiteGenForTC;
import atgt.generator.testsuite.TestSuiteGeneratorForTC;
import atgt.project.AsmProject;

/**
 * test the generation of test for some specifications - Cruise control (without
 * constraints) - BBS with constraints
 * 
 * NOTE: experiments moved to another package and directory.
 */

public class SALTestGeneratorTest extends CombinatorialTestsGeneratorTest{

	/** Logger for this class. */
	protected static final Logger logger = Logger.getLogger(SALTestGeneratorTest.class);


	
	/**
	 * generate a test for a very simple test condition for the CC.
	 */
	@Test
	public void testATestConditionCC() {
		testCCwith(new SalTSuiteGenForTC(new AsmProject(CruiseControl, null)));
	}

	/**
	 * generate a test for a collected test condition for the CC.
	 */
	@Test
	public void testATestConditionCC_Collect() {
		TestSuiteGeneratorForTC stgen = new SalTSuiteGenForTC(new AsmProject(ThreePowerFour, null));
		super.testWithCollect(stgen);
	}

}
