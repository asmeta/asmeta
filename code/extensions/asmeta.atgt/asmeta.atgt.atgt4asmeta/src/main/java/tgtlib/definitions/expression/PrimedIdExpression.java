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
package tgtlib.definitions.expression;


/**
 * X' where x is an IdExpression. primedIdExpression contains the expression ID' ATTENZIONE ho due
 * modi per ID': unary with primed oppure PrimedID. TODO choose a nomrlized way and use factories
 * 
 * @author garganti
 * 
 */
public class PrimedIdExpression extends UnaryExpression implements
		PrimedIdUIdExpression {

	public PrimedIdExpression(IdExpression e) {
		super(e);
	}

	/**
	 * @param e
	 *            the id (string)
	 * @deprecated
	 */
	@Deprecated
	public PrimedIdExpression(String e) {
		super(IdExpressionCreator.createNewIdExpression(e));
	}

	@Override
	public boolean equals(Object e2) {
		if (this == e2)
			return true;
		if (!(e2 instanceof PrimedIdExpression))
			return false;
		return (operand.equals(((PrimedIdExpression) e2).operand));
	}

	@Override
	public IdExpression getID() {
		return (IdExpression) operand;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> visitor) {
		return visitor.forPrimedIdExpression(this);
	}

	@Override
	public String toString() {
		return super.toString(Operator.prime, false);
	}

}
