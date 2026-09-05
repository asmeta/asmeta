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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import extgt.coverage.mcdc.MaskMCDCTPBuilder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

/**
 * The 
 * 
 * @author garganti
 */
public class MCDCVisitorTestParametric{

	/** The to test. */
	MaskMCDCTPBuilder toTest = MaskMCDCTPBuilder.getMCDCVisitor();
	private String expr;
	private String[] results;

	public void initMCDCVisitorTestParametric(String expression, String[] results){
		this.expr = expression;
		this.results = results;
	}

	public static Collection regExValues() {
	 return Arrays.asList(new Object[][] {
	  {"a", new String[]{"a", "not a"}},
	  {"not a", new String[]{"a", "not a"}},
	  {"a and b", new String[]{"a and b", "not a and b", "a and b", "a and not b"}},
	  {"a or b", new String[]{"a and not b", "not a and not b", "not a and b", "not a and not b"}},
	  //
	  {"not a or b", new String[]{"a and not b", "not a and not b", "a and b", "a and not b"}},
	  // other operator
	  {"a xor b", new String[]{"TODO"}},
	  // eq
	  {"a <=> b", new String[]{"TODO"}},	  
	 });
	}

	/**
	 * Test of forAndExpression method, of class
	 * atgt.coverage.MCDCVisitor.
	 * @throws ParseException 
	 */
	@MethodSource("regExValues") @ParameterizedTest
	public void testForExpression(String expression, String[] results) throws Exception {
		initMCDCVisitorTestParametric(expression, results);
		// read the expression
		Expression eTest = ExpressionParser.parseAsNewBooleanExpression(expr);
		Iterable<NamedTerm> result = toTest.analyze(eTest);
		List<String> resultS = new ArrayList<>();
		for (NamedTerm ne : result){
			System.out.println(ne.getName() + " -> " +ne.getCondition());
			resultS.add(ne.getCondition().toString());
		}		
		// some number of results
		assertEquals(resultS.size(), results.length);
		// same results
		for(String exResult: results){
			assertTrue(resultS.contains(exResult));
		}
	}	
}
