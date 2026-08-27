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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
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
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.util.Pair;

/** visits only boolean expressions, i.e. expressions which sub expressions are booleans. 
 * For example x and y is visited in x and y. 
 * x+3 > 4 is not split
 * return a list of expressions
 * if the expression is atomic or is not mutable, then return the empty list
 */

/**
 * The Class FaultExpressionVisitor.
 */
abstract public class FaultExpressionVisitor implements
		ExpressionVisitor<List<Pair<Integer,Expression>>> {

	@Override
	final public List<Pair<Integer, Expression>> forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("not implemented");
	}

	
	// Math Expression
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forPlusExpression(atgt
	 * .specification.expression.PlusExpression)
	 */
	@Override
	final public List<Pair<Integer, Expression>> forPlusExpression(PlusExpression e) {
		throw new RuntimeException("not a boolean exeption");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forMinusExpression(atgt
	 * .specification.expression.MinusExpression)
	 */
	@Override
	final public List<Pair<Integer, Expression>> forMinusExpression(MinusExpression e) {
		throw new RuntimeException("not a boolean exeption");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forDivExpression(atgt
	 * .specification.expression.DivExpression)
	 */
	@Override
	final public List<Pair<Integer, Expression>> forDivExpression(DivExpression e) {
		throw new RuntimeException("not a boolean exeption");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forMultExpression(atgt
	 * .specification.expression.MultExpression)
	 */
	@Override
	final public List<Pair<Integer, Expression>> forMultExpression(MultExpression e) {
		throw new RuntimeException("not a boolean exeption");

	}

	@Override
	final public List<Pair<Integer, Expression>> forModuloExpression(ModuloExpression e) {
		throw new RuntimeException("not a boolean exeption");
	}

	/**
	 * For unary expression.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the list< expression>
	 */
	abstract public List<Pair<Integer, Expression>> forUnaryExpression(Expression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forNegExpression(atgt
	 * .specification.expression.NegExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNegExpression(NegExpression e) {
		throw new RuntimeException("not a boolean exepression");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forIdExpression(atgt
	 * .specification.expression.IdExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forIdExpression(IdExpression e);

	// Logic Expression
	// not boolean
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forEqualsExpression(
	 * atgt.specification.expression.EqualsExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forEqualsExpression(EqualsExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forNotEqualsExpression
	 * (atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forNotEqualsExpression(
			NotEqualsExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forLessThanExpression
	 * (atgt.specification.expression.LessThanExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forLessThanExpression(LessThanExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forLessEqualExpression
	 * (atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forLessEqualExpression(
			LessEqualExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forGreaterThanExpression
	 * (atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forGreaterThanExpression(
			GreaterThanExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forGreaterEqualExpression
	 * (atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forGreaterEqualExpression(
			GreaterEqualExpression e);

	// boolean
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forAndExpression(atgt
	 * .specification.expression.AndExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forAndExpression(AndExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forOrExpression(atgt
	 * .specification.expression.OrExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forOrExpression(OrExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forNotExpression(atgt
	 * .specification.expression.NotExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forNotExpression(NotExpression e);

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forXOrExpression(atgt
	 * .specification.expression.XOrExpression)
	 */
	@Override
	abstract public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forImpliesExpression
	 * (atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forImpliesExpression(
			ImpliesExpression impliesExpression) {
		// build the equivalent
		return impliesExpression.getEquivalent().accept(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.ExpressionVisitorI#forNextExpression(atgt
	 * .specification.expression.NextExpression)
	 */
	@Override
	public List<Pair<Integer, Expression>> forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Fault expression for next ???");
	}

	@Override
	public List<Pair<Integer, Expression>> forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("Fault expression for next ???");
	}

	@Override
	public List<Pair<Integer, Expression>> forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	/** 
	 * It visits the subexpression and rebuilds the original one. 
	 * It distributes the operation to the operands and rebuilds the expression.
	 * 
	 * @param e
	 * @param op
	 * @return
	 */
	protected List<Pair<Integer, Expression>> distribute(BinaryExpression e, Operator op) {
		List<Pair<Integer, Expression>> result = new Vector<Pair<Integer, Expression>>();
		Expression e1 = e.getFirstOperand();
		Expression e2 = e.getSecondOperand();
		for (Pair<Integer, Expression> r1 : e1.accept(this)) {
			// position*2 because is on the left subtree
			BinaryExpression r1ope2 = BinaryExpression.mkBinExpr(r1.getSecond(), op, e2);
			int nextNode = getNextNodePos(r1.getFirst(), false);
			result.add(new Pair<Integer,Expression>(nextNode, r1ope2));
		}
		for (Pair<Integer, Expression> r2: e2.accept(this)) {
			// position*2 plus one because is on the right subtree
			BinaryExpression e1opr2 = BinaryExpression.mkBinExpr(e1, op, r2.getSecond());
			result.add(new Pair<Integer,Expression>(getNextNodePos(r2.getFirst(), true), e1opr2));
		}
		return result;
	}

	protected List<Pair<Integer, Expression>> distribute(UnaryExpression e, Operator op) {
		List<Pair<Integer, Expression>> result = new ArrayList<Pair<Integer, Expression>>();
		List<Pair<Integer, Expression>> p1 = e.getOperand().accept(this);
		for(Pair<Integer, Expression> exp: p1) {
			result.add(new Pair<Integer, Expression>(getNextNodePos(exp.getFirst(), false), UnaryExpression
				.mkUnExpr(Operator.NOT, exp.getSecond())));
		}
		return result;
	}

	protected static int getNextNodePos(int currentNode, boolean rightSide) {
		return currentNode *2 + (rightSide? 1 : 0);
		/** old version - using logs 
 		// 1xxxx -> 10000 
		int currentLevel = MSB(currentNode);
		// 1xxxx -> .xxxx
		int offset = currentNode - currentLevel;
		// 100000 + .xxxx
		int nextNode = (currentLevel << 1) + offset;
		// add 11xxxx
		if (rightSide) nextNode = nextNode | currentLevel;
		return nextNode;*/
	}

	// return the number with only the most significant bit to 1
	// the other are set to zero
	static int MSB(int number){
		int mask = 1;
		for(;;){
			// reset the single bit at position i
			number = number & ~ mask;
			if (number == 0) break;
			// mask = mask *2;
			mask = mask << 1;
		}
		return mask;
	}	
}
