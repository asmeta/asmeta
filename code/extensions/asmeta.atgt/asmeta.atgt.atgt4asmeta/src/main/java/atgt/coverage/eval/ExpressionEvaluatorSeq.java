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
package atgt.coverage.eval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import atgt.specification.expression.AsmExpressionVisitor;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.visitors.EvaluationNotSupported;
import tgtlib.definitions.expression.visitors.ExpressionEvaluator;
import tgtlib.definitions.expression.visitors.MathExpressionEvaluator;

/**
 * evaluates an expression against a list of assignments and returns true if it
 * is satisfied by the expression
 * 
 * It is implemented as ExpressionVisitor, which returns a boolean. The
 * assignments are a field of the visitor.
 * 
 * NON SONO SICURO CHE FUNZIONI !!!
 */
public class ExpressionEvaluatorSeq extends AsmExpressionVisitor<Boolean> {
	static private Logger logger = Logger.getLogger(ExpressionEvaluatorSeq.class);

	/** The list of assignments. */
	List<Map<Location, String>> assignements;

	/**
	 * Instantiates a new expression evaluator.
	 * 
	 * @param list
	 *            the list of assignments
	 */
	public ExpressionEvaluatorSeq(List<Map<Location, String>> list) {
		assignements = list;		
		//PA: 26 gen 13
		for(int i = 1; i < assignements.size(); i++) {
			Map<Location, String> previousState = assignements.get(i - 1);
			Map<Location, String> currentState = assignements.get(i);
			for(Location previousStateVar: previousState.keySet()) {
				if(!currentState.containsKey(previousStateVar)) {
					currentState.put(previousStateVar, previousState.get(previousStateVar));
				}
			}
		}
	}

	/**
	 * For expression.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the boolean
	 */
	public Boolean forExpression(Expression e) {
		if (assignements.isEmpty())
			return false;
		int i = 0;
		Map<Location, String> state = new HashMap<Location, String>(assignements.get(i));
		ExpressionEvaluator ev = new ExpressionEvaluator(state);
		for (;;) {			
			// PA 26 giu 2013
			// MathExpressionEvaluator mathEvaluator = new MathExpressionEvaluator(state);
			//Expression eMathSimplified = mathEvaluator.evaluate(e);
			//logger.debug("math simplified = " + eMathSimplified);
			// Evaluate the expression
			//if (eMathSimplified.accept(ev))
			if (e.accept(ev))
				return true;
			// else continue;
			if (++i >= assignements.size())
				break;
			// another state
			ev.add(assignements.get(i));
		}
		return false;
	}

	/*
	 * Solo a = b and c = d (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public Boolean forAndExpression(AndExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public Boolean forEqualsExpression(EqualsExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forGreaterEqualExpression(atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public Boolean forGreaterEqualExpression(GreaterEqualExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forGreaterThanExpression(atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public Boolean forGreaterThanExpression(GreaterThanExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt.specification.expression.IdExpression)
	 */
	@Override
	public Boolean forIdExpression(IdExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forLessEqualExpression(atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public Boolean forLessEqualExpression(LessEqualExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forLessThanExpression(atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public Boolean forLessThanExpression(LessThanExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forMinusExpression(atgt.specification.expression.MinusExpression)
	 */
	@Override
	public Boolean forMinusExpression(MinusExpression e) {
		throw new EvaluationNotSupported("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forMultExpression(atgt.specification.expression.MultExpression)
	 */
	@Override
	public Boolean forMultExpression(MultExpression e) {
		throw new EvaluationNotSupported("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNegExpression(atgt.specification.expression.NegExpression)
	 */
	@Override
	public Boolean forNegExpression(NegExpression e) {
		throw new EvaluationNotSupported("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public Boolean forNotEqualsExpression(NotEqualsExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt.specification.expression.NotExpression)
	 */
	@Override
	public Boolean forNotExpression(NotExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public Boolean forOrExpression(OrExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forPlusExpression(atgt.specification.expression.PlusExpression)
	 */
	@Override
	public Boolean forPlusExpression(PlusExpression e) {
		throw new EvaluationNotSupported("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forUnaryExpression(atgt.specification.expression.UnaryExpression)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forXOrExpression(atgt.specification.expression.XOrExpression)
	 */
	@Override
	public Boolean forXOrExpression(XOrExpression e) {
		return forExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression(atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public Boolean forImpliesExpression(ImpliesExpression e) {
		return forExpression(e);
	}


	///////////////////////
	// NOT SUPPORTED

	public Boolean forUnaryExpression(Expression e) {
		throw new EvaluationNotSupported("not supported");
	}
	
	@Override
	public Boolean forNextExpression(NextExpression nextExpression) {
		throw new EvaluationNotSupported("not supported");
	}
	@Override
	public Boolean forModuloExpression(ModuloExpression moduloExpression) {
		throw new EvaluationNotSupported("not supported");
	}
	
	@Override
	public Boolean forDivExpression(DivExpression e) {
		throw new EvaluationNotSupported("not supported");
	}
	@Override
	public Boolean forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException();
	}
	
	@Override
	public Boolean forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Boolean forConditionalExpression(CondExpression cond) {
		// TODO 
		throw new RuntimeException("not implemented yet");
	}

	
}
