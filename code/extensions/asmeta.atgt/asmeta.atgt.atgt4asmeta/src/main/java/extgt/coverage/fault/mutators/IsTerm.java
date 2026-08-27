package extgt.coverage.fault.mutators;

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
import tgtlib.definitions.expression.XOrExpression;

/**
 * analyze the expression and says if it can be considered as term, i.e. all
 * the subexpression have the same operator (and ...).
 * Note that a and not b is a term
 * 
 * @author garganti
 * 
 */
public class IsTerm implements ExpressionVisitor<IsTerm.TermType> {
	static public IsTerm instance = new IsTerm();

	public enum TermType {NO_TERM, AND_TERM, OR_TERM,
		// if it is a or not a where a is a id term
		LIT_TERM;

	public static TermType typeFromOperator(Operator operator) {
		if (operator == Operator.AND) return AND_TERM;
		else if (operator == Operator.OR) return OR_TERM;
		throw new RuntimeException("type of term??");
	}}
	
	
	private IsTerm(){};

	private TermType forBinaryExpression(BinaryExpression be){
		Expression e1 = be.getFirstOperand();
		Expression e2 = be.getSecondOperand();
		Operator operator = be.accept(GetOperator.INSTANCE);
		// if both atomic then is a term
		boolean literal1 = e1.accept(this) == TermType.LIT_TERM;
		boolean literal2 = e2.accept(this)== TermType.LIT_TERM;
		if (literal1 && literal2) {
			// it is a term
			return IsTerm.TermType.typeFromOperator(operator);
		}
		// one is not atomic
		boolean absorbable1 = literal1 || e1.accept(GetOperator.INSTANCE) == operator;
		boolean absorbable2 = literal2 || e2.accept(GetOperator.INSTANCE) == operator;
		if (absorbable1 && absorbable2) return IsTerm.TermType.typeFromOperator(operator);
		else return TermType.NO_TERM;
	}

	@Override
	public TermType forAndExpression(AndExpression be) {
		return forBinaryExpression(be);
	}

	@Override
	public TermType forOrExpression(OrExpression be) {
		return forBinaryExpression(be);
	}

	@Override
	public TermType forXOrExpression(XOrExpression xOrExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not a is a LIT_term iff a is a lit term
	 */
	@Override
	public TermType forNotExpression(NotExpression notExpression) {
		boolean literal1 = notExpression.getOperand().accept(this) == TermType.LIT_TERM;
		if (literal1) return  TermType.LIT_TERM;
		else return TermType.NO_TERM;
	}

	@Override
	public TermType forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forEqualsExpression(EqualsExpression equalsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forIdExpression(IdExpression idExpression) {
		return TermType.LIT_TERM;
	}

	@Override
	public TermType forDivExpression(DivExpression divExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forPlusExpression(PlusExpression plusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forMinusExpression(MinusExpression minusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forLessThanExpression(LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forImpliesExpression(ImpliesExpression impliesExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forMultExpression(MultExpression multExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forModuloExpression(ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermType forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public TermType forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
