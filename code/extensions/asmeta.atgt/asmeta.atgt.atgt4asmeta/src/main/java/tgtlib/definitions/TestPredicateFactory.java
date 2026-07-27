/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.definitions;

import tgtlib.definitions.expression.Expression;

public interface TestPredicateFactory<Q extends TestPredicate<?,?>> {

	/** build a new Test Predicate for a Faulty condition
	 * 
	 * @param n
	 * @param expression
	 * @return
	 */
	Q buildTestPredicate(String n, Expression expression);

}
