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

package tgtlib.definitions.expression;

/**
 * requires the definition only of the visitor for logic expressions. For the other throw an exception
 * 
 * TODO: to be completed - not used yet
 * 
 * @author garganti
 */
public abstract class LogicExpressionVisitorAdapter<T> implements ExpressionVisitor<T> {

	@Override
	public T forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		throw new RuntimeException("call not permitted");
	}

	@Override
	public T forEqualsExpression(EqualsExpression equalsExpression) {
		throw new RuntimeException("call not permitted");
	}

	@Override
	public T forIdExpression(IdExpression idExpression) {
		throw new RuntimeException("call not permitted");
	}

	@Override
	public T forDivExpression(DivExpression divExpression) {
		throw new RuntimeException("call not permitted");
	}

	@Override
	public T forPlusExpression(PlusExpression plusExpression) {
		throw new RuntimeException("call not permitted");
	}

	@Override
	public T forMinusExpression(MinusExpression minusExpression) {
		throw new RuntimeException("call not permitted");
	}

	@Override
	public T forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forLessEqualExpression(LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forLessThanExpression(LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forMultExpression(MultExpression multExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T forModuloExpression(ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		return null;
	}
}
