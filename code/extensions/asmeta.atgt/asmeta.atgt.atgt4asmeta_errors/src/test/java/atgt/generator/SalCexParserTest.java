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

import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.parser.trail.ParseException;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.MCExecutionResultReader;

// TODO: Auto-generated Javadoc
/**
 * The Class SalCexParserTest.
 */
public class SalCexParserTest {

	/**
	 * Test input.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testInput() throws ParseException {
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(false);

		Map<Location, String> ins = getFirstTest("salcex_ex.txt");
		
		assertEquals(SALGenerationUtil.getValue("cruiseControl", ins), "Inactive");
		assertEquals(SALGenerationUtil.getValue("ignited", ins), "false");
		assertEquals(SALGenerationUtil.getValue("cruiseEvent", ins), "Activate");
		assertEquals(SALGenerationUtil.getValue("brake", ins), "false");
		assertEquals(SALGenerationUtil.getValue("engRun", ins), "false");
		assertEquals(SALGenerationUtil.getValue("tooFast", ins), "false");
	}

	/** generate and return the first test sequence
	 * 
	 * @param spec
	 * @return
	 */
	private Map<Location, String> getFirstTest(String spec) {
		InputStream in = this.getClass().getResourceAsStream(spec);
		SalTSeqGenerator parser = new SalTSeqGenerator(null);
		MCExecutionResultReader output = new MCExecutionResultReader(in);
		AsmTestCondition tc = new AsmTestCondition("prova", null);
		AsmTestSequence ts = new AsmTestSequence(tc);

		MCAnalysisResult result = parser.analyses(output, ts);
		assertTrue(result.isTestFound());
		System.out.println(ts.toVideo());
		Map<Location, String> ins = ts.allInstructions().get(0);
		assertNotNull(ins);
		return ins;
	}

	// with a long path
	/**
	 * Test input2.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testInput2() throws ParseException {
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(false);
		Map<Location, String> ins = getFirstTest("salcex_ex2.txt");
		assertEquals(SALGenerationUtil.getValue("d", ins), "THREE");
		assertEquals(SALGenerationUtil.getValue("a", ins), "THREE");
		assertEquals(SALGenerationUtil.getValue("c", ins), "THREE");
		assertEquals(SALGenerationUtil.getValue("b", ins), "THREE");
	}

	// with an unfeasible
	/**
	 * Test unfesible smc.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testUnfesibleSMC() throws ParseException {
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(false);

		InputStream in = this.getClass().getResourceAsStream("salcex_unfea_smc.txt");

		SalTSeqGenerator parser = new SalTSeqGenerator(null);
		MCExecutionResultReader output = new MCExecutionResultReader(in);
		AsmTestCondition tc = new AsmTestCondition("prova", null);
		AsmTestSequence ts = new AsmTestSequence(tc);

		MCAnalysisResult result = parser.analyses(output, ts);
		assertTrue(result.isUnfeasible());

		// assertTrue(tc.getStatus() == TestConditionState.UNFEASIBLE);
	}

	// with an unfeasible
	/**
	 * Test unfesible bmc.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testUnfesibleBMC() throws ParseException {
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(false);

		InputStream in = this.getClass().getResourceAsStream("salcex_unfea_bmc.txt");

		SalTSeqGenerator parser = new SalTSeqGenerator(null);
		MCExecutionResultReader output = new MCExecutionResultReader(in);
		AsmTestCondition tc = new AsmTestCondition("prova", null);
		AsmTestSequence ts = new AsmTestSequence(tc);

		MCAnalysisResult res = parser.analyses(output, ts);
		assertTrue(res.isUnfeasible());
		// NO, questa funzionalità è demandata ad altri
		// assertTrue(tc.getStatus() == TestConditionState.UNFEASIBLE);
	}

	/**
	 * Test seqt.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testSeqt() throws ParseException {
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(true);

		Map<Location, String> ins = getFirstTest("salcex_ex3.txt");
		
		assertEquals(SALGenerationUtil.getValue("igOn", ins), "false");
		assertEquals(SALGenerationUtil.getValue("lever", ins), "DEACTIVATE");
		assertEquals(SALGenerationUtil.getValue("brake", ins), "false");
		assertEquals(SALGenerationUtil.getValue("engRun", ins), "false");
		assertEquals(SALGenerationUtil.getValue("fast", ins), "false");
		assertEquals(SALGenerationUtil.getValue("mode", ins), "OFF");
	}

}
