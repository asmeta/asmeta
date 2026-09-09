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
package atgt.translator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import atgt.parser.asmgofer.AsmGoferParser;

import org.junit.jupiter.api.Test;
import atgt.parser.asmgofer.ParseException;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionTranslator;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpressionToSALVisitorTest.
 */
class ExpressionToSALVisitorTest {

	/**
	 * Test for and expression.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test void forAndExpression() throws Exception {

		StringReader sr = new StringReader(
				"((cruiseControl == Off) && (engRun == Override))");
		AsmGoferParser parser = new AsmGoferParser(sr);
		Expression e = parser.logicExpression();
		ExpressionTranslator t = new ExpressionToSALVisitor();
		assertEquals("(cruiseControl = Off) AND (engRun = Override)", e
				.accept(t).toString());

	}

	@Test void forNotExpression() throws Exception {

		StringReader sr = new StringReader(
				"not (a && b)");
		AsmGoferParser parser = new AsmGoferParser(sr);
		Expression e = parser.logicExpression();
		ExpressionTranslator t = new ExpressionToSALVisitor();
		assertEquals("NOT(a AND b)", e.accept(t).toString());
	}

	
}
