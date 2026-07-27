package extgt.coverage.mcdc;

import tgtlib.definitions.expression.AndExpression;
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
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;

/**
 * return the simple negation of an expression. It simplifies the not (not not x
 * -> x)
 * 
 * @author garganti
 */
public class ShallowExpressionNegator implements ExpressionVisitor<Expression> {

	/**
	 * Instantiates a new expression negator.
	 */
	private ShallowExpressionNegator() {
	}

	/** The negate. */
	static public ShallowExpressionNegator negate = new ShallowExpressionNegator();

	// Math Expression
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forPlusExpression(
	 * atgt.specification.expression.PlusExpression)
	 */
	@Override
	public Expression forPlusExpression(PlusExpression e) {
		throw new RuntimeException("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMinusExpression
	 * (atgt.specification.expression.MinusExpression)
	 */
	@Override
	public Expression forMinusExpression(MinusExpression e) {
		throw new RuntimeException("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forDivExpression(atgt
	 * .specification.expression.DivExpression)
	 */
	@Override
	public Expression forDivExpression(DivExpression e) {
		throw new RuntimeException("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forMultExpression(
	 * atgt.specification.expression.MultExpression)
	 */
	@Override
	public Expression forMultExpression(MultExpression e) {
		throw new RuntimeException("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNegExpression(atgt
	 * .specification.expression.NegExpression)
	 */
	@Override
	public Expression forNegExpression(NegExpression e) {
		throw new RuntimeException("not supported");
	}

	// Logic Expression
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt
	 * .specification.expression.AndExpression)
	 */
	@Override
	public Expression forAndExpression(AndExpression e) {
		return NotExpression.createNotExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt
	 * .specification.expression.OrExpression)
	 */
	@Override
	public Expression forOrExpression(OrExpression e) {
		return NotExpression.createNotExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt
	 * .specification.expression.NotExpression)
	 */
	@Override
	public Expression forNotExpression(NotExpression e) {
		return e.getOperand();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forXOrExpression(atgt
	 * .specification.expression.XOrExpression)
	 */
	@Override
	public Expression forXOrExpression(XOrExpression e) {
		return NotExpression.createNotExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression
	 * (atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public Expression forImpliesExpression(ImpliesExpression e) {
		return NotExpression.createNotExpression(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt
	 * .specification.expression.IdExpression)
	 */
	@Override
	public Expression forIdExpression(IdExpression e) {
		return NotExpression.createNotExpression(e);
	}

	@Override
	public Expression forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNextExpression(
	 * atgt.specification.expression.NextExpression)
	 */
	@Override
	public Expression forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("not next supported");
	}

	//
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression
	 * (atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public Expression forEqualsExpression(EqualsExpression e) {
		return new NotEqualsExpression(e.getFirstOperand(),
				e.getSecondOperand());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression
	 * (atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public Expression forNotEqualsExpression(NotEqualsExpression e) {
		return new EqualsExpression(e.getFirstOperand(), e.getSecondOperand());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessThanExpression
	 * (atgt.specification.expression.LessThanExpression)
	 */
	@Override
	public Expression forLessThanExpression(LessThanExpression e) {
		return new GreaterEqualExpression(e.getFirstOperand(),
				e.getSecondOperand());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forLessEqualExpression
	 * (atgt.specification.expression.LessEqualExpression)
	 */
	@Override
	public Expression forLessEqualExpression(LessEqualExpression e) {
		return new GreaterThanExpression(e.getFirstOperand(),
				e.getSecondOperand());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forGreaterThanExpression
	 * (atgt.specification.expression.GreaterThanExpression)
	 */
	@Override
	public Expression forGreaterThanExpression(GreaterThanExpression e) {
		return new LessEqualExpression(e.getFirstOperand(),
				e.getSecondOperand());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.AsmExpressionVisitor#forGreaterEqualExpression
	 * (atgt.specification.expression.GreaterEqualExpression)
	 */
	@Override
	public Expression forGreaterEqualExpression(GreaterEqualExpression e) {
		return new LessThanExpression(e.getFirstOperand(), e.getSecondOperand());
	}

	@Override
	public Expression forModuloExpression(ModuloExpression moduloExpression) {
		return forMathExpression();
	}

	private Expression forMathExpression() {
		throw new RuntimeException("not supported");
	}

	@Override
	public Expression forFunctionTerm(FunctionTerm ft) {
		return NotExpression.createNotExpression(ft);
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
