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
package extgt.coverage.fault.mutators.foms;

import java.util.ArrayList;
import java.util.List;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GetConditions;
import tgtlib.definitions.expression.GetOperator;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExtraVariableFault;
import extgt.coverage.fault.mutators.IsTerm;
import extgt.coverage.fault.mutators.IsTerm.TermType;

/**
 * inserting  a literal in a generic expression. Insertion as leaf with the same 
 * operator. 
 * Clause Insertion Fault (CIF) - insert a clause d, that is, replace a clause c
by c § d, where d is another clause, § is either conjunction or disjunction.
There are two subclasses of this class.
� Clause Conjunction Fault (CCF) - replace a clause c by c ^ d.
� Clause Disjunction Fault (CDF) - replace a clause c by c _ d.
 */
abstract class ClauseInsertionFault extends ExtraVariableFault {

	
	private Operator op;
	private TermType stopTerm;
	/**
	 * 
	 * @param ids 
	 * @param termtype 
	 * 	term type to be inserted
	 * @param ids 
	 */
	ClauseInsertionFault(List<IdUNotIdExpression> ids, TermType termtype) {
		super(ids);
		stopTerm = termtype;
		op = getOperatorForTT(stopTerm);
	}
		
	private Operator getOperatorForTT(TermType stopTerm2) {
		if (stopTerm2 == TermType.AND_TERM)
			return Operator.AND;
		else if (stopTerm2 == TermType.OR_TERM)
			return Operator.OR;
		throw new RuntimeException(" termi type ??");
	}


	/**
	 * 
	 * @param e
	 * @param op AND-> conjoints, OR -> disjoints
	 * @return the list of e op Ids where Ids are the ids to be inserted
	 */
	private List<Pair<Integer, Expression>> makeJoints(Expression e, Operator op) {
		// get the ids of e
		List<IdUNotIdExpression> idOfe = e.accept(GetConditions.getConds);
		// just add all the ids not already in e
		List<IdUNotIdExpression> toInsert = new ArrayList<IdUNotIdExpression>(getConditions());
		toInsert.removeAll(idOfe);
		List<Pair<Integer, Expression>> result = new ArrayList<Pair<Integer, Expression>>();
		for (IdUNotIdExpression id : toInsert) {
			result.add(new Pair<Integer, Expression>(1,BinaryExpression.mkBinExpr(e, op, id)));
		}
		return result;
	}

	private List<Pair<Integer, Expression>> forBinaryExpression(BinaryExpression e) {
		Operator operator = e.accept(GetOperator.INSTANCE);
		TermType type = e.accept(IsTerm.instance);
		if (type == stopTerm) {
			return makeJoints(e, this.op);
		} else {
			return distribute(e, operator);
		}
	}

	@Override
	public List<Pair<Integer, Expression>> forAndExpression(AndExpression e) {
		return forBinaryExpression(e);
	}

	@Override
	public List<Pair<Integer, Expression>> forOrExpression(OrExpression e) {
		return forBinaryExpression(e);
	}

	@Override
	public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
		return forBinaryExpression(e);
	}

	@Override
	public List<Pair<Integer, Expression>> forIdExpression(IdExpression e) {
		return makeJoints(e, this.op);
	}

	@Override
	public List<Pair<Integer, Expression>> forNotExpression(NotExpression e) {
		TermType type = e.accept(IsTerm.instance);
		if (type == TermType.LIT_TERM) {
			return makeJoints(e,  this.op);
		} else {
			assert type == TermType.NO_TERM;
			return distribute(e,Operator.NOT);
		}
	}
}
