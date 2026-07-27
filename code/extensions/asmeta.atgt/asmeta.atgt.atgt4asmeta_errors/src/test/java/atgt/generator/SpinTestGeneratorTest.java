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

package atgt.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import atgt.combinatorial.ToSALCombinatorialTest;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.RootCoverage;
import atgt.coverage.TestCondition;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.testsuite.AsmTestSuiteGenerator;
import atgt.generator.testsuite.TestSuiteGeneratorForTC;
import atgt.parser.ExampleLoader;
import atgt.parser.ParseSpecsAsmm;
import atgt.parser.asmeta.AsmMLoaderTest;
import atgt.parser.asmgofer.ParseException;
import atgt.preferences.ATGToolPreferences;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.evalcoverage.TranslatorInputsToC;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.util.CmdExecutor;
import tgtlib.util.Pair;
import tgtlib.util.SimpleCmdExecutor;

/**
 * The Class SpinTestGeneratorTest.
 * 
 * @author garganti
 */

@RunWith(value = Parameterized.class)
public class SpinTestGeneratorTest {

	/** The tglogger. */
	TestGeneratorLogger tglogger = new TestGeneratorLogger();

	String cruiseControl = "cruiseControl";
	String cruise = "Cruise";


	public SpinTestGeneratorTest(Boolean safety, Boolean bitstate){
		// check -DSAFETY
		ATGToolPreferences.SAFETY.setChecked(safety);
		ATGToolPreferences.BITSTATE.setChecked(bitstate);				
	}

