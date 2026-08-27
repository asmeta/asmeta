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
import static org.junit.Assert.fail;

import org.junit.Test;

import atgt.coverage.AsmTestCondition;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpressionCreator;

/**
 * The Class LastValuesTest.
 */
public class FirstValuesTest {

	@Test
	public void testNoAdd() {
		FirstValues lv = new FirstValues();
		// teh first instruction is an Add State
		// so one can have an empty test seq with no state 
		try{
			lv.addAssignment("x", "0");
		} catch (Exception e) {
			return;
		}
		fail("");
	}


	@Test
	public void testAdd() {
		FirstValues lv = new FirstValues();
		// teh first instruction is an Add State
		// so one can have an empty test seq with no state
		Variable x = new Variable(IdExpressionCreator.createNewIdExpression("x"),null,null);
		lv.addState();
		lv.addAssignment(x, "0");
		lv.addState();
		lv.addAssignment(x, "1");
		assertEquals(1, lv.allInstructions().size());
		assertEquals("0", SALGenerationUtil.getValue("x",lv.allInstructions().get(0)));
	}
/**
	 * Test get asm sequence.
	 */
	@Test
	public void testGetAsmSequence() {
		AsmTestSequenceFirstValues as = new AsmTestSequenceFirstValues(new AsmTestCondition("", null));
		// teh first instruction is an Add State
		// so one can have an empty test seq with no state 
		Variable x = new Variable(IdExpressionCreator.createNewIdExpression("x"), null, null);
		as.addState();
		as.addAssignment(x, "0");
		as.addState();
		as.addAssignment(x, "1");
		assertEquals(1, as.allInstructions().size());
		assertEquals("0", SALGenerationUtil.getValue("x",as.allInstructions().get(0)));
	}
	

}
