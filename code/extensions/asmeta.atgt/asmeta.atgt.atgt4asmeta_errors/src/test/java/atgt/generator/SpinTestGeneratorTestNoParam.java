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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestCondition;
import atgt.generator.testsuite.AsmTestSuiteGenerator;
import atgt.generator.testsuite.TestSuiteGeneratorForTC;
import atgt.parser.ParseSpecsAsmm;
import atgt.parser.asmeta.AsmMLoaderTest;
import atgt.parser.asmgofer.ParseException;
import atgt.preferences.ATGToolPreferences;
import atgt.specification.ASMSpecification;
import tgtlib.evalcoverage.TranslatorInputsToC;
import tgtlib.util.CmdExecutor;
import tgtlib.util.SimpleCmdExecutor;

/**
 * The Class SpinTestGeneratorTest.
 * 
 * @author garganti
 */

public class SpinTestGeneratorTestNoParam {

	/** The tglogger. */
	TestGeneratorLogger tglogger = new TestGeneratorLogger();


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
	 * Test for coverages sis.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testForCoveragesChooseRule() throws ParseException, IOException {
		// sis.gs
		ASMSpecification sis = atgt.parser.ExampleLoader.getSpec("chooserule.asm");
		AsmTestSuite result = SpinTestGeneratorTest.testCoverages(sis);
		assertTrue(result.size() > 1);
	}	
	
	@Test
	public void testForCoveragesStereoAcuity() {
		File stereoacuity = ParseSpecsAsmm.getFileSpec("stereoacuity/certifier3CHECK_6.asm");
		ASMSpecification stereoacuityASM = AsmMLoaderTest.loadSpec(stereoacuity);
		SpinTestGeneratorTest.testCoverages(stereoacuityASM);
	}

	@Test
	public void testForCoveragesStereoAcuityNc() {
		File stereoacuity = ParseSpecsAsmm.getFileSpec("stereoacuity/certifier_nochoose.asm");
		ASMSpecification stereoacuityASM = AsmMLoaderTest.loadSpec(stereoacuity);
		SpinTestGeneratorTest.testCoverages(stereoacuityASM);
	}

	@Test
	public void testForCarSystem2019() {
		File stereoacuity = ParseSpecsAsmm.getFileSpec("D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem001.asm");
		ASMSpecification stereoacuityASM = AsmMLoaderTest.loadSpec(stereoacuity);
		SpinTestGeneratorTest.testCoverages(stereoacuityASM);
	}
	
	
}
