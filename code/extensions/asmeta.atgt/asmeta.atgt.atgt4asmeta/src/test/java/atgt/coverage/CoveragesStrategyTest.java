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
package atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;

/**
 * The Class CoveragesStrategyTest.
 * 
 * @author garganti
 */
class CoveragesStrategyTest {

	/**
	 * Sets the up.
	 * 
	 * @throws java.lang.Exception *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeEach void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link atgt.coverage.RootCoverage#generateCoverages(atgt.specification.ASMSpecification)}.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test void generateCoverages() throws Exception {
		ASMSpecification SP = atgt.parser.asmgofer.ASMParserTest.getCruiseControlNoAxiom();
		assertEquals("CruiseControl", SP.getName());
		// check tp tree
		AsmCoverageTree ct = (AsmCoverageTree) RootCoverage.ROOT.getTPTree(SP);
		// check root name
		assertEquals(RootCoverage.ROOT_NAME, ct.getName());
		// check names
		Iterator<VisitableTPTreeNode> ctIter = ct.allCoverages().iterator();
		assertTrue(ctIter.hasNext());
		// chakc TP names
		assertTrue(ctIter.next().toString().startsWith("Structural"));		
		// it contains test conditions
		Iterator<AsmTestCondition> tpIterator = ct.allTPs().iterator();
		assertTrue(tpIterator.hasNext());
		// chakc TP names
		assertTrue(tpIterator.next().toString().startsWith("BR"));
	}

}
