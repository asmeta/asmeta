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
package atgt.coverage.eval;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.parser.asmgofer.AsmExpressionParser;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.location.Location.VarKind;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.type.BoolType;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpressionEvaluatorTest.
 */
public class ExpressionEvaluatorSeqTest {

	/**
	 * Test for and expression.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testForAndExpression() throws ParseException {
		// A = a AND B = b
		String[][] t1 = { { "A", "c" }, { "B", "b" } };
		assertFalse(testExpreSeq("A == a && B == b", t1));
		String[][] t2 = { { "C", "c" }, { "B", "b" }, { "A", "a" } };
		assertTrue(testExpreSeq("A == a && B == b", t2));
	}

	/**
	 * Assert not kwown.
	 * 
	 * @param testExpreSeq
	 *            the test expre seq
	 */
	private void assertNotKwown(FinalResult testExpreSeq) {
		assertEquals(testExpreSeq, FinalResult.DONOTKNOW);
	}

	/**
	 * Assert true.
	 * 
	 * @param testExpreSeq
	 *            the test expre seq
	 */
	private void assertTrue(FinalResult testExpreSeq) {
		assertEquals(testExpreSeq, FinalResult.TRUE);

	}

	/**
	 * Assert false.
	 * 
	 * @param testExpreSeq
	 *            the test expre seq
	 */
	private void assertFalse(FinalResult testExpreSeq) {
		assertEquals(testExpreSeq, FinalResult.FALSE);
	}

	/**
	 * Test with sequence cc.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testWithSequenceCC() throws ParseException {
		String prop = "cruiseControl == Cruise && ignited && engRun && not tooFast";
		String[][] a = { { "cruiseControl", "1" }, null, { "ignited", "false" } };
		assertFalse(testExpreSeq(prop, a));
		String[][] b = { { "ignited", BoolType.FALSE_CONST.getIdString() } };
		assertFalse(testExpreSeq("ignited", b));
		String[][] c = { { "ignited", BoolType.TRUE_CONST.getIdString() } };
		assertTrue(testExpreSeq("ignited", c));
	}

	/**
	 * Test with sequence and.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test
	public void testWithSequenceAND() throws ParseException {
		// the expression evaluator is not able to discore if it is covered or
		// not
		String[][] a = { { "A", "1" }, null, { "A", "3" } };
		assertNotKwown(testExpreSeq("A == 3 && B ==4", a));
		String[][] b = { { "A", "1" }, { "B", "4" }, null, { "A", "3" } };
		assertTrue(testExpreSeq("A == 3 && B ==4", b));
	}

	/**
	 * Test gt expression.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws RuntimeException
	 *             the runtime exception
	 */
	@Test
	public void testGTExpression() throws ParseException, RuntimeException {
		String[][] a = { { "C", "c" }, { "B", "b" } };
		assertNotKwown(testExpreSeq("A > 10", a));
	}
	/**
	 * Test gt expression.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws RuntimeException
	 *             the runtime exception
	 */
	@Test
	public void testGTExpression2() throws ParseException, RuntimeException {
		String[][] a = { { "A", "10" }, { "B", "5" } };
		assertFalse(testExpreSeq("A > 10", a));
	}

	/**
	 * The Enum FinalResult.
	 */
	enum FinalResult {
		/** The TRUE. */
		TRUE, /** The FALSE. */
		FALSE, /** The DONOTKNOW. */
		DONOTKNOW
	}

	/**
	 * Test expre seq.
	 * 
	 * @param es
	 *            the es
	 * @param testseq
	 *            the testseq
	 * 
	 * @return the final result
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	private FinalResult testExpreSeq(String es, String[][] testseq)
			throws ParseException {
		// the expression evaluator is not able to discore if it is covered or
		// not
		Expression e =  AsmExpressionParser.parse(es);
		ExpressionEvaluatorSeq ev = buildTestSequence(testseq);
		try {
			boolean result = e.accept(ev);
			return result ? FinalResult.TRUE : FinalResult.FALSE;
		} catch (Exception ex) {
			ex.printStackTrace();
			return FinalResult.DONOTKNOW;
		}
	}

	/**
	 * given a test sequence as String null : new state.
	 * 
	 * @param asses
	 *            the asses
	 * 
	 * @return the expression evaluator seq
	 */
	private ExpressionEvaluatorSeq buildTestSequence(String[][] asses) {
		//
		AsmTestSequence tr = createTestSequence(asses);
		return new ExpressionEvaluatorSeq(tr.allInstructions());
	}

	/**
	 * create a test sequence form a matrix of string. if the content is null
	 * --> new state
	 * 
	 * @param asses
	 *            the asses
	 * @param spec 
	 * 
	 * @return the asm test sequence
	 */
	static public AsmTestSequence createTestSequence(String[][] asses) {
		AsmTestCondition tc = new AsmTestCondition("",null);
		AsmTestSequence tr = new AsmTestSequence(tc);
		// set the first state
		tr.addState();
		for (String[] asse : asses) {
			if (asse == null || asse.length == 0) {
				tr.addState();
				continue;
			}
			tr.addAssignment(asse[0], asse[1], VarKind.MONITORED);
		}
		return tr;
	}

}
