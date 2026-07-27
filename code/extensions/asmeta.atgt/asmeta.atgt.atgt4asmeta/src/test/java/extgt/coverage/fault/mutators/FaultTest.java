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

package extgt.coverage.fault.mutators;

import static tgtlib.definitions.expression.BinaryExpression.mkBinExpr;
import static tgtlib.definitions.expression.Operator.AND;
import static tgtlib.definitions.expression.Operator.OR;

import org.junit.BeforeClass;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NotExpression;

/**
 * The Class FaultTest.
 * 
 * @author garganti
 */
public class FaultTest extends ExpressionsToTest{

	protected static IdExpression x1,x2,x3,x4;
	
	//  (x1 \/ not x2) /\ (x3 /\ x4)
	protected static Expression chenExpr;

	/**
	 * Instantiates a new fault test.
	 */
	@BeforeClass
	public static void faultTestSetup() {
		ExpressionsToTest.faultTestSetup();
		// for chen expressions
		x1 = icc.createIdExpression("x1", null);
		x2 = icc.createIdExpression("x2", null);
		x3 = icc.createIdExpression("x3", null);
		x4 = icc.createIdExpression("x4", null);
		//
		chenExpr = mkBinExpr(mkBinExpr(x1,OR,NotExpression.createNotExpression(x2)),
				AND, mkBinExpr(x3, AND, x4));
	}
}
