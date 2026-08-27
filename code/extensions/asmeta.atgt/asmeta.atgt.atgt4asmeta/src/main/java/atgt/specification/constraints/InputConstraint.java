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
package atgt.specification.constraints;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.specification.Axiom;

// TODO: Auto-generated Javadoc
/**
 * represents axioms over the inputs two kinds: delta (TODO) next state.
 */
abstract public class InputConstraint extends Axiom {

	/** The var. */
	private TypedInitExpression var;

	/**
	 * Instantiates a new input constraint.
	 * 
	 * @param name
	 *            the name of the constraint
	 * @param body
	 *            the body of the constraint
	 * @param _var
	 *            the _var
	 */
	public InputConstraint(String name, TypedInitExpression _var, Expression body) {
		super(name, body);
		var = _var;
	}

	/**
	 * Gets the var.
	 * 
	 * @return the var
	 */
	public final TypedInitExpression getVar() {
		return var;
	}
}
