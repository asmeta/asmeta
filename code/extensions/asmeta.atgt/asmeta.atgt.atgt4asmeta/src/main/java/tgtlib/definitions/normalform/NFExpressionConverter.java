package tgtlib.definitions.normalform;

import static tgtlib.definitions.expression.BinaryExpression.mkBinExpr;
import static tgtlib.definitions.expression.Operator.AND;
import static tgtlib.definitions.expression.Operator.OR;
import static tgtlib.definitions.expression.Operator.XOR;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.UnaryExpression;

/**
 */
public abstract class NFExpressionConverter<T extends NFExpression<?,?>> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(NFExpressionConverter.class);

	/**
	 * return the equivalent of e1 xor e2 with some simplification.
	 *
	 * @param e1 the e1
	 * @param e2 the e2
	 * @param pushnot the pushnot utile the end???
	 * @return BinaryExpression
	 */
	public static BinaryExpression getXorSimpl(Expression e1, Expression e2, boolean pushnot) {
		logger.debug("xor eq between " + e1 + " , " + e2);
		// get the equivalent
		// (a and b) xor (a and c) == a and (b xor c)
		// proved in yices: (assert (/= (/= (and a b)(and a c)) (and a (/= b
		// c))))
		if (e1 instanceof AndExpression && e2 instanceof AndExpression) {
			Expression e11 = ((AndExpression) e1).getFirstOperand();
			Expression e12 = ((AndExpression) e1).getSecondOperand();
			Expression e21 = ((AndExpression) e2).getFirstOperand();
			Expression e22 = ((AndExpression) e2).getSecondOperand();
			if (e11.equals(e21))
				return mkBinExpr(e11, AND, getXorSimpl(e12, e22,pushnot));
			if (e12.equals(e22))
				return mkBinExpr(e12, AND, getXorSimpl(e11, e21,pushnot));
			// or mix
			if (e11.equals(e22))
				return mkBinExpr(e11, AND, getXorSimpl(e12, e21,pushnot));
			if (e12.equals(e21))
				return mkBinExpr(e12, AND, getXorSimpl(e11, e22,pushnot));
		}
		// if (a or b) xor (a or c) => not a and (b xor c)
		// proved: (assert (/= (/= (or a b)(or a c)) (and (not a) (/= b c))))
		if (e1 instanceof OrExpression && e2 instanceof OrExpression) {
			Expression e11 = ((OrExpression) e1).getFirstOperand();
			Expression e12 = ((OrExpression) e1).getSecondOperand();
			Expression e21 = ((OrExpression) e2).getFirstOperand();
			Expression e22 = ((OrExpression) e2).getSecondOperand();
			if (e11.equals(e21))
				return mkBinExpr(mkNotExpr(e11), AND, getXorSimpl(e12, e22,pushnot));
			if (e12.equals(e22))
				return mkBinExpr(mkNotExpr(e12), AND, getXorSimpl(e11, e21,pushnot));
			// mix (a or b) xor (c or a)
			if (e11.equals(e22))
				return mkBinExpr(mkNotExpr(e11), AND, getXorSimpl(e12, e21,pushnot));
			if (e12.equals(e21))
				return mkBinExpr(mkNotExpr(e12), AND, getXorSimpl(e11, e22,pushnot));

		}
		if (pushnot){
			// a xor b = (a and not b) or (not a and b)
			// 1 and not 2
			Expression not_e2 = mkNotAndPushNot(e2);
			Expression one = mkBinExpr(e1, AND, not_e2);
			Expression not_e1 = mkNotAndPushNot(e1);
			Expression two = mkBinExpr(not_e1, AND, e2);
			BinaryExpression xorEq = mkBinExpr(one, OR, two);
			logger.debug(e1 + " xor " + e2 + " -> " + xorEq);
			return xorEq;	
		} else {
			// not simplificable, just make the XOR
			return mkBinExpr(e1,XOR,e2);
		}
	}


	/**
	 * Method mkNotAndPushNot.
	 * @param e Expression
	 * @return Expression
	 */
	private static Expression mkNotAndPushNot(Expression e) {
		Expression not_e = mkNotExpr(e);
		return not_e.accept(PushNot.pushNot);
	}

	/**
	 * make not and simplify if necessary
	 * 
	 * @param e
	
	 * @return Expression
	 */
	protected static Expression mkNotExpr(Expression e) {
		//
		if (e instanceof NotExpression) {
			return ((NotExpression) e).getOperand();
		} else {
			return UnaryExpression.mkUnExpr(Operator.NOT, e);
		}
	}

}
