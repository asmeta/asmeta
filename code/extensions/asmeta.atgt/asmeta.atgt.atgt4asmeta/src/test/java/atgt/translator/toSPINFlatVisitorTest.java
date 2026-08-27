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

package atgt.translator;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import atgt.coverage.AsmCoverageTree;
import atgt.parser.ExampleLoader;
import atgt.parser.ParseSpecsAsmm;
import atgt.parser.asmeta.AsmMLoaderTest;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;

/**
 * The Class toSPINFlatVisitorTest.
 * 
 * @author garganti
 */
@RunWith(Parameterized.class)
public class toSPINFlatVisitorTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { Boolean.TRUE },
				{ Boolean.FALSE } });
	}

	public toSPINFlatVisitorTest(boolean computeCoverage){
		
	}
	
	/**
	 * Test of forSpecification method, of class
	 * atgt.translator.toSPINFlatVisitor.
	 */
	@Test
	public void testSIS_asmSpecification() {

		ASMSpecification SP = atgt.parser.asmeta.AsmMLoaderTest
				.SISSpecification();

		toSPINFlatVisitor trans = new toSPINFlatVisitor();

		/* add coverages */
		atgt.coverage.AsmCoverageTree cvgs = (AsmCoverageTree) atgt.coverage.RootCoverage.ROOT
				.getTPTree(SP);
		trans.setCoverages(cvgs);
		trans.setSearchCommonCoverage(true);

		System.out.println(trans.analyze(SP).toString());

	}

	/**
	 * Test si s_gs specification.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException
	 */
	@Test
	public void testSIS_gsSpecification() throws ParseException, IOException {
		ASMSpecification spec = ExampleLoader.getSpec("sis.gs");
		assertNotNull(spec);
		toSPINFlatVisitor tr = new toSPINFlatVisitor();
		System.out.println(tr.analyze(spec));
	}

	/**
	 * Test c c_gs specification.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException
	 */
	@Test
	public void testCC_gsSpecification() throws ParseException, IOException {
		ASMSpecification spec = ExampleLoader.getSpec("cruiseControl.gs");
		assertNotNull(spec);
		toSPINFlatVisitor tr = new toSPINFlatVisitor();
		System.out.println(tr.analyze(spec));
	}

	/**
	 * Test c c_asm specification.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testCC_asmSpecification() throws FileNotFoundException,
			ParseException {
		ASMSpecification spec = AsmMLoaderTest.cc_asmWithAxioms();
		assertNotNull(spec);
		toSPINFlatVisitor tr = new toSPINFlatVisitor();
		System.out.println(tr.analyze(spec));
	}
	
	@Test
	public void testPunto() throws ParseException, IOException {
		Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		ASMSpecification s = ExampleLoader.getSpec("punto2DintParamsForSpin.asm");
		toSPINFlatVisitor tr = new toSPINFlatVisitor();
		System.out.println(tr.analyze(s));
	}

	@Test
	public void testChooseSpecification() {

		File derivedF = ParseSpecsAsmm.getFileSpec("fuzzyCounterChoose.asm");
		ASMSpecification SP = AsmMLoaderTest.loadSpec(derivedF);

		toSPINFlatVisitor trans = new toSPINFlatVisitor();

		System.out.println(trans.analyze(SP).toString());

	}

	

}
