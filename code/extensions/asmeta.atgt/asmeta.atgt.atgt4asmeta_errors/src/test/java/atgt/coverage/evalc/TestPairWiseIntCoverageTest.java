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
package atgt.coverage.evalc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.combinatorial.TestCrossCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.Coverage;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.SalTSeqGenerator;
import atgt.generator.SalTSuiteGenForTC;
import atgt.generator.testsuite.AsmTestSuiteGenerator;
import atgt.generator.testsuite.TestSuiteGeneratorForTC;
import atgt.parser.ExampleLoader;
import atgt.parser.asmgofer.AsmExpressionParser;
import atgt.parser.asmgofer.ParseException;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import tgtlib.definitions.expression.Expression;


/**
 * test pairwise with integers.
 * TODO da spostare !! GLI INTERI VENGONO IGNORATI !!!!
 */
public class TestPairWiseIntCoverageTest {

	/** Logger for this class. */
	private static final Logger logger = Logger
			.getLogger(TestPairWiseIntCoverageTest.class);

	/** The SIS. */
	static ASMSpecification SIS;

	/**
	 * Load specs.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@BeforeClass
	public static void loadSpecs() throws ParseException, IOException {
		SIS = ExampleLoader.getSpec("sis.gs");
		//atgt.preferences.ATGToolPreferences.Integer.setChecked(true);
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(true);
		// use smc
		atgt.preferences.ATGToolPreferences.SAL_PROGRAM.setValue("sal-smc");
		// activate logger
		Logger.getLogger(AsmTestSuiteGenerator.class).setLevel(Level.ALL);
		Logger.getLogger(TestSuiteGeneratorForTC.class).setLevel(Level.ALL);
		Logger.getLogger(SalTSeqGenerator.class).setLevel(Level.ALL);
	}

	/**
	 * Test a int test condition.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testAIntTestCondition() throws ParseException {
		Expression e =  AsmExpressionParser.parse("waterPressure > 900");
		AsmTestCondition tc = new AsmTestCondition("wpgt900", e);
		tc.setToVerify(true);
		AsmTestSequence res = runForSomeTC(tc);
		// check coverage
		logger.debug("test found with " + res.allInstructions().size()
				+ " steps");
		assertEquals(TestConditionState.AssertViolated, tc.getStatus());
	}

	/**
	 * prende una test condition e controlla la copertura di un altro !!!.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testAIntTestCondition2() throws ParseException {
		//
		String tcS = "waterPressure > 900";
		tgtlib.definitions.expression.Expression e = AsmExpressionParser.parse(tcS);
		AsmTestCondition tc = new AsmTestCondition("wpgt900", e);
		tc.setToVerify(true);
		//
		String tcS2 = "pressure == Normal";
		tgtlib.definitions.expression.Expression e2 = AsmExpressionParser.parse(tcS2);
		AsmTestCondition tc2 = new AsmTestCondition("pressureNormal", e2);
		tc2.setToVerify(false);
		AsmTestSequence res = runForSomeTC(tc, tc2);
		// check coverage
		logger.debug("test found with " + res.allInstructions().size()
				+ " steps");
		logger.debug(res.toVideo());
		assertEquals(TestConditionState.AssertViolated, tc.getStatus());
	}

	/**
	 * run for a single test condition and check the coverage for others.
	 * 
	 * @param tcs
	 *            the tcs
	 * 
	 * @return the asm test sequence
	 */
	private AsmTestSequence runForSomeTC(AsmTestCondition... tcs) {
		AsmTestCondition toRun = null;
		Coverage cov = new Coverage("ROOT");
		for (AsmTestCondition tc : tcs) {
			cov.addTestCondition(tc);
			if (tc.getStatus() == TestConditionState.Queued)
				toRun = tc;
		}
		assertNotNull(toRun);
		AsmTestSuiteGenerator stgen = new SalTSuiteGenForTC(new AsmProject(SIS, cov));
		stgen.setSearchCommonCoverage(true);
		TestCrossCoverage.report(cov);
		AsmTestSequence res = stgen.forTestCondition(toRun).iterator().next();
		return res;

	}
}
