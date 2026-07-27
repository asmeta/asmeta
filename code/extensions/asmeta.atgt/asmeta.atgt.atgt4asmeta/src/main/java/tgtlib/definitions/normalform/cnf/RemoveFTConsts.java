package tgtlib.definitions.normalform.cnf;

import static tgtlib.definitions.expression.type.BoolType.FALSE_CONST;
import static tgtlib.definitions.expression.type.BoolType.TRUE_CONST;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
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
import tgtlib.definitions.expression.type.BoolType;

/**
 * it removes the constants true and false, preserving the semantics. If it does
 * not substitute, it returns exactly the same object
 * 
 * @author garganti
 * 
 * @version $Revision: 1.0 $
 */
public class RemoveFTConsts implements ExpressionVisitor<Expression> {

	public static RemoveFTConsts instance = new RemoveFTConsts();

	/**
	 * Method forAndExpression.
	 * @param andExpression AndExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forAndExpression(AndExpression)
	 */
	@Override
	public Expression forAndExpression(AndExpression andExpression) {
		return forAndOrExpr(andExpression, BoolType.TRUE_CONST,
				BoolType.FALSE_CONST, Operator.AND);
	}

	/**
	 * Method forOrExpression.
	 * @param orExpression OrExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forOrExpression(OrExpression)
	 */
	@Override
	public Expression forOrExpression(OrExpression orExpression) {
		return forAndOrExpr(orExpression, BoolType.FALSE_CONST,
				BoolType.TRUE_CONST, Operator.OR);
	}

	/**
	 * Method forImpliesExpression.
	 * @param impliesExpression ImpliesExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forImpliesExpression(ImpliesExpression)
	 */
	@Override
	public Expression forImpliesExpression(ImpliesExpression e) {
		Expression a1 = e.getFirstOperand();
		Expression e1eq = a1.accept(this);
		Expression a2 = e.getSecondOperand();
		Expression e2eq = a2.accept(this);
		// F => a2
		if (e1eq == BoolType.FALSE_CONST) return BoolType.TRUE_CONST;
		// T => a2
		if (e1eq == BoolType.TRUE_CONST) return e2eq;
		// a1 => F ===> not a1
		if (e2eq == BoolType.FALSE_CONST) return NotExpression.createNotExpression(e1eq);
		// a1 => T ===> T
		if (e2eq == BoolType.TRUE_CONST) return BoolType.TRUE_CONST;		
		if (e1eq == a1 && e2eq == a2)
			return e;
		else 
			return BinaryExpression.mkBinExpr(e1eq, Operator.IMPLIES, e2eq);
	}

	
	/***
	 * 
	 * @param binExpr
	 * @param ignoreCase
	 *            in this case just remove it (like a and true): true can be ignored
	 * @param predCase
	 *            in this case this dominates (like a or true): true dominates
	
	 * @param op Operator
	 * @return Expression
	 */
	private Expression forAndOrExpr(BinaryExpression binExpr,
			BoolType.BoolConst ignoreCase, BoolType.BoolConst predCase, Operator op) {
		Expression a1 = binExpr.getFirstOperand();
		Expression e1eq = a1.accept(this);
		Expression a2 = binExpr.getSecondOperand();
		Expression e2eq = a2.accept(this);
		// a1 ??
		// predCase op something
		if (e1eq == predCase) return predCase;
		// ingoreCase op something
		if (e1eq == ignoreCase) return e2eq;
		// a2??
		// a2 changed??
		// a2 op nullCase
		if (e2eq == predCase) return predCase;
		// a1 op ingoreCase
		if (e2eq == ignoreCase) return e1eq;
		if (e1eq == a1 && e2eq == a2)
			return binExpr;
		else 
			return BinaryExpression.mkBinExpr(e1eq, op, e2eq);
	}

	/**
	 * Method forIdExpression.
	 * @param idExpression IdExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forIdExpression(IdExpression)
	 */
	@Override
	public Expression forIdExpression(IdExpression idExpression) {
		return idExpression;
	}

	/**
	 * Method forXOrExpression.
	 * @param xOrExp XOrExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forXOrExpression(XOrExpression)
	 */
	@Override
	public Expression forXOrExpression(XOrExpression xOrExp) {
		return forBoolDiverseExpr(xOrExp);
	}

	/** same case xor or != */
	private Expression forBoolDiverseExpr(BinaryExpression xOrExp) {
		Expression a1 = xOrExp.getFirstOperand();
		Expression e1eq = a1.accept(this);
		Expression a2 = xOrExp.getSecondOperand();
		Expression e2eq = a2.accept(this);
		// a1 ??
		// predCase op something
		if (e1eq == TRUE_CONST){
			// not the second one
			// true xor true -> false
			if (e2eq == TRUE_CONST) return FALSE_CONST;
			// true xor false -> true
			if (e2eq == FALSE_CONST) return TRUE_CONST;
			// otherwise just not the second one (must be false)
			return UnaryExpression.mkUnExpr(Operator.NOT, e2eq);
		}
		// ingoreCase op something
		if (e1eq == FALSE_CONST) {
			// useless, regardless the first, is false, return the second
			// false xor true -> true
			//if (e2eq == trueConst) return trueConst;
			// false xor false -> false
			//if (e2eq == falseConst) return falseConst;
			return e2eq;
		}
		// a2 is not equal to false not to true
		// a2 changed??
		// a2 op nullCase
		if (e2eq == TRUE_CONST) return UnaryExpression.mkUnExpr(Operator.NOT, e1eq);
		// a1 op ignoreCase
		if (e2eq == FALSE_CONST) return e1eq;
		if (e1eq == a1 && e2eq == a2)
			return xOrExp;
		else 
			return BinaryExpression.mkBinExpr(e1eq, Operator.XOR, e2eq);
	}

