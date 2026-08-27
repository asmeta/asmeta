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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.generator.AsmMonitoredDataExtractor;
import atgt.parser.asmgofer.ASMParserTest;
import atgt.preferences.ATGToolPreferences;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import extgt.coverage.combinatorial.MonitoredData;
import extgt.coverage.combinatorial.PairwiseCovBuilder;
import extgt.coverage.combinatorial.StdPairwiseCovBuild;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumType;

/**
 * test the generation for pairwise coverage tree.
 */
public class PairwiseCoverageTest {
	
	@BeforeClass
	public static void setupLogger(){
		Logger.getLogger(AsmCombCovBuilder.class).setLevel(Level.ALL);
		Logger.getLogger(AsmMonitoredDataExtractor.class).setLevel(Level.ALL);
		Logger.getLogger(StdPairwiseCovBuild.class).setLevel(Level.ALL);
	}

	/**
	 * Test for specification cc.
	 */
	@Test
	public void testForSpecificationCC() {
		ASMSpecification SP = ASMParserTest.getCruiseControlNoAxiom();
		System.out.println(SP.getVariables());
		CoverageTree<AsmTestCondition> result = AsmCombCovBuilder.makePairwiseCovBuilder().getTPTree(SP);
		Iterator<AsmTestCondition> i = result.allTPs().iterator();
		assertNotNull(i);
		assertTrue(i.hasNext());
		assertEquals("(ignited = false) and (brake = false)",i.next().getCondition().toString() );
		assertEquals("(ignited = false) and (brake = true)",i.next().getCondition().toString() );
		assertEquals("(ignited = true) and (brake = false)",i.next().getCondition().toString() );
		assertEquals("??",i.next().getCondition().toString() );
	}

	/**
	 * Test compute std tp.
	 */
	@Test
	public void testComputeStdTP() {
		ATGToolPreferences.TP_ORDERING.setValue(ATGToolPreferences.OrderKind.AS_GENERATED);
		MonitoredData data = new MonitoredData();
		PairwiseCovBuilder<ASMSpecification, AsmTestCondition, AsmCoverage> std = AsmCombCovBuilder.makePairwiseCovBuilder();
		ElementsType A = new EnumType("A");
		A.addElement("a1");
		A.addElement("a2");
		ElementsType B = new EnumType("B");
		B.addElement("b1");
		B.addElement("b2");
		data.add(new Variable(IdExpressionCreator.createNewIdExpression("a"), A, null));
		data.add(new Variable(IdExpressionCreator.createNewIdExpression("b"), B, null));
		CoverageTree<AsmTestCondition> tps = std.computeTPs(data);
		Iterator<AsmTestCondition> i = tps.allTPs().iterator();
		System.out.println(i.next().getCondition());
		data.add(new Variable(IdExpressionCreator.createNewIdExpression("c"), BoolType.BOOLTYPE, null));
		tps = std.computeTPs(data);
		i = tps.allTPs().iterator();
		System.out.println(i.next().getCondition());
	}
}
