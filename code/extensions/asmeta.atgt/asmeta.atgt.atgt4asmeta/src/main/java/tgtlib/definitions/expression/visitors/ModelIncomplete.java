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
package tgtlib.definitions.expression.visitors;

import java.util.Map;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;

/**
 * the model does not specify the value for the var.
 */
public class ModelIncomplete extends RuntimeException {

	/**
	 * Instantiates a new model incomplete.
	 * 
	 * @param s
	 *            the s
	 * @param e 
	 */
	ModelIncomplete(String s, Expression e) {
		super(s + " not defined when evaluating " + e.toString());
	}

	public ModelIncomplete(IdExpression e, Map<String, String> state) {
		super("value not defined when evaluating " + e.toString() + " in " + state.toString());
	}

	public ModelIncomplete(IdExpression id, Expression e, Map<String, String> state) {
		super("value of "+id+" ("+id.getClass()+") not defined when evaluating " + e.toString() + " in expression " + e.toString() + " state "+ state.toString());
	}
}
