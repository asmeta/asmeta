package tgtlib.definitions.expression.type;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;

/** variables of a specification
 * 
 * @author garganti
 *
 */
public interface Variable {

	/**
	 * 
	 * @return true if the variable is controlled
	 */
	boolean isControlled();

	/**
	 * 
	 * @return the default value
	 */
	Expression getValue();

	/** 
	 * 
	 * @return name of the variable
	 */
	String getName();

	/** return the id expression
	 * 
	 * @return
	 */
	IdExpression getIdExpression();

	/** type of the variable
	 * 
	 * @return
	 */
	Type getType();
	
}
