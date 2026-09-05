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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverageTree;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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
public class toSPINFlatVisitorTest {

	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { Boolean.TRUE },
				{ Boolean.FALSE } });
	}

	public void inittoSPINFlatVisitorTest(boolean computeCoverage){
		
	}

	/**
	 * Test of forSpecification method, of class
	 * atgt.translator.toSPINFlatVisitor.
	 */
	@MethodSource("data") @ParameterizedTest
	public void testSIS_asmSpecification(boolean computeCoverage) {

		inittoSPINFlatVisitorTest(computeCoverage);

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
	@MethodSource("data") @ParameterizedTest
	public void testSIS_gsSpecification(boolean computeCoverage) throws Exception {
		inittoSPINFlatVisitorTest(computeCoverage);
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
	@MethodSource("data") @ParameterizedTest
	public void testCC_gsSpecification(boolean computeCoverage) throws Exception {
		inittoSPINFlatVisitorTest(computeCoverage);
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
	@MethodSource("data") @ParameterizedTest
	public void testCC_asmSpecification(boolean computeCoverage) throws Exception {
		inittoSPINFlatVisitorTest(computeCoverage);
		ASMSpecification spec = AsmMLoaderTest.cc_asmWithAxioms();
		assertNotNull(spec);
		toSPINFlatVisitor tr = new toSPINFlatVisitor();
		System.out.println(tr.analyze(spec));
	}

	@MethodSource("data") @ParameterizedTest
	public void testPunto(boolean computeCoverage) throws Exception {
		inittoSPINFlatVisitorTest(computeCoverage);
		Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		ASMSpecification s = ExampleLoader.getSpec("punto2DintParamsForSpin.asm");
		toSPINFlatVisitor tr = new toSPINFlatVisitor();
		System.out.println(tr.analyze(s));
	}

	@MethodSource("data") @ParameterizedTest
	public void testChooseSpecification(boolean computeCoverage) {

		inittoSPINFlatVisitorTest(computeCoverage);

		File derivedF = ParseSpecsAsmm.getFileSpec("fuzzyCounterChoose.asm");
		ASMSpecification SP = AsmMLoaderTest.loadSpec(derivedF);

		toSPINFlatVisitor trans = new toSPINFlatVisitor();

		System.out.println(trans.analyze(SP).toString());

	}


}