	/**
	 * Method forNotExpression.
	 * @param notExpression NotExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotExpression(NotExpression)
	 */
	@Override
	public Expression forNotExpression(NotExpression notExpression) {
		Expression e1 = notExpression.getOperand();
		Expression e1eq = e1.accept(this);
		// chekc first if not true
		if (e1eq == BoolType.TRUE_CONST) return BoolType.FALSE_CONST;
		// then not false
		if (e1eq == BoolType.FALSE_CONST) return BoolType.TRUE_CONST;
		// not [not changed]
		if (e1eq == e1) return notExpression;
		return UnaryExpression.mkUnExpr(Operator.NOT, e1eq);
	}

	/**
	 * Method forEqualsExpression.
	 * @param equalsExpression EqualsExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forEqualsExpression(EqualsExpression)
	 */
	@Override
	public Expression forEqualsExpression(EqualsExpression eqExpr) {
		Expression a1 = eqExpr.getFirstOperand();
		Expression e1eq = a1.accept(this);
		Expression a2 = eqExpr.getSecondOperand();
		Expression e2eq = a2.accept(this);
		// a1 ??
		// predCase op something
		if (e1eq == FALSE_CONST){
			// not the second one
			// true = false -> false
			if (e2eq == TRUE_CONST) return FALSE_CONST;
			// false= false -> false
			if (e2eq == FALSE_CONST) return TRUE_CONST;
			//if (e2eq == falseConst) return falseConst;
			return UnaryExpression.mkUnExpr(Operator.NOT, e2eq);
		}
		// ingoreCase op something
		if (e1eq == TRUE_CONST) {
			// useless, regardless the first, is false, return the second
			return e2eq;
		}
		// a2 is not equal to false not to true
		// a2 changed??
		// e: a1 == false 
		if (e2eq == FALSE_CONST) return UnaryExpression.mkUnExpr(Operator.NOT, e1eq);
		// e: e1 == true
		if (e2eq == TRUE_CONST) return e1eq;
		if (e1eq == a1 && e2eq == a2)
			return eqExpr;
		else 
			return BinaryExpression.mkBinExpr(e1eq, Operator.EQ, e2eq);
	}

	/**
	 * Method forNotEqualsExpression.
	 * @param notEqualsExpression NotEqualsExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotEqualsExpression(NotEqualsExpression)
	 */
	@Override
	public Expression forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		return forBoolDiverseExpr(notEqualsExpression);
	}

	/**
	 * Method forGreaterEqualExpression.
	 * @param greaterEqualExpression GreaterEqualExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterEqualExpression(GreaterEqualExpression)
	 */
	@Override
	public Expression forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Method forDivExpression.
	 * @param divExpression DivExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forDivExpression(DivExpression)
	 */
	@Override
	public Expression forDivExpression(DivExpression divExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forPlusExpression.
	 * @param plusExpression PlusExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forPlusExpression(PlusExpression)
	 */
	@Override
	public Expression forPlusExpression(PlusExpression plusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forMinusExpression.
	 * @param minusExpression MinusExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forMinusExpression(MinusExpression)
	 */
	@Override
	public Expression forMinusExpression(MinusExpression minusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forGreaterThanExpression.
	 * @param greaterThanExpression GreaterThanExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterThanExpression(GreaterThanExpression)
	 */
	@Override
	public Expression forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forLessEqualExpression.
	 * @param lessEqualExpression LessEqualExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessEqualExpression(LessEqualExpression)
	 */
	@Override
	public Expression forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forLessThanExpression.
	 * @param lessThanExpression LessThanExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessThanExpression(LessThanExpression)
	 */
	@Override
	public Expression forLessThanExpression(
			LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Method forMultExpression.
	 * @param multExpression MultExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forMultExpression(MultExpression)
	 */
	@Override
	public Expression forMultExpression(MultExpression multExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forNegExpression.
	 * @param negExpression NegExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNegExpression(NegExpression)
	 */
	@Override
	public Expression forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forNextExpression.
	 * @param nextExpression NextExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNextExpression(NextExpression)
	 */
	@Override
	public Expression forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forPrimedIdExpression.
	 * @param primedIdExpression PrimedIdExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forPrimedIdExpression(PrimedIdExpression)
	 */
	@Override
	public Expression forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forModuloExpression.
	 * @param moduloExpression ModuloExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forModuloExpression(ModuloExpression)
	 */
	@Override
	public Expression forModuloExpression(ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Expression forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}