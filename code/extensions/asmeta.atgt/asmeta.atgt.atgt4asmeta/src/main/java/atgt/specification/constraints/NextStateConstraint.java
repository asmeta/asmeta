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
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.OrExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class NextStateConstraint.
 */
public class NextStateConstraint extends InputConstraint {

	/** The current val. */
	private IdExpression currentVal;

	/** The next vals. */
	private IdExpression[] nextVals;

	/**
	 * Instantiates a new next state constraint.
	 * 
	 * @param name
	 *            the name of the axiom (optional)
	 * @param var
	 *            the input variable constrained
	 * @param val
	 *            the value in the current state
	 * @param valPs
	 *            all the possible values in the next state
	 */
	public NextStateConstraint(String name, Variable var, IdExpression val,
			IdExpression... valPs) {
		super(name, var, makeBody(var.getIdExpression(), val, valPs));
		currentVal = val;
		nextVals = valPs;
	}

	/**
	 * Possible change in valPs or it can remain in the same valeu.
	 * 
	 * @param name
	 *            the name of the axiom
	 * @param var
	 *            the varriable
	 * @param val
	 *            the valuye
	 * @param valPs
	 *            the val ps
	 * 
	 * @return the next state constraint
	 */
	public static NextStateConstraint possibleChangeIn(String name,
			Variable var, IdExpression val, IdExpression... valPs) {
		IdExpression[] including = java.util.Arrays.copyOf(valPs,
				valPs.length + 1);
		including[valPs.length] = val;
		return new NextStateConstraint(name, var, val, including);
	}

	/**
	 * Gets the current val.
	 * 
	 * @return the current val
	 */
	public IdExpression getCurrentVal() {
		return currentVal;
	}

	/**
	 * Gets the next vals.
	 * 
	 * @return the next vals
	 */
	public IdExpression[] getNextVals() {
		return nextVals;
	}

	/**
	 * Make body.
	 * 
	 * @param var
	 *            the variable V
	 * @param val
	 *            the value X
	 * @param valPs
	 *            the values in the next state y1,... yn
	 * 
	 * @return the expression: V = X => V' = y1 or V' = y2 ... or V' = yn
	 */
	private static Expression makeBody(IdExpression var, IdExpression val,
			IdExpression... valPs) {
		Expression thenE = null;
		for (IdExpression valP : valPs) {
			EqualsExpression eq = new EqualsExpression(var, valP);
			NextExpression n = new NextExpression(eq);
			if (thenE == null)
				thenE = n;
			else
				thenE = new OrExpression(thenE, n);
		}
		Expression ifE = new EqualsExpression(var, val);
		ImpliesExpression result = new ImpliesExpression(ifE, thenE);
		return result;
	}

}
