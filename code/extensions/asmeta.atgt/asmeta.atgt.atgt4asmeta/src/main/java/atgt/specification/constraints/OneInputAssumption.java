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

import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.specification.Axiom;

// TODO: Auto-generated Javadoc
/**
 * represents the one input assumption, as in SCR only one input can change from
 * time to time "allows at most one monitored variable to change from one state
 * to the next" (da code generation) NON FACCIAMO per ora quella più forte :
 * "one monitored event occurs at each state transition" (da SCR) è più facile
 * da tradurre in logica con un xor.
 */
public final class OneInputAssumption extends Axiom {

	/**
	 * Instantiates a new one input assumption.
	 */
	private OneInputAssumption() {
		// the body of the axiom is not written, since
		// 1) it may be useless
		// 2) it may be difficult in LTL, for example quite impossible
		// HOWEVER a simple body is given to permit printing
		super("OneInputAssumption", null);
	}

	/** the unique one input assumption. */
	public static OneInputAssumption OIA = new OneInputAssumption();

	/**
	 * return the expression for this assumption x != x' xor y != y' xor z != z'
	 * ... nopt really useful, sicn ein SAL is transalted in a different way
	 * anzi non può essere tradotta in modo banale
	 * 
	 * @param exs
	 *            the exs
	 * 
	 * @return the expression
	 */

	public static Expression makeBody(List<IdExpression> exs) {
		if (exs.size() == 0)
			return null;
		Expression result = null;
		return result;

	}

	/*
	 * private static Expression nextDiffer(IdExpression ex){ Expression next =
	 * new Next }
	 */
}
