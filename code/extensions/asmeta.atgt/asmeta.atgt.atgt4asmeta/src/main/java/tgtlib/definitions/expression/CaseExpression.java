package tgtlib.definitions.expression;

import java.util.HashMap;
import java.util.Map;

// ASM case expressions
//switch doors
//case OPEN:
//	CYLINDER_EXTENDED

public class CaseExpression implements Expression{
	private IdExpression idSwitch;
	private Map<IdExpression, Expression> cases = new HashMap<IdExpression, Expression>();
	// it can be null
	private Expression defaultCase;

	public CaseExpression(IdExpression comparedTermAsExp) {
		idSwitch = comparedTermAsExp;
	}

	public void addCase(IdExpression caseId, Expression value){
		assert cases.get(caseId) == null;
		cases.put(caseId,value);		
	}

	public void setDefault(Expression val){
		defaultCase = val;
	}
	
	@Override
	public <T> T accept(ExpressionVisitor<T> visitor) {		
		return visitor.forCaseExpression(this);
	}

	public IdExpression getIdSwitch() {
		return idSwitch;
	}

	public void setIdSwitch(IdExpression idSwitch) {
		this.idSwitch = idSwitch;
	}

	public Map<IdExpression, Expression> getCases() {
		return cases;
	}

	public void setCases(Map<IdExpression, Expression> cases) {
		this.cases = cases;
	}

	public Expression getDefaultCase() {
		return defaultCase;
	}

	public void setDefaultCase(Expression defaultCase) {
		this.defaultCase = defaultCase;
	}

}