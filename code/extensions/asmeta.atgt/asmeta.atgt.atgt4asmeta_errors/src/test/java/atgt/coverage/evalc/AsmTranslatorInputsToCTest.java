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

import org.junit.Test;

import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.RootCoverage;
import atgt.coverage.VisitableTPTreeNode;
import atgt.coverage.eval.ExpressionEvaluatorSeqTest;
import atgt.parser.ExampleLoader;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;

/**
 * The Class AsmTranslatorInputsToCTest.
 */
public class AsmTranslatorInputsToCTest {

	/**
	 * Test translate.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testTranslateSISGs() throws ParseException, IOException {
		ASMSpecification spec = ExampleLoader.getSpec("sis.gs");
		assertNotNull(spec);
		AsmTranslatorInputsToC tr = new AsmTranslatorInputsToC(spec,new AsmCoverageTree("EMPTY"));
		String[][] a = { { "waterPressure", "14" }, null,{ "waterPressure", "10" } };
		AsmTestSequence test = ExpressionEvaluatorSeqTest.createTestSequence(a);
		System.out.println(tr.translate(new NavigableAsmInputs(test, spec)));
	}


	@Test
	public void testTranslateSISasm() throws ParseException, IOException {
		ASMSpecification spec = ExampleLoader.getSpec("SIS.asm");
		assertNotNull(spec);
		AsmTranslatorInputsToC tr = new AsmTranslatorInputsToC(spec,new AsmCoverageTree("EMPTY"));
		String[][] a = { { "waterpressure", "12" }, null,{ "waterpressure", "10" } };
		AsmTestSequence test = ExpressionEvaluatorSeqTest.createTestSequence(a);
		System.out.println(tr.translate(new NavigableAsmInputs(test, spec)));
	}

	/**
	 * Test translate with tps.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testTranslateWithTps() throws ParseException, IOException {
		ASMSpecification spec = ExampleLoader.getSpec("sis.gs");
		assertNotNull(spec);
		VisitableTPTreeNode ct = RootCoverage.ROOT.getTPTree(spec);
		AsmTranslatorInputsToC tr = new AsmTranslatorInputsToC(spec, ct);
		String[][] a = { { "waterPressure", "14" }, null, { "block", "off" } };
		AsmTestSequence test = ExpressionEvaluatorSeqTest.createTestSequence(a);
		System.out.println(tr.translate(new NavigableAsmInputs(test, spec)));
	}

	/**
	 * vediamo se non ha condizione iniziale.
	 */
	@Test
	public void testTransNoInitialSTate() {
		ASMSpecification spec = atgt.combinatorial.Util.two_powerN(3);
		assertNotNull(spec);
		tgtlib.coverage.CoverageTree<AsmTestCondition> ct = RootCoverage.ROOT.getTPTree(spec);
		assertNotNull(ct);
		String[][] a = { { "v1", "true" } };
		AsmTestSequence test = ExpressionEvaluatorSeqTest.createTestSequence(a);
		AsmCoverageEvaluatorC evt = new AsmCoverageEvaluatorC(spec, (VisitableTPTreeNode) ct);
		System.out.println(evt.computeCoverage(new NavigableAsmInputs(test,
				spec)));
	}

	/**
	 * Test trans no initial s tate no tp.
	 */
	@Test
	public void testTransNoInitialSTateNoTP() {
		int numvar = 3;
		ASMSpecification spec = atgt.combinatorial.Util.two_powerN(numvar);
		assertNotNull(spec);
		AsmTranslatorInputsToC trans = new AsmTranslatorInputsToC(spec, null);
		assertEquals(numvar, trans.getOneStateVarsDecl().size());
	}

}
