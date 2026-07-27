package atgt.specification.statement;

import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
/**
 * represents a choose rule 
 *
 */
public class ChooseRule extends BasicRule{
	
	// choose var in {terms} do body
	// body
	BasicRule doRule;
	IdExpression var;
	Expression condition;
	List<Expression> terms;
	
	/**
	 * 
	 * @param var variable
	 * @param terms about the variable
	 * @param cr body of the rule
	 */
	public ChooseRule(IdExpression var, List<Expression> terms, Expression condition, BasicRule cr){
		assert var.getType() != null;
		doRule = cr;
		this.var = var;
		this.terms = terms;
		this.condition = condition;
		assert ! condition.toString().equalsIgnoreCase("FALSE");
	}	
	
	public BasicRule getDoRule(){
		return doRule;
	}
	
	public IdExpression getVar(){
		return var;
	}
	/** return the condition never null - true if in any case */	
	public Expression getCondition(){
		return condition;
	}

	/** where the variable take values
	 * 
	 * @return
	 */
	public List<Expression> getTerms(){
		return terms;
	}

	@Override
	public <T> T accept(RuleVisitor<T> ask) {
		return ask.forChooseRule(this);
	}

}
