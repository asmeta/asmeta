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

import atgt.specification.location.Variable;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.specification.Axiom;

// TODO: Auto-generated Javadoc
/**
 * representa delta contrainst over a monitored variable.
 */
public class DeltaConstraint extends Axiom {

	/**
	 * Instantiates a new delta constraint.
	 * 
	 * @param var
	 *            the var
	 * @param delta
	 *            the delta
	 */
	public DeltaConstraint(Variable var, int delta) {
		super("delta_" + var.getName(), makeBody(var.getIdExpression(), delta));
	}

	// TODO
	// per elimninare il delta e trattarlo come assioma
	/**
	 * Make body.
	 * 
	 * @param var
	 *            the var
	 * @param delta
	 *            the delta
	 * 
	 * @return the expression
	 */
	private static Expression makeBody(IdExpression var, int delta) {
		/*
		 * NextExpression varP = new NextExpression(var); Expresion c1 = new
		 * GreaterEqualExpression(varP,) Expression ifE = new
		 * EqualsExpression(var, val); ImpliesExpression result = new
		 * ImpliesExpression(ifE, thenE); return result;
		 */
		return null;
	}

}
