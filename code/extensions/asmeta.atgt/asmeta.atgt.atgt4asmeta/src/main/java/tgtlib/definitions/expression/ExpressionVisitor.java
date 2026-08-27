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


public interface ExpressionVisitor<T> {
	
	// basic
	
	T forIdExpression(IdExpression idExpression);

	T forPrimedIdExpression(PrimedIdExpression primedIdExpression);

	T forNextExpression(NextExpression nextExpression);
	
	T forFunctionTerm(FunctionTerm ft);

	// logical expressions

	T forAndExpression(AndExpression andExpression);

	T forOrExpression(OrExpression orExpression);

	T forXOrExpression(XOrExpression xOrExpression);

	T forNotExpression(NotExpression notExpression);

	T forImpliesExpression(ImpliesExpression impliesExpression);

	// MATH expressions comparison - return boolean

	T forGreaterEqualExpression(GreaterEqualExpression greaterEqualExpression);

	T forEqualsExpression(EqualsExpression equalsExpression);
	
	T forGreaterThanExpression(GreaterThanExpression greaterThanExpression);

	T forLessEqualExpression(LessEqualExpression lessEqualExpression);

	T forLessThanExpression(LessThanExpression lessThanExpression);

	T forNotEqualsExpression(NotEqualsExpression notEqualsExpression);

	// MATH value

	T forDivExpression(DivExpression divExpression);

	T forPlusExpression(PlusExpression plusExpression);

	T forMinusExpression(MinusExpression minusExpression);

	T forMultExpression(MultExpression multExpression);

	T forNegExpression(NegExpression negExpression);

	T forModuloExpression(ModuloExpression moduloExpression);

	// extended types of expressions
	
	T forCaseExpression(CaseExpression caseExpression);

	T forConditionalExpression(CondExpression cond);
	
}
