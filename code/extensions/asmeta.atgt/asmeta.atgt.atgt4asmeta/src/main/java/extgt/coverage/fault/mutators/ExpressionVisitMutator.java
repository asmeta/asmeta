package extgt.coverage.fault.mutators;

import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.util.Pair;

/**
 * factory for expression mutators: given an expression return the expression
 * mutator
 * 
 * @param <T>
 *            the generic type representing the visitor type
 * 
 * @author garganti
 */
public abstract class ExpressionVisitMutator<T extends FaultExpressionVisitor> {

	// the visitor of the expression
	protected T fev;

	/**
	 * 
	 * @param e
	 *            the expression representing the initial specification
	 * @return the expression mutator
	 */
	public abstract ExpressionMutator getExpressionMutator(Expression e);

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

	/**
	 * @return the mutations from a newly created expression mutators
	 * 
	 */
	public final List<Pair<Integer, Expression>> buildMutatorGetMutations(Expression e){
		ExpressionMutator mutator = getExpressionMutator(e);
		return mutator.getMutations(e);
	}	
}
