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
package tgtlib.definitions.expression.visitors;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.ExpressionToSMV;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
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
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.util.Pair;

// OVERWRITE THE CLASS IN THE LIBRARY

/**
 * evaluates an expression against a list of assignments and returns true if it
 * is satisfied by the expression. The model must be complete: it must give the
 * value for every variable
 * 
 * It is implemented as ExpressionVisitor, which returns a boolean. The
 * assignments are a field of the visitor.
 */
public class ExpressionEvaluator implements tgtlib.definitions.expression.ExpressionVisitor<Boolean> {

	static private Logger logger = Logger.getLogger(ExpressionEvaluator.class);

	/** The list of assignments variable -> value. represents a state */
	Map<IdExpression, String> state;
	private boolean modelMustBeComplete;

	/**
	 * Instantiates a new expression evaluator.
	 * 
	 * @param list
	 *            the list of assignments
	 * @param vars
	 *            : list of variables that if not set by the map, take their
	 *            default value
	 */
	public ExpressionEvaluator(Map<? extends Variable, String> list, Iterable<? extends Variable> vars) {
		logger.debug("evaluating over " + list + " with vars " + vars);
		state = new HashMap<IdExpression, String>();
		add(list);
		// get the initial values of controlled vars
		assert vars != null;
		for (tgtlib.definitions.expression.type.Variable v : vars) {
			if (v.isControlled()) {
				Expression e = v.getValue();
				if (e != null && e != Undef.UNDEF)
					state.put(v.getIdExpression(), e.toString());
			}
		}
		modelMustBeComplete = true;
	}

	/**
	 * call the evaluator : TODO use only this entry point
	 * 
	 * @param e
	 * @return
	 */
	public Boolean evaluate(Expression e) {
		logger.debug("evaluating " + e);
		return e.accept(this);
	}

	/**
	 * build give an assignment every id in the expression will be considered a
	 * constant if it is not assigned a value in the assignment !
	 */
	public ExpressionEvaluator(Map<? extends Variable, String> assignment) {
		this(assignment, assignment.keySet());
	}

	public ExpressionEvaluator(Map<? extends Variable, String> assignment, boolean modelMustBeComplete) {
		this(assignment);
		this.modelMustBeComplete = modelMustBeComplete;
	}

