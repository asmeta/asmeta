package atgt.specification.location;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.Type;

public abstract class AsmTerm extends TypedInitExpression {

	/**
	 * 
	 * @param id the id
	 * @param type its type
	 * @param value its initial value
	 */
	@Deprecated
	protected AsmTerm(IdExpression id, Type type, Expression value) {
		super(id, type, value);
	}

	/**
	 * 
	 * @param id the id (with its type)
	 * @param value its initial value
	 */
	protected AsmTerm(IdExpression id, Expression value) {
		super(id, value);
		assert id.getType() != null : "id " + id.getIdString() + " has no type";
	}

	/**
	 * A method for Visitor Pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the T
	 */
	public abstract <T> T accept(LocationVisitorI<T> ask);


}
