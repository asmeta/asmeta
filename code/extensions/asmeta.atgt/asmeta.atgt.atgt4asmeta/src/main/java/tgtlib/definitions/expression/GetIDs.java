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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * returns the ids (variables) of an expression (without duplicates): Including enum consts like true and false
 * 
 * @deprecated use the get conditions instead or IDExpressionCollector to get the ids
 * @author garganti
 */
@Deprecated
public final class GetIDs implements ExpressionVisitor<List<IdExpression>> {

	private GetIDs() {
	}

	static public GetIDs getIDs = new GetIDs();
	
	private List<IdExpression> forBinaryExpression(BinaryExpression e) {
		ArrayList<IdExpression> l = new ArrayList<IdExpression>(e.getFirstOperand().accept(this));
		for(IdExpression id :e.getSecondOperand().accept(this)){
			if (! l.contains(id)) l.add(id);
		}
		return l;
	}

	@Override
	public List<IdExpression> forIdExpression(IdExpression idExpression) {
		//assert ! (idExpression instanceof EnumConst);
		return Collections.singletonList(idExpression);
	}

	@Override
	public List<IdExpression> forAndExpression(AndExpression andExpression) {
		return forBinaryExpression(andExpression);
	}

	@Override
	public List<IdExpression> forOrExpression(OrExpression orExpression) {
		return forBinaryExpression(orExpression);
	}

	@Override
	public List<IdExpression> forXOrExpression(XOrExpression xOrExpression) {
		return forBinaryExpression(xOrExpression);
	}

	@Override
	public List<IdExpression> forNotExpression(NotExpression notExpression) {
		return notExpression.getOperand().accept(this);
	}

	@Override
	public List<IdExpression> forImpliesExpression(
			ImpliesExpression impliesExpression) {
		return forBinaryExpression(impliesExpression);
	}
	
	@Override
	public List<IdExpression> forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		return forBinaryExpression(greaterEqualExpression);
	}

	@Override
	public List<IdExpression> forEqualsExpression(
			EqualsExpression equalsExpression) {
		return forBinaryExpression(equalsExpression);
	}

	@Override
	public List<IdExpression> forDivExpression(DivExpression divExpression) {
		return forBinaryExpression(divExpression);
	}

	@Override
	public List<IdExpression> forPlusExpression(PlusExpression plusExpression) {
		return forBinaryExpression(plusExpression);
	}

	@Override
	public List<IdExpression> forMinusExpression(MinusExpression minusExpression) {
		return forBinaryExpression(minusExpression);
	}

	@Override
	public List<IdExpression> forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		return forBinaryExpression(greaterThanExpression);
	}

	@Override
	public List<IdExpression> forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		return forBinaryExpression(lessEqualExpression);
	}

	@Override
	public List<IdExpression> forLessThanExpression(
			LessThanExpression lessThanExpression) {
		return forBinaryExpression(lessThanExpression);
	}

	@Override
	public List<IdExpression> forMultExpression(MultExpression multExpression) {
		return forBinaryExpression(multExpression);
	}

	@Override
	public List<IdExpression> forNegExpression(NegExpression negExpression) {
		return negExpression.getOperand().accept(this);
	}

	@Override
	public List<IdExpression> forNextExpression(NextExpression nextExpression) {
		return nextExpression.getOperand().accept(this);
	}

	@Override
	public List<IdExpression> forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		return forBinaryExpression(notEqualsExpression);
	}

	@Override
	public List<IdExpression> forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		return primedIdExpression.getOperand().accept(this);
	}

	@Override
	public List<IdExpression> forModuloExpression(
			ModuloExpression moduloExpression) {
		return forBinaryExpression(moduloExpression);
	}

	@Override
	public List<IdExpression> forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdExpression> forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<IdExpression> forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}
