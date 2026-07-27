package atgt.specification.location;

import tgtlib.definitions.expression.IdExpression;

/** represents logical variables, those that starts with $ in asmeta
 * 
 */
public class LogicalVariable  extends Location{

	public LogicalVariable(IdExpression _name) {
		super(_name, null);
	}

	@Override
	public <T> T accept(LocationVisitorI<T> ask) {
		return ask.forLogicalVariable(this);
	}

}
