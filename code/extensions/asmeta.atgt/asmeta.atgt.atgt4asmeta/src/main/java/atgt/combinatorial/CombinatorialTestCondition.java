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


import atgt.coverage.AsmTestCondition;
import tgtlib.definitions.expression.Expression;

/**
 * test condition regarding the combinatorial testing
 * 
 * @author garganti
 * 
 */
// TODO:
//public class CombinatorialTestCondition extends TestCondition<COmbinatorialTest> {
//public class CombinatorialTestCondition extends TestCondition<AsmTestSequence> {
public abstract class CombinatorialTestCondition extends AsmTestCondition {

	public CombinatorialTestCondition(String _name, Expression se) {
		super(_name, se);
	}
	
}
