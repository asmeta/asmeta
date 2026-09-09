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
package extgt.coverage.mcdc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import extgt.coverage.mcdc.MaskMCDCTPBuilder;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;

/**
 * The Class MCDCVisitorTest. with enums
 * 
 * @author garganti
 */
class MCDCVisitorEnumTest{

	/** The D. */
	static IdExpression A;

	static EnumConst a1,a2,a3;


	/** The to test. */
	MaskMCDCTPBuilder toTest = MaskMCDCTPBuilder.getMCDCVisitor();


	@BeforeAll
	static void setUp() {
		EnumConstCreator icc = new EnumConstCreator();
		A = icc.createIdExpression("A", null);
		a1 = icc.createEnumConst("a1");
		a2 = icc.createEnumConst("a2");
		a3 = icc.createEnumConst("a3");
	}


	/**
	 * Test of forAndExpression method, of class
	 * atgt.coverage.MCDCVisitor.
	 */
	@Test void forEqExpression() {
		EqualsExpression eq = new EqualsExpression(A, a1);
		Iterable<NamedTerm> result = toTest.analyze(eq);
		for (NamedTerm ne : result)
			System.out.println(ne.getName() + " -> " +ne.getCondition());
		Iterator<NamedTerm> resultI = result.iterator();
		assertEquals("[<T: A = a1, F: A != a1>]",result.toString());
//		assertEquals("A and B", resultI.next().getCondition().toString());		
//		assertEquals("not A and B", resultI.next().getCondition().toString());		
//		assertEquals("A and B", resultI.next().getCondition().toString());		
//		assertEquals("A and not B", resultI.next().getCondition().toString());		
//		assertFalse(resultI.hasNext());		
	}
	
	@Test void forAndEqExpression() {
		EqualsExpression eq1 = new EqualsExpression(A, a1);
		EqualsExpression eq2 = new EqualsExpression(A, a2);
		AndExpression eq = new AndExpression(eq1, eq2);		
		Iterable<NamedTerm> result = toTest.analyze(eq);
		for (NamedTerm ne : result)
			System.out.println(ne.getName() + " -> " +ne.getCondition());
		Iterator<NamedTerm> resultI = result.iterator();
		assertEquals("[<TT: (A = a1) and (A = a2), FT: (A != a1) and (A = a2)>, <TT: (A = a1) and (A = a2), TF: (A = a1) and (A != a2)>]",result.toString());
//		assertEquals("A and B", resultI.next().getCondition().toString());		
//		assertEquals("not A and B", resultI.next().getCondition().toString());		
//		assertEquals("A and B", resultI.next().getCondition().toString());		
//		assertEquals("A and not B", resultI.next().getCondition().toString());		
//		assertFalse(resultI.hasNext());		
	}

	
}
