package tgtlib.definitions.expression;

import java.util.Map;

// ASM case expressions of type
// IF CONDITION THEN A ELSE B ENDIF

public class CondExpression implements Expression{

	private Expression condition;
	private Expression thenE;
	private Expression elseE;

	public CondExpression(Expression condition, Expression thenE, Expression elseE) {
		assert condition != null;
		this.condition = condition;
		// assert condition type = boolean
		this.thenE = thenE;
		this.elseE = elseE;
		assert thenE != null && elseE != null;
		// assert the same type for both
	}
	
	@Override
	public <T> T accept(ExpressionVisitor<T> visitor) {		
		return visitor.forConditionalExpression(this);
	}

	public Expression getCondition() {
		return condition;
	}


	public Expression getThenE() {
		return thenE;
	}


	public Expression getElseE() {
		return elseE;
	}	
}