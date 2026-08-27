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

import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;

import extgt.coverage.mcdc.MaskMCDCTPBuilder;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

/**
 * The Class MCDCVisitorTest.
 * 
 * @author garganti
 */
public class MCDCVisitorTest{

	/** The D. */
	static IdExpression A;

	static IdExpression B;

	static IdExpression C;

	static IdExpression D;

	/** The a an db. */
	static AndExpression aANDb;

	/** The a o rb. */
	static OrExpression aORb;

	/** The to test. */
	MaskMCDCTPBuilder toTest = MaskMCDCTPBuilder.getMCDCVisitor();


	@BeforeClass
	public static void setUp() {
		IdExpressionCreator icc = new IdExpressionCreator();
		A = icc.createIdExpression("A", null);
		B = icc.createIdExpression("B", null);
		C = icc.createIdExpression("C", null);
		D = icc.createIdExpression("D", null);
		aANDb = new AndExpression(A, B);
		aORb = new OrExpression(A, B);
	}


	/**
	 * Test of forAndExpression method, of class
	 * atgt.coverage.MCDCVisitor.
	 */
	@Test
	public void testForAndExpression() {
		Iterable<NamedTerm> result = toTest.analyze(aANDb);
		for (NamedTerm ne : result)
			System.out.println(ne.getName() + " -> " +ne.getCondition());
		Iterator<NamedTerm> resultI = result.iterator();
		assertEquals("[<TT, FT>, <TT, TF>]",result.toString());
		assertEquals("A and B", resultI.next().getCondition().toString());		
		assertEquals("not A and B", resultI.next().getCondition().toString());		
		assertEquals("A and B", resultI.next().getCondition().toString());		
		assertEquals("A and not B", resultI.next().getCondition().toString());		
		assertFalse(resultI.hasNext());		
	}

	/**
	 * Test of forAndExpression method, of class
	 * atgt.coverage.MCDCVisitor.
	 */
	@Test
	public void testForOrExpression() {
		Iterable<NamedTerm> result = toTest.analyze(aORb);
		assertEquals("A or B",aORb.toString());		
		for (NamedTerm ne : result)
			System.out.println(ne.getName() + ne.getCondition());
		// [<TF: A and not B, FF: not A and not B>, <FT: not A and B, FF: not A and not B>]
		Iterator<NamedTerm> resultI = result.iterator();
		assertEquals("TF: A and not B", resultI.next().toString());		
		assertEquals("FF: not A and not B", resultI.next().toString());		
		assertEquals("FT: not A and B", resultI.next().toString());		
		assertFalse(resultI.hasNext());		
	}

	@Test
	public void testFornotExpression() throws ParseException {
		Expression nota = ExpressionParser.parseAsNewBooleanExpression("not a");
		Iterable<NamedTerm> result = toTest.analyze(nota);
		for (NamedTerm ne : result)
			System.out.println(ne.getName() + ne.getCondition());
		assertEquals("[<T: a, F: not a>]",result.toString());
	}

	@Test
	public void testForEqExpression() throws ParseException {
		Expression nota = ExpressionParser.parseAsNewBooleanExpression("a <=> (b or c)");
		Iterable<NamedTerm> result = toTest.analyze(nota);
		// NOT IMPLEMENTED YET
		// how to implement this ???
	}

	@Test
	public void testForXorExpression() throws ParseException {
		Expression nota = ExpressionParser.parseAsNewBooleanExpression("a xor b");
		Iterable<NamedTerm> result = toTest.analyze(nota);
	}

	
}
