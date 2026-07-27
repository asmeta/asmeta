package extgt.coverage.fault.mutators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.type.BoolType;

/**
 * faults that introduce extra variables or other occurrence of the variables
 * 
 * @author garganti
 * 
 */
public abstract class ExtraVariableFault extends FaultExpressionEmptyVisitor {

	// NOTE from chen's paper
	// We follow the definitions and notations used by Kapoor and Bowen [Kapoor and 
	// Bowen 2007]. Here a condition is a Boolean variable or a negated Boolean variable.

	// the conditions to be considered
	private List<IdUNotIdExpression> toConsider;

	/**
	 * note that the setIds must be called after the creation
	 * @param ids 
	 */
	protected ExtraVariableFault(List<IdUNotIdExpression> ids) {
		// set the ids: copy the id in to consider
		// check that it does not contain true and false
		assert !ids.contains(BoolType.TRUE_CONST);
		assert !ids.contains(BoolType.FALSE_CONST);
		assert ids.size() > 0;
		//
		toConsider = new ArrayList<IdUNotIdExpression>(ids);
	}

	/**  
	 * @return the Ids or not ids to be considered
	 */
	protected List<IdUNotIdExpression> getConditions() {
		return Collections.unmodifiableList(toConsider);
	}
}
