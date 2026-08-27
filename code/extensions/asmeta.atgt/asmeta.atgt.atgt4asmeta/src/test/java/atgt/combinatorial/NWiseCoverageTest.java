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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import atgt.coverage.AsmTestCondition;
import atgt.parser.asmgofer.ASMParserTest;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CoverageTree;

// TODO: Auto-generated Javadoc
/**
 * test the generation for pairwise coverage tree.
 */
public class NWiseCoverageTest {

	/**
	 * Test for specification cc.
	 */
	@Test
	public void testForSpecificationCC() {
		ASMSpecification SP = ASMParserTest.getCruiseControlNoAxiom();
		CoverageTree<AsmTestCondition> result = AsmCombCovBuilder.createNWiseCovBuilder(3).getTPTree(SP);
		Iterator<AsmTestCondition> i = result.allTPs().iterator();
		assertTrue(i.hasNext());
		AsmTestCondition next = i.next();
		assertEquals("(ignited == true) AND (cruiseEvent == Activate) AND (brake == true)",next.getCondition().toString() );
		System.out.println(next.getCondition());
	}

}