	/*
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt
	 * .specification.expression.AndExpression)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt
	 * .specification.expression.AndExpression)
	 */
	@Override
	public Boolean forAndExpression(AndExpression e) {
		return e.getFirstOperand().accept(this) && e.getSecondOperand().accept(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forDivExpression(atgt
	 * .specification.expression.DivExpression)
	 */
	@Override
	public Boolean forDivExpression(DivExpression e) {
		throw new EvaluationNotSupported(e.getClass() + " not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression
	 * (atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public Boolean forEqualsExpression(EqualsExpression e) {
		Optional<Integer> eval = evalAsmath(e);
		if (eval.isPresent()) {
			return eval.get() == 0;
		}
		Expression firstOperand = e.getFirstOperand();
		Expression secondOperand = e.getSecondOperand();
		if (firstOperand instanceof IdExpression && secondOperand instanceof IdExpression) {
			// BOTH IDs
			// e1 == e2 identical
			IdExpression e1 = (IdExpression) firstOperand;
			IdExpression e2 = (IdExpression) secondOperand;
			if (e1 == e2)
				return true;
			assert (!e1.toString().equals(e2.toString()));
			// val_i is not null iff e_i is a var
			String val1 = state.get(e1);
			// e1 is not a constant and val1 is null
			if (!(e1 instanceof EnumConst) && val1 == null)
				throw new ModelIncomplete(e1.getIdString(), e);
			// val1 ==null <=> e1 is a constant
			assert (e1 instanceof EnumConst) == (val1 == null);
			String val2 = state.get(e2);
			// e2 is not a constant and val2 is null
			if (!(e2 instanceof EnumConst) && val2 == null)
				throw new ModelIncomplete(e2, e, state);
			// val2 ==null <=> e2 is a constant
			assert (e2 instanceof EnumConst) == (val2 == null);
			// if both are constants
			if (val1 == null && val2 == null) {
				// cannot be equals as string otherwise they'd be equals ==
				return false;
			}
			// if both variables: equals iff values equals in the state
			if (val1 != null && val2 != null) {
				return val1.equals(val2);
			}
			// one const and the other var
			if (val1 != null && val2 == null) {
				return val1.equals(e2.toString());
			} else {
				assert (val1 == null && val2 != null);
				return val2.equals(e1.toString());
			}
		}
		// expr1 = expr2 AND NOT ID
		// FOR INSTANCE a or b = c
		// assume boolean e1 = e2 and e1 and e2 are boolean
		Boolean v1 = firstOperand.accept(this);
		Boolean v2 = secondOperand.accept(this);
		return v1 == v2;
	}

	// eval the two expression assuming that are mathematical expression
	// return the comparison value (as the comparator)
	// throw if part is math and part not
	private Optional<Integer> evalAsmath(BinaryExpression e) {
		Expression firstOperand = e.getFirstOperand();
		if (!(firstOperand instanceof IdExpression)) {
			//System.err.println(firstOperand.getClass());
			return Optional.empty();
		}
		Expression secondOperand = e.getSecondOperand();
		if (!(secondOperand instanceof IdExpression)) {
			//System.err.println(firstOperand.getClass());
			return Optional.empty();
		}
		// try as math expressions like a = 3 
		MathExpressionToIntEvaluator mm = new MathExpressionToIntEvaluator(state);
		Integer ii1 = mathValue(mm, firstOperand);
		Integer ii2 = mathValue(mm, secondOperand);
		if (ii1 != null && ii2 != null) {
			// both are math values, must be equal
			return Optional.of(ii1.compareTo(ii2));
		} else if (ii1 != null || ii2 != null) {
			// System.err.println(firstOperand + " " + secondOperand);
			// one if math value, the other no
			// add the case in which one is undef			
			if (ii1 != null && state.get(secondOperand).equals(Undef.UNDEF.toString()))
				return Optional.of(-1);
			// add the case in which one is undef
			System.err.println(firstOperand.getClass());
			if (ii2 != null && state.get(firstOperand).equals(Undef.UNDEF.toString()))
				return Optional.of(-1);
			throw new EvaluationNotSupported("evaluation not supported " + ii1 + " != " + ii2);
		}
		// both are not math expression
		assert ii1 == null && ii2 == null;
		return Optional.empty();
	}
		

	private Integer mathValue(MathExpressionToIntEvaluator mm, Expression firstOperand) {
		Integer ii1 = null;
		try {
			ii1 = mm.evaluate(firstOperand);
		} catch (EvaluationNotSupported ens) {
		}
		return ii1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#
	 * forGreaterEqualExpression
	 * (atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public Boolean forGreaterEqualExpression(GreaterEqualExpression e) {		
		Optional<Integer> eval = evalAsmath(e);
		if (eval.isPresent()) {
			return eval.isPresent() && eval.get() >= 0;
		}
		throw new EvaluationNotSupported("evaluation not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#
	 * forGreaterThanExpression
	 * (atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public Boolean forGreaterThanExpression(GreaterThanExpression e) {
		Optional<Integer> eval = evalAsmath(e);
		if (eval.isPresent()) {
			return eval.isPresent() && eval.get() > 0;
		}
		throw new EvaluationNotSupported("evaluation not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt
	 * .specification.expression.IdExpression)
	 */
	@Override
	public Boolean forIdExpression(IdExpression var) {
		// in case it is false or true
		if (var == BoolType.FALSE_CONST)
			return false;
		if (var == BoolType.TRUE_CONST)
			return true;
		assert (!var.getIdString().equalsIgnoreCase(BoolType.FALSE_CONST.getIdString()));
		assert (!var.getIdString().equalsIgnoreCase(BoolType.TRUE_CONST.getIdString()));
		// assuming e is a boolean
		String val = state.get(var);
		if (val == null) {
			if (modelMustBeComplete) {
				throw new ModelIncomplete(var, state);
			} else {
				// we can return any value
				return false;
			}
		}
		if (val.equalsIgnoreCase(BoolType.FALSE_CONST.getIdString()))
			return false;
		if (val.equalsIgnoreCase(BoolType.TRUE_CONST.getIdString()))
			return true;
		throw new RuntimeException("var " + var.getIdString() + " has no bool value but" + val);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessEqualExpression
	 * (atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public Boolean forLessEqualExpression(LessEqualExpression e) {
		Optional<Integer> eval = evalAsmath(e);
		if (eval.isPresent()) {
			return eval.isPresent() && eval.get() <= 0;
		}
		throw new EvaluationNotSupported("evaluation not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessThanExpression
	 * (atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public Boolean forLessThanExpression(LessThanExpression e) {
		Optional<Integer> eval = evalAsmath(e);
		if (eval.isPresent()) {
			return eval.isPresent() && eval.get() < 0;
		}
		throw new EvaluationNotSupported("evaluation not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMinusExpression
	 * (atgt.specification.expression.MinusExpression)
	 */
	@Override
	public Boolean forMinusExpression(MinusExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMultExpression(
	 * atgt.specification.expression.MultExpression)
	 */
	@Override
	public Boolean forMultExpression(MultExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNegExpression(atgt
	 * .specification.expression.NegExpression)
	 */
	@Override
	public Boolean forNegExpression(NegExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression
	 * (atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public Boolean forNotEqualsExpression(NotEqualsExpression e) {
		return (!forEqualsExpression(new EqualsExpression(e.getFirstOperand(), e.getSecondOperand())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt
	 * .specification.expression.NotExpression)
	 */
	@Override
	public Boolean forNotExpression(NotExpression e) {
		return (!e.getOperand().accept(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt
	 * .specification.expression.OrExpression)
	 */
	@Override
	public Boolean forOrExpression(OrExpression e) {
		return e.getFirstOperand().accept(this) || e.getSecondOperand().accept(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forPlusExpression(
	 * atgt.specification.expression.PlusExpression)
	 */
	@Override
	public Boolean forPlusExpression(PlusExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forUnaryExpression
	 * (atgt.specification.expression.UnaryExpression)
	 */
	/**
	 * For unary expression.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the boolean
	 */
	public Boolean forUnaryExpression(Expression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forXOrExpression(atgt
	 * .specification.expression.XOrExpression)
	 */
	@Override
	public Boolean forXOrExpression(XOrExpression e) {
		return e.getFirstOperand().accept(this) ^ e.getSecondOperand().accept(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression
	 * (atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public Boolean forImpliesExpression(ImpliesExpression e) {
		// a => b == not a or b
		return !e.getFirstOperand().accept(this) || e.getSecondOperand().accept(this);
	}

	/**
	 * Adds the.
	 * 
	 * @param list
	 *            the list
	 */
	public void add(Map<? extends Variable, String> list) {
		for (Entry<? extends Variable, String> i : list.entrySet()) {
			state.put(i.getKey().getIdExpression(), i.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNextExpression(
	 * atgt.specification.expression.NextExpression)
	 */
	@Override
	public Boolean forNextExpression(NextExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	// ///////////////////////
	/*
	 * for these it is not supported
	 */

	@Override
	public Boolean forModuloExpression(ModuloExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	@Override
	public Boolean forPrimedIdExpression(PrimedIdExpression e) {
		throw new EvaluationNotSupported(e.getClass() + "not supported");
	}

	@Override
	public Boolean forFunctionTerm(FunctionTerm ft) {
		throw new EvaluationNotSupported(ft.getClass() + "not supported");
	}

	@Override
	public Boolean forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Boolean forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}