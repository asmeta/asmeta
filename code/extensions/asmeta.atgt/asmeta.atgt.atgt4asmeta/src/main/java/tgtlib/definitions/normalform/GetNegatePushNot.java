package tgtlib.definitions.normalform;

import org.apache.log4j.Logger;

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
import tgtlib.definitions.expression.XOrExpression;

/**
 * get the negation, but push not
 * 
 * A -> not A; A and B - > not A or not B; ...
 * 
 * @author garganti
 * 
 * @version $Revision: 1.0 $
 */
public final class GetNegatePushNot implements ExpressionVisitor<Expression> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GetNegatePushNot.class);

	boolean negate = false;

	public static final ExpressionVisitor<Expression> pushAndNegate = new GetNegatePushNot();


	private GetNegatePushNot() {}

	/**
	 * Method forAndExpression.
	 * @param andExpression AndExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forAndExpression(AndExpression)
	 */
	@Override
	public Expression forAndExpression(AndExpression andExpression) {
		Expression firstOperand = andExpression.getFirstOperand();
		Expression secondOperand = andExpression.getSecondOperand();
		// push inside and negate
		Expression e1 = firstOperand.accept(this);
		Expression e2 = secondOperand.accept(this);
		return new OrExpression(e1, e2);
	}

	/**
	 * Method forOrExpression.
	 * @param orExpression OrExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forOrExpression(OrExpression)
	 */
	@Override
	public Expression forOrExpression(OrExpression orExpression) {
		Expression e1 = orExpression.getFirstOperand().accept(this);
		Expression e2 = orExpression.getSecondOperand().accept(this);
		return new AndExpression(e1, e2);
	}

	/**
	 * Method forXOrExpression.
	 * @param xOrExpression XOrExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forXOrExpression(XOrExpression)
	 */
	@Override
	public Expression forXOrExpression(XOrExpression xOrExpression) {
		// a xor b: get the negation of a and b (push not) and then change to equals
		// do not negate the operands
		return invertBinaryExpr(xOrExpression,Operator.EQ);
	}

	private Expression invertBinaryExpr(BinaryExpression expr, Operator op) {
		Expression e1 = expr.getFirstOperand().accept(PushNot.pushNot);
		Expression e2 = expr.getSecondOperand().accept(PushNot.pushNot);
		return BinaryExpression.mkBinExpr(e1, op, e2);
	}
	/**
	 * Method forEqualsExpression.
	 * @param equalsExpression EqualsExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forEqualsExpression(EqualsExpression)
	 */
	@Override
	public Expression forEqualsExpression(EqualsExpression equalsExpression) {
		// a = b: get the negation of a and b (push not) and then change to not equals
		// do not negate the operands
		return invertBinaryExpr(equalsExpression,Operator.NEQ);
	}

	
	/**
	 * Method forIdExpression.
	 * @param idExpression IdExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forIdExpression(IdExpression)
	 */
	@Override
	public Expression forIdExpression(IdExpression idExpression) {
		return NotExpression.createNotExpression(idExpression);
	}

	/**
	 * Method forNotExpression.
	 * @param notExpression NotExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotExpression(NotExpression)
	 */
	@Override
	public Expression forNotExpression(NotExpression notExpression) {
		// do not negate at this point !
		Expression operand = notExpression.getOperand();
		// push the not inside
		Expression accept = operand.accept(PushNot.pushNot);
		return accept;
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
	 * Method forImpliesExpression.
	 * @param impliesExpression ImpliesExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forImpliesExpression(ImpliesExpression)
	 */
	@Override
	public Expression forImpliesExpression(ImpliesExpression impliesExpression) {
		throw new RuntimeException("not implemented yet");
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
	 * Method forNotEqualsExpression.
	 * @param notEqualsExpression NotEqualsExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotEqualsExpression(NotEqualsExpression)
	 */
	@Override
	public Expression forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
		// same are not equals
		return invertBinaryExpr(notEqualsExpression,Operator.EQ);
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
		throw new RuntimeException("not implemented yet");
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
