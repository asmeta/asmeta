package extgt.coverage.mcdc;

import java.util.Collection;
import java.util.List;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
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
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.definitions.expression.visitors.EvaluationNotSupported;
import tgtlib.definitions.expression.visitors.IsAtomicBool;
import tgtlib.util.Pair;

/**
 * given an expression, return the list of pairs that make the expression true
 * and false
 * 
 * @author garganti
 * 
 */
class MaskMCDCExprVisitor extends MCDCExprVisitor<Pair<NamedTerm, NamedTerm>> {

	
	MaskMCDCExprVisitor(Collection<Variable> vars){
		
	}
	
	// logic expressions

	@Override
	public List<Pair<NamedTerm, NamedTerm>> forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		return makeTF(primedIdExpression);
	}

	@Override
	public List<Pair<NamedTerm, NamedTerm>> forXOrExpression(
			XOrExpression xOrExpression) {
		// testable using test predicates, it woudl need a couple of pairs
		// instead of a pair
		// a xor b => prima possibilit� a and not b , not a and not - seconda
		// poss: not a and b, a and not b
		// vedi il ibro di
		throw new RuntimeException("a xor b is not testable");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNextExpression
	 * (atgt.specification.expression.NextExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forNextExpression(
			NextExpression nextExpression) {
		throw new RuntimeException("MCDC for next ???");
	}

	/**
	 * Nel caso di espressione negata, ignora il segno <CODE>ot</CODE>.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forNotExpression(NotExpression e) {
		List<Pair<NamedTerm, NamedTerm>> result = e.getOperand().accept(this);
		// TODO switch first with second and viceversa
		// not a <a, not a> should be <not a,a>
		return result;
	}

	// Math Expression
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forPlusExpression(PlusExpression e) {
		throw new RuntimeException("not implemented");
	}

	/**
	 * For minus expression.
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forMinusExpression(MinusExpression e) {
		throw new RuntimeException("not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forDivExpression
	 * (atgt.specification.expression.DivExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forDivExpression(DivExpression e) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<NamedTerm, NamedTerm>> forModuloExpression(
			ModuloExpression moduloExpression) {
		throw new RuntimeException("not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forMultExpression
	 * (atgt.specification.expression.MultExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forMultExpression(MultExpression e) {
		throw new RuntimeException("not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNegExpression
	 * (atgt.specification.expression.NegExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forNegExpression(NegExpression e) {
		throw new RuntimeException("not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression
	 * (atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forEqualsExpression(
			EqualsExpression e) {
		// if a == b is atomic
		if (e.accept(IsAtomicBool.isAtomicBool)) return  makeTF(e);
		throw new EvaluationNotSupported("== not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression
	 * (atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forNotEqualsExpression(
			NotEqualsExpression e) {
		// if a != b is atomic
		if (e.accept(IsAtomicBool.isAtomicBool)) return  makeTF(e);
		throw new EvaluationNotSupported("not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessThanExpression
	 * (atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forLessThanExpression(
			LessThanExpression e) {
		return makeTF(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessEqualExpression
	 * (atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forLessEqualExpression(
			LessEqualExpression e) {
		return makeTF(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#
	 * forGreaterThanExpression
	 * (atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forGreaterThanExpression(
			GreaterThanExpression e) {
		return makeTF(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#
	 * forGreaterEqualExpression
	 * (atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public List<Pair<NamedTerm, NamedTerm>> forGreaterEqualExpression(
			GreaterEqualExpression e) {
		return makeTF(e);
	}

	/**
	 * Se <CODE>e</CODE> e' una espressione atomica allora ritorna {
	 * <CODE>e</CODE>, <CODE>not e</CODE> .
	 * 
	 * @param e
	 *            the e
	 * 
	 * @return the list< named expression>
	 */
	@Override
	protected Pair<NamedTerm, NamedTerm> makeTFPair(Expression e) {
		Expression notE = e.accept(ShallowExpressionNegator.negate);
		return new Pair<NamedTerm, NamedTerm>(new NamedTerm("T", e),
				new NamedTerm("F", notE));
	}

	/**
	 * given a set of test goals already computed, it adds the new ones.
	 * 
	 * @param set1
	 *            is the set of TestGoal already computed
	 * @param y
	 *            the expression to be added
	 * @param result
	 *            is the set containing the results
	 * @param type
	 *            if the operator is "and" or "or" firstExpr: to put first y
	 *            then x
	 * @param firstExpr
	 *            the first expr?
	 */
	@Override
	protected void addToSet(List<Pair<NamedTerm, NamedTerm>> set1,
			Expression y, List<Pair<NamedTerm, NamedTerm>> result,
			Operator type, boolean firstExpr) {
		// walk the vector X
		for (Pair<NamedTerm, NamedTerm> x : set1) {
			NamedTerm x1 = addToSetSingle(x.getFirst(), y, type, firstExpr);
			NamedTerm x2 = addToSetSingle(x.getSecond(), y, type, firstExpr);
			result.add(new Pair<NamedTerm, NamedTerm>(x1, x2));
		}
	}

	static private NamedTerm addToSetSingle(NamedTerm x, Expression y,
			Operator type, boolean firstExpr) {
		// build the new test purpose
		Expression xCondition = x.getCondition();
		if (type == Operator.AND) {
			// ADD x and y (or y and x)
			if (!firstExpr) {
				AndExpression condition = new AndExpression(xCondition, y);
				return new NamedTerm(x.getName() + "T", condition);
			} else {
				AndExpression condition = new AndExpression(y, xCondition);
				return new NamedTerm("T" + x.getName(), condition);
			}
		} else if (type == Operator.OR) {
			// check if x is already in a form of not y...
			// convert to not y
			Expression noty = y.accept(ShallowExpressionNegator.negate);
			if (!firstExpr) {
				AndExpression condition = new AndExpression(xCondition, noty);
				return new NamedTerm(x.getName() + "F", condition);
			} else {
				AndExpression condition = new AndExpression(noty, xCondition);
				return new NamedTerm("F" + x.getName(), condition);
			}
		} else if (type == Operator.XOR) {
			throw new RuntimeException();
		}
		throw new RuntimeException();
	}

	@Override
	public List<Pair<NamedTerm, NamedTerm>> forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}