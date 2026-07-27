package tgtlib.definitions;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.Type;

/** IDexpression + its type
 * 
 * @author garganti
 *
 */
class TypedExpression {
	
	/** IdExpression of the location*/
	protected IdExpression name;
	/** Type of location. */
	protected Type type;

	/**
	 * 
	 * @param id of the expression
	 * @param _type the type (can be null)
	 */
	TypedExpression(IdExpression id, Type _type) {
		assert id != null;
		//assert _type != null;
		this.name = id;
		this.type = _type;
	}


}
