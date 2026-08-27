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



/** return the operator of an expression
 *  Actually used only by SCRTGTOOL. the uothers use the dispatching provided by the visitor
 *  TODO: to be deleted
 * @author garganti
 *
 */
public class GetOperator implements ExpressionVisitor<Operator>{
	
	public static final GetOperator INSTANCE = new GetOperator();
	

	@Override
	public Operator forAndExpression(AndExpression andExpression) {
		return Operator.AND;
	}

	@Override
	public Operator forDivExpression(DivExpression divExpression) {
		return Operator.DIV;
	}

	@Override
	public Operator forEqualsExpression(EqualsExpression equalsExpression) {
		return Operator.EQ;
	}

	@Override
	public Operator forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		return Operator.GE;
	}

	@Override
	public Operator forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		return Operator.GT;
	}

	@Override
	public Operator forIdExpression(IdExpression idExpression) {
		throw new RuntimeException();
	}

	@Override
	public Operator forImpliesExpression(ImpliesExpression impliesExpression) {
		return Operator.IMPLIES;
	}

	@Override
	public Operator forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		return Operator.LE;
	}

	@Override
	public Operator forLessThanExpression(LessThanExpression lessThanExpression) {
		return Operator.LT;
	}

	@Override
	public Operator forMinusExpression(MinusExpression minusExpression) {
		return Operator.MINUS;
	}

	@Override
	public Operator forMultExpression(MultExpression multExpression) {
		return Operator.MULT;
	}

	@Override
	public Operator forNegExpression(NegExpression negExpression) {
		return Operator.NOT;
	}

	@Override
	public Operator forNextExpression(NextExpression nextExpression) {
		return Operator.prime;
	}

	@Override
	public Operator forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		return Operator.NEQ;
	}

	@Override
	public Operator forNotExpression(NotExpression notExpression) {
		return Operator.NOT;
	}

	@Override
	public Operator forOrExpression(OrExpression orExpression) {
		return Operator.OR;
	}

	@Override
	public Operator forPlusExpression(PlusExpression plusExpression) {
		return Operator.PLUS;
	}

	@Override
	public Operator forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		return Operator.prime;
	}

	@Override
	public Operator forXOrExpression(XOrExpression xOrExpression) {
		return Operator.XOR;
	}

	@Override
	public Operator forModuloExpression(ModuloExpression moduloExpression) {
		return Operator.MOD;
	}

	@Override
	public Operator forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException();
	}

	@Override
	public Operator forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Operator forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	
}
