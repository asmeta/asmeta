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
package atgt.specification.statement;

import tgtlib.definitions.expression.Expression;

// TODO: Auto-generated Javadoc
/**
 * If Then Else statement. Handles conditional statement. It has a guard
 * expression and two possible statement for execution
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class ConditionalRule extends BasicRule {

	/** The guard. */
	protected Expression guard;

	/** The then part. */
	protected BasicRule thenPart;

	/** The else part. */
	protected BasicRule elsePart; // can be null

	/**
	 * Instantiates a new conditional rule.
	 * 
	 * @param _guard
	 *            the _guard
	 * @param _thenPart
	 *            the _then part
	 * @param _elsePart
	 *            the _else part
	 */
	public ConditionalRule(Expression _guard, BasicRule _thenPart,
			BasicRule _elsePart) {
		this(_guard, _thenPart);
		this.elsePart = _elsePart;
	}

	/**
	 * Instantiates a new conditional rule.
	 * 
	 * @param _guard
	 *            the _guard
	 * @param _thenPart
	 *            the _then part
	 */
	public ConditionalRule(Expression _guard, BasicRule _thenPart) {
		this.guard = _guard;
		assert guard != null;
		this.thenPart = _thenPart;
		assert thenPart != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.BasicRule#accept(atgt.specification.statement.RuleVisitor)
	 */
	@Override
	public <T> T accept(RuleVisitor<T> ask) {
		return ask.forIfThenElse(this);
	}

	/**
	 * Gets the guard.
	 * 
	 * @return the guard
	 */
	public Expression getGuard() {
		return this.guard;
	}

	/**
	 * Gets the then part.
	 * 
	 * @return the then part
	 */
	public BasicRule getThenPart() {
		return this.thenPart;
	}

	/**
	 * Gets the else part.
	 * 
	 * @return the else part
	 */
	public BasicRule getElsePart() {
		return this.elsePart;
	}

}
