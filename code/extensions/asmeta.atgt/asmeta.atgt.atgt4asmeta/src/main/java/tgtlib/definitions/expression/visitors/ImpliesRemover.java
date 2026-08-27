package tgtlib.definitions.expression.visitors;

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

/** remove the implies and convert to equivalent */
public class ImpliesRemover implements ExpressionVisitor<Expression> {
	static final public ImpliesRemover instance = new ImpliesRemover();

	private ImpliesRemover() {
	}

	@Override
	public Expression forIdExpression(IdExpression idExpression) {
		return idExpression;
	}

	@Override
	public Expression forImpliesExpression(ImpliesExpression impliesExpression) {
		// recurse in the formula
		ImpliesExpression red = (ImpliesExpression) forBinaryExpression(impliesExpression, Operator.IMPLIES);
		// remove external implies
		return red.getEquivalent();
	}

	@Override
	public Expression forAndExpression(AndExpression andExpression) {
		return forBinaryExpression(andExpression, Operator.AND);
	}

	@Override
	public Expression forOrExpression(OrExpression orExpression) {
		return forBinaryExpression(orExpression, Operator.OR);
	}

	@Override
	public Expression forXOrExpression(XOrExpression xOrExpression) {
		return forBinaryExpression(xOrExpression, Operator.XOR);
	}

	private Expression forBinaryExpression(BinaryExpression e, Operator op) {
		Expression e1 = e.getFirstOperand();
		Expression e1p = e1.accept(this);
		Expression e2 = e.getSecondOperand();
		Expression e2p = e2.accept(this);
		if (e1 == e1p && e2 == e2p)
			return e;
		else
			return BinaryExpression.mkBinExpr(e1p, op, e2p);
	}

	@Override
	public Expression forNotExpression(NotExpression notExpression) {
		Expression operand = notExpression.getOperand();
		Expression e1 = operand.accept(this);
		if (e1 == operand)
			return notExpression;
		else
			return NotExpression.createNotExpression(e1);
	}

	@Override
	public Expression forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forGreaterEqualExpression(GreaterEqualExpression greaterEqualExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forEqualsExpression(EqualsExpression equalsExpression) {
		//throw new RuntimeException("not implemented yet");
		Expression e1 = equalsExpression.getFirstOperand();
		Expression e1p = e1.accept(this);
		Expression e2 = equalsExpression.getSecondOperand();
		Expression e2p = e2.accept(this);
		return OrExpression.mkBinExpr(AndExpression.makeAndExpression(e1p, e2p), Operator.OR, AndExpression.makeAndExpression(NotExpression.createNotExpression(e1p), NotExpression.createNotExpression(e2p)));
	}

	@Override
	public Expression forGreaterThanExpression(GreaterThanExpression greaterThanExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forLessEqualExpression(LessEqualExpression lessEqualExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forLessThanExpression(LessThanExpression lessThanExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
		return forBinaryExpression(notEqualsExpression, Operator.NEQ);
	}

	@Override
	public Expression forDivExpression(DivExpression divExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forPlusExpression(PlusExpression plusExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forMinusExpression(MinusExpression minusExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forMultExpression(MultExpression multExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNegExpression(NegExpression negExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forModuloExpression(ModuloExpression moduloExpression) {
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
