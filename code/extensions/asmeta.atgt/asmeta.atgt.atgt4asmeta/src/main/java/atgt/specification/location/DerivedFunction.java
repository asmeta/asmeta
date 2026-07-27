package atgt.specification.location;

import tgtlib.definitions.expression.IdExpression;

/** derived function - for now only 0-arity variables can be derived
 * 
 * @author garganti
 *
 */
public class DerivedFunction extends AsmTerm {

	// only the declaration
	// for the definition use setValue instead
	public DerivedFunction(IdExpression id) {
		super(id, null);		
	}
	
	@Override
	public <T> T accept(LocationVisitorI<T> ask) {
		throw new RuntimeException("not implemented yet");
	}	
}
