///*******************************************************************************
// * Copyright (c) 2008 Angelo Gargantini.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// * 
// * Contributors:
// *     Angelo Gargantini - initial API and implementation
// ******************************************************************************/
//package atgt.combinatorial;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import atgt.coverage.AsmTestCondition;
//import atgt.coverage.AsmTestSequence;
//import atgt.coverage.AsmTestSuite;
//import atgt.coverage.CoveragesVisitorI;
//import atgt.generator.SalTSeqGenerator;
//import atgt.generator.SalTSuiteGenForTC;
//import atgt.parser.asmeta.AsmMLoaderTest;
//import atgt.project.AsmProject;
//import atgt.specification.ASMSpecification;
//import atgt.specification.location.Variable;
//import tgtlib.definitions.TestSequenceState;
//import tgtlib.definitions.TypedInitExpression;
//import tgtlib.definitions.expression.Expression;
//import tgtlib.reduction.TestSuiteRed;
//import tgtlib.util.IterableEnumeration;
//
//// TODO: Auto-generated Javadoc
///**
// * test the generation with a spec using the next operator in the constraints
// * esempio: cruisecontrol.asm
// */
//
//public class UseOfNextTest {
//
//	/** Logger for this class. */
//	private static final Logger logger = Logger.getLogger(UseOfNextTest.class);
//
//	/** The ccwa. */
//	static ASMSpecification ccwa; // con assiomi
//
//	/** The ccn ax. */
//	static ASMSpecification ccnAx;// senza assiomi: ne' next ne initial state
//									// (peò current state s�not , attenzione)
//
//	/**
//	 * Inits the.
//	 */
//	@BeforeClass
//	public static void init() {
//		ccwa = AsmMLoaderTest.cc_asmWithAxioms();
//		assertNotNull(ccwa);
//		// check the initial value of engRun
//		for (TypedInitExpression v : new IterableEnumeration<Variable>(ccwa.allVariables())) {
//			if (v.getName().equals("engRun")) {
//				assertEquals("false", v.getValue().toString());
//			}
//		}
//		// get cc without axioms
//		ccnAx = AsmMLoaderTest.loadSpec(atgt.parser.ParseSpecsAsmm.CC_ASM);
//		// reset the initial state
//		for (TypedInitExpression v : new IterableEnumeration<Variable>(ccnAx
//				.allVariables())) {
//			v.setValue(null);
//		}
//		// set the logger
//		Logger.getLogger(SalTSeqGenerator.class).setLevel(Level.ALL);
//	}
//
//	/**
//	 * generate a test for a very simple test condition.
//	 */
//	@Test
//	public void testATestConditionCC() {
//
//		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(true);
//
//		Expression exp = ToSALCombinatorialTest.makeEqualsExpression(ccnAx, "lever", "RESUME");
//
//		CoveragesVisitorI<AsmTestSuite> stgen = new SalTSuiteGenForTC(new AsmProject(ccnAx,null));
//
//		AsmTestCondition tc = new AsmTestCondition("cincative", exp);
//
//		AsmTestSuite result = stgen.forTestCondition(tc);
//		assertEquals(1, result.size());
//		AsmTestSequence test = result.iterator().next();
//		// assertEquals(3, test.allInstructions().size());
//		// Pair<String, String> assignement = new Pair<String, String>(var,
//		// val);
//		// assertTrue(test.allInstructions().get(2).contains(assignement));
//		System.out.println(test.toVideo());
//		System.out.println(test.allInstructions());
//	}
//
//	// test all the pairwise per Cruise control
//	/**
//	 * Test all piairwise cc.
//	 */
//	@Test
//	public void testAllPiairwiseCC() {
//		// set random, no collect, no antidiagonal
//		
//		atgt.preferences.ATGToolPreferences.TP_ORDERING.setValue(atgt.preferences.ATGToolPreferences.OrderKind.RANDOM);
//		atgt.preferences.ATGToolPreferences.CollectTPS.setChecked(false);
//		// /
//		testAll(true, "sal-bmc");
//		// testAll(false, "sal-bmc");
//		// testAll(true, "sal-smc");
//		// testAll(true, "sal-bmc");
//		/*
//		 * testAll(true,"sal-bmc"); testAll(true,"sal-bmc");
//		 * testAll(true,"sal-bmc");
//		 */
//	}
//
//	/**
//	 * Test all.
//	 * 
//	 * @param considerConstraints
//	 *            the consider constraints
//	 * @param SALPROGRAM
//	 *            the sALPROGRAM
//	 */
//	private void testAll(boolean considerConstraints, String SALPROGRAM) {
//		logger.info("consider constraints" + considerConstraints
//				+ " SALPROGRAM " + SALPROGRAM);
//		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(
//				considerConstraints);
//		ASMSpecification cc = considerConstraints ? ccwa : ccnAx;
//		printInfo(SALTestGenPairwiseTest.generateAll(cc));
//	}
//
//	// test all the test predicates for cruise control
//	@Test
//	public void testAllCC() {
//		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(
//				true);
//
//	}
//
//	/**
//	 * Prints the info.
//	 * 
//	 * @param result
//	 *            the result
//	 */
//	void printInfo(AsmTestSuite result) {
//		int reduced = new TestSuiteRed<AsmTestCondition,AsmTestSequence>().analyzeAsmTestSuite(result).size();
//		int totalTime = 0;
//		// get the time
//		logger.info("test suite found with " + result.size() + " reduced to "
//				+ reduced);
//		int sum = 0, sumU = 0;
//		StringBuffer states = new StringBuffer();
//		for (AsmTestSequence t : result) {
//			totalTime += t.time;
//			int size = t.allInstructions().size();
//			sum += size;
//			if (t.getState() == TestSequenceState.TEST_DISCARDED) {
//				states.append(" (" + size + ")");
//				continue;
//			}
//			sumU += size;
//			states.append(" " + size);
//		}
//		logger.info(states.toString() + " = " + sumU + "(" + sum + ") in "
//				+ totalTime);
//	}
//}
