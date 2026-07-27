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
 * push not. returns the same with a not pushed till the ID if does not change,
 * return exactly the same. For example not(a and b) => not a or not b ...
 * It reduces to negation normal form.
 * 
 * @author garganti
 * 
 * @version $Revision: 1.0 $
 */
public class PushNot implements ExpressionVisitor<Expression> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PushNot.class);

	public static final ExpressionVisitor<Expression> pushNot = new PushNot();

	private PushNot() {
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
	 * Method forAndExpression.
	 * @param andExpression AndExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forAndExpression(AndExpression)
	 */
	@Override
	public Expression forAndExpression(AndExpression andExpression) {
		return forBinExpression(andExpression, Operator.AND);
	}

	/**
	 * Method forOrExpression.
	 * @param orExpression OrExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forOrExpression(OrExpression)
	 */
	@Override
	public Expression forOrExpression(OrExpression orExpression) {
		return forBinExpression(orExpression, Operator.OR);
	}

	/**
	 * Method forXOrExpression.
	 * @param xOrExpression XOrExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forXOrExpression(XOrExpression)
	 */
	@Override
	public Expression forXOrExpression(XOrExpression xOrExpression) {
		return forBinExpression(xOrExpression, Operator.XOR);
	}

	/**
	 * Method forEqualsExpression.
	 * @param equalsExpression EqualsExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forEqualsExpression(EqualsExpression)
	 */
	@Override
	public Expression forEqualsExpression(EqualsExpression equalsExpression) {
		return forBinExpression(equalsExpression, Operator.EQ);
	}
	/**
	 * Method forNotEqualsExpression.
	 * @param notEqualsExpression NotEqualsExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotEqualsExpression(NotEqualsExpression)
	 */
	@Override
	public Expression forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
		return forBinExpression(notEqualsExpression, Operator.NEQ);
	}

	/**
	 * Method forImpliesExpression.
	 * @param impliesExpression ImpliesExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forImpliesExpression(ImpliesExpression)
	 */
	@Override
	public Expression forImpliesExpression(ImpliesExpression impliesExpression) {
		return forBinExpression(impliesExpression, Operator.IMPLIES);
	}

	/**
	 * Method forBinExpression.
	 * @param binExpr BinaryExpression
	 * @param op Operator
	 * @return Expression
	 */
	private Expression forBinExpression(BinaryExpression binExpr, Operator op) {
		Expression firstOperand = binExpr.getFirstOperand();
		Expression secondOperand = binExpr.getSecondOperand();
		Expression e1 = firstOperand.accept(pushNot);
		Expression e2 = secondOperand.accept(pushNot);
		if (e1 == firstOperand && e2 == secondOperand)
			return binExpr;
		else
			return BinaryExpression.mkBinExpr(e1, op, e2);
	}

	/**
	 * Method forNotExpression.
	 * @param notExpression NotExpression
	 * @return Expression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotExpression(NotExpression)
	 */
	@Override
	public Expression forNotExpression(NotExpression notExpression) {
		Expression operand = notExpression.getOperand();
		// not id: finished
		if (operand instanceof IdExpression)
			return notExpression;
		return operand.accept(GetNegatePushNot.pushAndNegate);
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
		throw new RuntimeException("not implemented");
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
