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

package extgt.coverage.fault.mutators;

import java.util.List;
import java.util.Vector;

import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.util.Pair;

/**
 * The Class FaultExpressionEmptyVisitor. Adaptor. return the empty list.
 * 
 * @author garganti
 */
public abstract class FaultExpressionEmptyVisitor extends FaultExpressionVisitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#forUnaryExpression
	 * (atgt.specification.expression.UnaryExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forUnaryExpression(Expression e) {
		return makeEmpty(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#forIdExpression
	 * (atgt.specification.expression.IdExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forIdExpression(IdExpression e) {
		return makeEmpty(e);
	}

	// Logic Expression
	// not boolean
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#forEqualsExpression
	 * (atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forEqualsExpression(EqualsExpression e) {
		return makeEmpty(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
	 * forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNotEqualsExpression(NotEqualsExpression e) {
		return makeEmpty(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.faultcoverage.FaultExpressionVisitor#forLessThanExpression
	 * (atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forLessThanExpression(LessThanExpression e) {
		return makeEmpty(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
	 * forLessEqualExpression(atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forLessEqualExpression(LessEqualExpression e) {
		return makeEmpty(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
	 * forGreaterThanExpression
	 * (atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forGreaterThanExpression(GreaterThanExpression e) {
		return makeEmpty(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.faultcoverage.FaultExpressionVisitor#
	 * forGreaterEqualExpression
	 * (atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forGreaterEqualExpression(GreaterEqualExpression e) {
		return makeEmpty(e);
	}

	/**
	 * Make empty.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the list< expression>
	 */
	private static List<Pair<Integer, Expression>> makeEmpty(Expression e) {
		List<Pair<Integer, Expression>> le = new Vector<Pair<Integer, Expression>>();
		return le;
	}
}