	/**
	 * set the preferences in a consistent way.
	 */
	@BeforeClass
	static public void setPref() {
		ATGToolPreferences.COLLAPSE.setChecked(false);
		ATGToolPreferences.BITSTATE.setChecked(false);
		//ATGToolPreferences.TIMEOUT.setChecked(true);
		//ATGToolPreferences.TIMEOUT.setValue("10");
	}

	
	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Boolean[][] { 
			   // safety - bitstate
			   { true,true },
			   { true,false },
			   { false,true },
			   { false,false }};
	   return Arrays.asList(data);
	 }
	
	
	@BeforeClass
	public static void activateLogging(){
		// activate assert
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
		Logger.getLogger(SpinTSeqGenerator.class).setLevel(Level.DEBUG);		
		Logger.getLogger(CmdExecutor.class).setLevel(Level.DEBUG);
		Logger.getLogger(SimpleCmdExecutor.class).setLevel(Level.OFF);
		Logger.getLogger(TestSuiteGeneratorForTC.class).setLevel(Level.ALL);
		Logger.getLogger(AsmTestSuiteGenerator.class).setLevel(Level.ALL);		
		Logger.getLogger(TestCondition.class).setLevel(Level.ALL);
		Logger.getLogger(TranslatorInputsToC.class).setLevel(Level.ALL);
	}
	
	/**
	 * generates for sp.
	 * 
	 * @param sp
	 *            the sp
	 * @return 
	 */
	static AsmTestSuite testCoverages(ASMSpecification sp) {

		AsmCoverageTree cvgs = (AsmCoverageTree) RootCoverage.ROOT.getTPTree(sp);
		SpinTSuiteGenForTC sptg = SpinTSuiteGenForTC
				.createFlatSpinTSuiteGenForTC(new AsmProject(sp, cvgs));
		sptg.setSearchCommonCoverage(true);
		// set some test cases to run
		int i = 0;
		for (TestCondition tc : cvgs.allTPs()) {
			tc.setToVerify(true);
			if (i++ > 10)
				break;
		}
		AsmTestSuite result = sptg.forCoverageTree(cvgs);
		assertTrue(result.size() <= 10);
		assertTrue(result.size() >= 1);
		return result;
	}

	/**
	 * Test for a test condition di prova generata ad hoc
	 */
	@Test
	public void testForTCProva_CC() {
		Pair<AsmTestSuite, AsmTestCondition> r = runCCForATC();
		AsmTestSuite result = r.getFirst();
		// the test suite is not empty
		assertEquals(1, result.size());
		// the test is not empty (should be four?)
		AsmTestSequence ts = result.iterator().next();
		int size = ts.allInstructions().size();
		assertTrue(size > 1);
		TestCondition tc = r.getSecond();
		assertTrue(tc.getStatus() == TestConditionState.AssertViolated);
		// the last state contains the instruction
		// note that a test contains also the last updates,
		// so the last state is the prelast state
		Map<Location, String> lastState = ts.allInstructions().get(size - 2);
		assertEquals(cruise,SALGenerationUtil.getValue(cruiseControl,lastState));
		// the coverage is not computed since the tp is not part of the coverage
		// tree
	}

	/**
	 * Test for a test condition that seems problematic
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Test
	public void testForTCCounter1() throws ParseException, IOException {
		ASMSpecification cc = ExampleLoader.getSpec("counter.asm");
		AsmProject result = generateFirstTC(cc);
		AsmTestSuite ts = result.getAsmTestSuite();
		assertEquals(1, ts.size());
	}

	
	@Test
	public void testForTC_CC_ASM() {
		ASMSpecification cc = atgt.parser.asmeta.AsmMLoaderTest.cc_asmWithAxioms();
		AsmCoverageTree cvgs = (AsmCoverageTree) RootCoverage.ROOT.getTPTree(cc);
		SpinTSuiteGenForTC sptg = SpinTSuiteGenForTC
				.createFlatSpinTSuiteGenForTC(new AsmProject(cc, cvgs));
		sptg.setSearchCommonCoverage(true);
		sptg.addTestListener(tglogger);
		TestCondition found = null;
		// queque a test condition in the tree
		for (TestCondition tc : cvgs.allTPs()) {
			if (tc.getName().equals("BR_r_CruiseControl_FFT2")) {
				tc.setToVerify(true);
				found = tc;
				System.out.println("queqed " + tc.getName());
				break;
			}
		}
		assertNotNull(found);
		AsmTestSuite result = sptg.forCoverageTree(cvgs);
		assertEquals(1, result.size());
		// the test suite is not empty
		assertEquals(1, result.size());
		// the test is not empty (should be four?)
		AsmTestSequence ts = result.iterator().next();
		int size = ts.allInstructions().size();
		assertTrue(size > 1);
		assertTrue(found.getStatus() == TestConditionState.AssertViolated);
		// the last state contains the instruction
		Map<Location, String> lastState = ts.allInstructions().get(size - 1);
		assertEquals(SALGenerationUtil.getValue("CRUISE",lastState), lastState.get("mode"));
		// the coverage is not computed since the tp is not part of the coverage
		// tree
		// check that ts covers tc
		assertTrue(ts.tpCovered().toString(),ts.tpCovered().contains(found));
		assertTrue(found.allCoveredBy().toString(),found.allCoveredBy().contains(ts));
	}

	/**
	 * Test for coverages cc.
	 */
	@Test
	public void testForCoveragesCC() {
		// cruise control
		ASMSpecification cc = atgt.parser.asmgofer.ASMParserTest.getCruiseControlNoAxiom();
		testCoverages(cc);
	}

	/**
	 * Test for coverages sis.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testForCoveragesSIS() throws ParseException, IOException {
		// sis.gs
		ASMSpecification sis = atgt.parser.ExampleLoader.getSpec("sis.gs");
		AsmTestSuite result = testCoverages(sis);
		assertTrue(result.size() > 1);
	}

	@Test
	public void testForCoveragesStereoAcuity() {
		File stereoacuity = ParseSpecsAsmm.getFileSpec("stereoacuity/certifier3CHECK_6.asm");
		ASMSpecification stereoacuityASM = AsmMLoaderTest.loadSpec(stereoacuity);
		testCoverages(stereoacuityASM);
	}

	
	
	/**
	 * test that is TC is quequed and it is not generate d(spin was not found)
	 * its state becomes uknown
	 */
	@Test
	public void testTCStatusAfterFailure() {
		// set spin to a wrong value
		// 
		String spinProgramBak = atgt.preferences.ATGToolPreferences.SPIN_PROGRAM.getValue();
		try{
			atgt.preferences.ATGToolPreferences.SPIN_PROGRAM.setValue("spinxxx");
			Pair<AsmTestSuite, AsmTestCondition> r = runCCForATC();
			AsmTestSuite result = r.getFirst();
			// the test suite is empty
			assertEquals(0, result.size());
			TestCondition tc = r.getSecond();
			assertEquals(TestConditionState.Unknown, tc.getStatus());
		} catch(Exception e){
			e.printStackTrace();
			assertTrue(e.getClass().toString(), e instanceof ModelCheckerExecutionException);
		} finally{
			// 	ripristina
			atgt.preferences.ATGToolPreferences.SPIN_PROGRAM.setValue(spinProgramBak);
		}
	}

	private Pair<AsmTestSuite, AsmTestCondition> runCCForATC() {
		ASMSpecification cc = atgt.parser.asmgofer.ASMParserTest.getCruiseControlNoAxiom();
		AsmCoverageTree cvgs = (AsmCoverageTree) RootCoverage.ROOT.getTPTree(cc);
		SpinTSuiteGenForTC sptg = SpinTSuiteGenForTC
				.createFlatSpinTSuiteGenForTC(new AsmProject(cc, cvgs));
		sptg.setSearchCommonCoverage(false); // inutile perchè è di prova
		sptg.addTestListener(tglogger);
		EqualsExpression tp = ToSALCombinatorialTest.makeEqualsExpression(cc, cruiseControl, cruise);
		AsmTestCondition tc = new AsmTestCondition("tc di prova", tp);
		AsmTestSuite result = sptg.forTestCondition(tc);
		return new Pair<AsmTestSuite, AsmTestCondition>(result, tc);
	}
	
	@Test
	public void testGenerateTestsAndWaitCC(){
		ASMSpecification cc = atgt.parser.asmgofer.ASMParserTest.getCruiseControlNoAxiom();
		AsmProject pro = generateFirstTC(cc);
		assertEquals(1, pro.getTestSuite().size());		
		//				
	}
	
	private AsmProject generateFirstTC(ASMSpecification cc){
		AsmCoverageTree cvgs = (AsmCoverageTree) RootCoverage.ROOT.getTPTree(cc);
		AsmProject pro = new AsmProject(cc, cvgs);
		SpinTSuiteGenForTC sptg = SpinTSuiteGenForTC.createFlatSpinTSuiteGenForTC(pro);
		// set teh coverage
		sptg.setSearchCommonCoverage(true);
		// 	select the first one
		TestCondition tc = cvgs.allTPs().iterator().next();
		tc.setToVerify(true);
		TestSuite<?, ?> result = sptg.generateTestsWait();
		pro.addAsmTestSuite((AsmTestSuite) result);
		assert tc.getStatus() == TestConditionState.AssertViolated;
		assert result.getTests().size() == 1;
		return pro;
	}


	
}
