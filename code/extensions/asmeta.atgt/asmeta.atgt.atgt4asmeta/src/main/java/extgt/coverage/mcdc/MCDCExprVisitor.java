package extgt.coverage.mcdc;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;

/** 
 * some utils methods for MCDC alike classes
 * 
 * @param <T>
 */
public abstract class MCDCExprVisitor<T> implements ExpressionVisitor<List<T>>{

	
	@Override
	public final List<T> forIdExpression(IdExpression e) {
		return makeTF(e);
	}

	@Override
	public List<T> forFunctionTerm(FunctionTerm ft) {
		// TODO consider also the arguments in case they are expressions???
		return Collections.singletonList(makeTFPair(ft));
	}

	
	protected List<T> makeTF(Expression e) {
		return Collections.singletonList(makeTFPair(e));
	}

	
	abstract protected T makeTFPair(Expression e);


	protected final List<T> forBinaryExpression(
			BinaryExpression expr, Operator op) {
		List<T> result = new Vector<T>();		
		Expression e1 = expr.getFirstOperand();
		List<T> set1 = e1.accept(this);
		Expression e2 = expr.getSecondOperand();
		List<T> set2 = e2.accept(this);
		addToSet(set1, e2, result, op, false);
		addToSet(set2, e1, result, op, true);
		return result;
	}

	protected abstract void addToSet(List<T> set1, Expression e2, List<T> result,
			Operator op, boolean b);

	
	// Logic Expression
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forAndExpression
	 * (atgt.specification.expression.AndExpression)
	 */
	@Override
	public final List<T> forAndExpression(AndExpression expr) {
		return forBinaryExpression(expr, Operator.AND);
	}

	@Override
	public final List<T> forOrExpression(OrExpression expr) {
		return forBinaryExpression(expr, Operator.OR);
	}

	@Override
	public final List<T> forImpliesExpression(
			ImpliesExpression impliesExpression) {
		throw new RuntimeException("implies ???");
	}
	
	@Override
	public List<T> forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}


}
