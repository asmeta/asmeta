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

import org.apache.log4j.Logger;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.Expression;

/**
 * represent a generic pair test condition it can be a pair eq test condition
 * with var1 = val1 and var2 eq val2 or a relational pair test condition var1 op
 * val1 and var2 op val2 or any generic expression for avr 1 and var2
 */
public abstract class PairTestCondition extends CombinatorialTestCondition {

	/** Logger for this class. */
	private static final Logger logger = Logger
			.getLogger(PairTestCondition.class);

	protected TypedInitExpression var1;

	/** The variables. */
	protected TypedInitExpression var2;

	/**
	 * build the test predicate e1 and e2.
	 * 
	 * @param _name
	 *            the _name
	 * @param var1
	 *            the var1
	 * @param var2
	 *            the var2
	 * @param se
	 *            the se
	 */
	protected PairTestCondition(String _name, TypedInitExpression var1, TypedInitExpression var2,
			Expression se) {
		super(_name, se);
		// set the fields
		this.var1 = var1;
		this.var2 = var2;
	}

}
