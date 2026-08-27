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

import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;

/**
 * returns the ids (variables) or the not ids of an expression. 
 * not true and false. assuming every variable is boolean
 * 
 * @author garganti
 */
public final class GetConditions implements ExpressionVisitor<List<IdUNotIdExpression>> {

	private GetConditions() {
	}

	static public GetConditions getConds = new GetConditions();
	
	private List<IdUNotIdExpression> forBinaryExpression(BinaryExpression e) {
		ArrayList<IdUNotIdExpression> l = new ArrayList<IdUNotIdExpression>(e.getFirstOperand().accept(this));
		for(IdUNotIdExpression id :e.getSecondOperand().accept(this)){
			if (! l.contains(id)) l.add(id);
		}
		return l;
	}

	@Override
	public List<IdUNotIdExpression> forIdExpression(IdExpression idExpression) {
		if (idExpression == BoolType.FALSE_CONST) return Collections.EMPTY_LIST;
		if (idExpression == BoolType.TRUE_CONST) return Collections.EMPTY_LIST;
		assert !(idExpression instanceof EnumConst);
		return Collections.singletonList((IdUNotIdExpression)idExpression);
	}
	@Override
	public List<IdUNotIdExpression> forNotExpression(NotExpression notExpression) {
		// not id 
		if (notExpression instanceof NotIDExpression)
			return Collections.singletonList((IdUNotIdExpression)notExpression);
		else
		 return notExpression.getOperand().accept(this);
	}

	@Override
	public List<IdUNotIdExpression> forAndExpression(AndExpression andExpression) {
		return forBinaryExpression(andExpression);
	}

	@Override
	public List<IdUNotIdExpression> forOrExpression(OrExpression orExpression) {
		return forBinaryExpression(orExpression);
	}

	@Override
	public List<IdUNotIdExpression> forXOrExpression(XOrExpression xOrExpression) {
		return forBinaryExpression(xOrExpression);
	}

	@Override
	public List<IdUNotIdExpression> forImpliesExpression(
			ImpliesExpression impliesExpression) {
		return forBinaryExpression(impliesExpression);
	}
	
	@Override
	public List<IdUNotIdExpression> forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forEqualsExpression(
			EqualsExpression equalsExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forDivExpression(DivExpression divExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forPlusExpression(PlusExpression plusExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forMinusExpression(MinusExpression minusExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forLessThanExpression(
			LessThanExpression lessThanExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forMultExpression(MultExpression multExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forNegExpression(NegExpression negExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forModuloExpression(
			ModuloExpression moduloExpression) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public List<IdUNotIdExpression> forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdUNotIdExpression> forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<IdUNotIdExpression> forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}
