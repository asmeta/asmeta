package mcdc.scrtgtool;

import java.util.ArrayList;
import java.util.List;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GetOperator;
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
import tgtlib.util.Pair;

/**
 * given a simple expression, returns the list of test predicates for masking
 * MCDC
 * 
 * @author garganti
 * 
 */
public class PairMaskingMCDCExr<Q extends TestPredicate<?,?>> implements
		tgtlib.definitions.expression.ExpressionVisitor<MCDCTPList<Q>> {

	protected TestPredicateFactory<Q> factory;

	private ExpressionVisitor<Boolean> isAtomic;

	private int nLiteral;

	/**
	 * starts counting the literals !
	 * 
	 * @param fac
	 */
	protected PairMaskingMCDCExr(TestPredicateFactory<Q> fac,
			ExpressionVisitor<Boolean> isAtomicRec) {
		factory = fac;
		isAtomic = isAtomicRec;
		nLiteral = 1;
	}

	/**
	 * compute in an unique method the pairs - TODO - use the pattern
	 * 
	 * @param exp
	 * @return
	 */
	protected MCDCTPList<Q> computeVectorTP(Expression exp) {
		// if it is false, return the empty set
		if (exp == BoolType.TRUE_CONST)
			return MCDCTPList.EMPTY_LIST;
		// if it is true returns one TEST_GOAL
		if (exp == BoolType.TRUE_CONST)
			return MCDCTPList.EMPTY_LIST;
		// it works only with Simple Expressions

		MCDCTPList<Q> result = new MCDCTPList<Q>();

		/**
		 * to store the literal number in the description of the test purpose
		 */
		if (exp.accept(isAtomic)) {
			Q tt = factory
					.buildTestPredicate("literal " + nLiteral + " T", exp);
			Q tf = factory.buildTestPredicate("literal " + nLiteral + " F",
					UnaryExpression.mkUnExpr(Operator.NOT, exp));
			result.add(tt, tf);
			nLiteral++;
			return result;
		}
		// this is not a atomic boolean condition
		// it can be a binary or unary expression
		if (exp instanceof BinaryExpression) {
			BinaryExpression bexp = (BinaryExpression) exp;
			Operator op = bexp.accept(GetOperator.INSTANCE);
			Expression e1 = bexp.getFirstOperand();
			MCDCTPList<Q> set1 = computeVectorTP(e1);
			Expression e2 = bexp.getSecondOperand();
			MCDCTPList<Q> set2 = computeVectorTP(e2);
			// se l'operatore e' AND
			if (op == Operator.AND) {
				result.addAll(composeTestGoals(set1, e2, AddingType.AND));
				result.addAll(composeTestGoals(set2, e1, AddingType.AND));
			} // if the operator is or
			else if (op == Operator.OR) {
				// for e1:
				result.addAll(composeTestGoals(set1, e2, AddingType.OR));
				result.addAll(composeTestGoals(set2, e1, AddingType.OR));
			} else {
				throw new RuntimeException(
						" for binary expression with operator not and and not or");
			}
			return result;
		} else if (exp instanceof UnaryExpression) {
			UnaryExpression uexp = (UnaryExpression) exp;
			Operator op = uexp.accept(GetOperator.INSTANCE);
			Expression e = uexp.getOperand();
			if (op == Operator.NOT)
				return computeVectorTP(e).exchangeTrueFalse();
			else if (op == Operator.prime) {
				// compute vector MCDC for expression
				MCDCTPList<Q> inside = computeVectorTP(e);
				for (Pair<Q, Q> i : inside) {
					// take the current element
					// get the expression
					Q tt = pushPrime(i.getFirst());
					Q tf = pushPrime(i.getSecond());
					result.add(tt, tf);
				}
				return result;
			}
			throw new RuntimeException(
					" for unary expression with operator /= from not nethier prime");
		} else {
			throw new RuntimeException(
					" for not unary expression nether binary ");
		}
	}

	/**
	 * given a set of test goals already computed, it adds the new.
	 * 
	 * @param set1
	 *            is the set of TestGoal already computed
	 * @param y
	 *            is the expression to compose
	 * @param or
	 *            if the operator is "and" or "or"
	 * 
	 * @return the list< pair< test goal, test goal>>
	 */
	private MCDCTPList<Q> composeTestGoals(MCDCTPList<Q> set1, Expression y,
			AddingType or) {
		MCDCTPList<Q> result = new MCDCTPList<Q>();
		// walk the vector X
		for (Pair<Q, Q> p : set1) {
			// take the current element
			Q current = p.getFirst();
			Q tt = joinTestGoals(current, or, y);
			current = p.getSecond();
			Q tf = joinTestGoals(current, or, y);
			result.add(tt, tf);
		}
		return result;
	}

	private enum AddingType {
		AND, OR;
	}

	/**
	 * join the current TestGoal with another experssion
	 * 
	 * @param current
	 * @param or
	 * @param y
	 * @return
	 */
	private Q joinTestGoals(Q current, AddingType or, Expression y) {
		// get the expression
		Expression x = current.getCondition();
		String descr = current.getName();
		// build the new test purpose
		if (or == AddingType.AND)
			return factory.buildTestPredicate(descr,
					BinaryExpression.mkBinExpr(x, Operator.AND, y));
		assert (or == AddingType.OR);
		Expression ny;
		// check if x is already in a form of not y...
		if (y instanceof NotExpression) {
			ny = ((NotExpression) y).getOperand();
		} else {
			ny = NotExpression.createNotExpression(y);
		}
		return factory.buildTestPredicate(descr,
				BinaryExpression.mkBinExpr(x, Operator.AND, ny));

	}

	/**
	 * 
	 * @param current
	 * @return current' as test goal
	 */
	private Q pushPrime(Q current) {
		Expression x = current.getCondition();
		String descr = current.getName();
		// build the new test purpose
		return factory.buildTestPredicate(descr,
				UnaryExpression.mkUnExpr(Operator.prime, x));

	}

	@Override
	public MCDCTPList<Q> forAndExpression(AndExpression andExpression) {
		return computeVectorTP(andExpression);
	}

	@Override
	public MCDCTPList<Q> forDivExpression(DivExpression divExpression) {
		return computeVectorTP(divExpression);
	}

	@Override
	public MCDCTPList<Q> forEqualsExpression(EqualsExpression equalsExpression) {
		return computeVectorTP(equalsExpression);
	}

	@Override
	public MCDCTPList<Q> forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		return computeVectorTP(greaterEqualExpression);
	}

	@Override
	public MCDCTPList<Q> forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		return computeVectorTP(greaterThanExpression);
	}

	@Override
	public MCDCTPList<Q> forIdExpression(IdExpression idExpression) {
		return computeVectorTP(idExpression);
	}

	@Override
	public MCDCTPList<Q> forImpliesExpression(
			ImpliesExpression impliesExpression) {
		return computeVectorTP(impliesExpression);
	}

	@Override
	public MCDCTPList<Q> forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		return computeVectorTP(lessEqualExpression);
	}

	@Override
	public MCDCTPList<Q> forLessThanExpression(
			LessThanExpression lessThanExpression) {
		return computeVectorTP(lessThanExpression);
	}

	@Override
	public MCDCTPList<Q> forMinusExpression(MinusExpression minusExpression) {
		return computeVectorTP(minusExpression);
	}

	@Override
	public MCDCTPList<Q> forMultExpression(MultExpression multExpression) {
		return computeVectorTP(multExpression);
	}

	@Override
	public MCDCTPList<Q> forNegExpression(NegExpression negExpression) {
		return computeVectorTP(negExpression);
	}

	@Override
	public MCDCTPList<Q> forNextExpression(NextExpression nextExpression) {
		return computeVectorTP(nextExpression);
	}

	@Override
	public MCDCTPList<Q> forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		return computeVectorTP(notEqualsExpression);
	}

	@Override
	public MCDCTPList<Q> forNotExpression(NotExpression notExpression) {
		return computeVectorTP(notExpression);
	}

	@Override
	public MCDCTPList<Q> forOrExpression(OrExpression orExpression) {
		return computeVectorTP(orExpression);
	}

	@Override
	public MCDCTPList<Q> forPlusExpression(PlusExpression plusExpression) {
		return computeVectorTP(plusExpression);
	}

	@Override
	public MCDCTPList<Q> forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		return computeVectorTP(primedIdExpression);
	}

	@Override
	public MCDCTPList<Q> forXOrExpression(XOrExpression xOrExpression) {
		return computeVectorTP(xOrExpression);
	}

	@Override
	public MCDCTPList<Q> forModuloExpression(ModuloExpression moduloExpression) {
		return computeVectorTP(moduloExpression);
	}

	/**
	 * return the list of test Goals to satisfy the masking MCDC for expression
	 * exp. see description of the algorithm in paper (it converts the Pair to a
	 * list)
	 * 
	 * @param exp
	 * @param factory2
	 * @return the list
	 */
	public static <Q extends TestPredicate<?,?>> List<Q> computeVectorTP(
			PairMaskingMCDCExr<Q> pairMCDC, Expression exp) {
		MCDCTPList<Q> liP = exp.accept(pairMCDC);
		List<Q> res = new ArrayList<Q>();
		for (Pair<Q, Q> p : liP) {
			res.add(p.getFirst());
			res.add(p.getSecond());
		}
		return res;
	}

	@Override
	public MCDCTPList<Q> forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("not implemented");
	}
	@Override
	public MCDCTPList<Q> forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public MCDCTPList<Q> forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
