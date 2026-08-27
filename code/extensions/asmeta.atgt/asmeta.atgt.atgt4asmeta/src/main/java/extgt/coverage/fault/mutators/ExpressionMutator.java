package extgt.coverage.fault.mutators;

import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.util.Pair;

/** given an expression return the mutations
 * 
 * @author garganti
 *
 */
public interface ExpressionMutator {

	/** use this method to get the mutations (equivalent to use the visitor) + some checks (prefer use this)
	 * 
	 * @param e expression
	 * @return the list of mutants and integers representing the position where the mutation has been applied
	 */
	public List<Pair<Integer, Expression>> getMutations(Expression e);
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	abstract public String getName();

	/**
	 * Gets the abbrv name.
	 * 
	 * @return the abbrv name
	 */
	abstract public String getAbbrvName();
}
