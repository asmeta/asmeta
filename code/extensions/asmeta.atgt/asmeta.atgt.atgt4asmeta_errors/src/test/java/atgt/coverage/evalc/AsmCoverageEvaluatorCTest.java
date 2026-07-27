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

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.RootCoverage;
import atgt.coverage.VisitableTPTreeNode;
import atgt.coverage.eval.ExpressionEvaluatorSeqTest;
import atgt.parser.ExampleLoader;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;

/**
 * test the evaluation of a test sequence by using C code
 */
public class AsmCoverageEvaluatorCTest {

	/**
	 * Test compute coverage.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testComputeCoverage() throws ParseException, IOException {
		ASMSpecification spec = ExampleLoader.getSpec("sis.gs");
		assertNotNull(spec);
		tgtlib.coverage.CoverageTree<AsmTestCondition> ct = RootCoverage.ROOT.getTPTree(spec);
		assertNotNull(ct);
		String[][] a = { { "waterPressure", "14" }, null,
				{ "waterPressure", "10" } };
		AsmTestSequence test = ExpressionEvaluatorSeqTest.createTestSequence(a);
		AsmCoverageEvaluatorC evt = new AsmCoverageEvaluatorC(spec, (VisitableTPTreeNode) ct);
		System.out.println(evt.computeCoverage(new NavigableAsmInputs(test, spec)));
		// TODO add the assert to be sure that the coverage is evaluated
		//
	}

	
	
}
