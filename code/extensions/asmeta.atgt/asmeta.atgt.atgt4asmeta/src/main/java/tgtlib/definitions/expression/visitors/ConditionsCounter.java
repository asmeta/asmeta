package tgtlib.definitions.expression.visitors;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
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
import tgtlib.definitions.expression.type.BoolType;

public class ConditionsCounter implements ExpressionVisitor<Integer> {
	static final public ConditionsCounter instance = new ConditionsCounter();
	private boolean areTrueFalseConditions = true;

	private ConditionsCounter() {
		this(true);
	}

	public ConditionsCounter(boolean areTrueFalseConditions) {
		this.areTrueFalseConditions = areTrueFalseConditions;
	}

	@Override
	public Integer forIdExpression(IdExpression idExpression) {
		String idAsStr = idExpression.getIdString();
		if((idAsStr.equals(BoolType.TRUE_STR) || idAsStr.equals(BoolType.FALSE_STR)) && !areTrueFalseConditions) {
			return 0;
		}
		return 1;
	}

	@Override
	public Integer forImpliesExpression(ImpliesExpression impliesExpression) {
		return forBinaryExpression(impliesExpression, Operator.IMPLIES);
	}

	@Override
	public Integer forAndExpression(AndExpression andExpression) {
		return forBinaryExpression(andExpression, Operator.AND);
	}
	
	@Override
	public Integer forOrExpression(OrExpression orExpression) {
		return forBinaryExpression(orExpression, Operator.OR);
	}

	@Override
	public Integer forXOrExpression(XOrExpression xOrExpression) {
		return forBinaryExpression(xOrExpression, Operator.XOR);
	}

	private Integer forBinaryExpression(BinaryExpression e, Operator op) {
		return e.getFirstOperand().accept(this) + e.getSecondOperand().accept(this);
	}

	@Override
	public Integer forNotExpression(NotExpression notExpression) {
		return notExpression.getOperand().accept(this);
	}

	@Override
	public Integer forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forGreaterEqualExpression(GreaterEqualExpression greaterEqualExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forEqualsExpression(EqualsExpression equalsExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forGreaterThanExpression(GreaterThanExpression greaterThanExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forLessEqualExpression(LessEqualExpression lessEqualExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forLessThanExpression(LessThanExpression lessThanExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
		return forBinaryExpression(notEqualsExpression,Operator.NEQ);
	}

	@Override
	public Integer forDivExpression(DivExpression divExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forPlusExpression(PlusExpression plusExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forMinusExpression(MinusExpression minusExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forMultExpression(MultExpression multExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forNegExpression(NegExpression negExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forModuloExpression(ModuloExpression moduloExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("Not applicable");
	}

	@Override
	public Integer forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}