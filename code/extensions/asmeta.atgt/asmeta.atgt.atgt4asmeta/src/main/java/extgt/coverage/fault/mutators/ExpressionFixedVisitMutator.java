package extgt.coverage.fault.mutators;

import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.util.Pair;

/**
 * for this type the visitor is set once for all
 * 
 * @author garganti
 * 
 * @param <T>
 */
public abstract class ExpressionFixedVisitMutator<T extends FaultExpressionVisitor>
		extends ExpressionVisitMutator<T> {

	protected ExpressionFixedVisitMutator(T visitor) {
		fev = visitor;
	}

	/**
	 * simply visit the expression with fev
	 * 
	 */
	@Override
	public final ExpressionMutator getExpressionMutator(Expression e) {
		return getExpressionMutator();
	}

	/** the expression mutator dose not depend on e
	 * 
	 * @return
	 */
	public final ExpressionMutator getExpressionMutator() {
		final String name = getName();
		final String abbrname = getAbbrvName();
		return new ExpressionMutator() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public List<Pair<Integer, Expression>> getMutations(final Expression e) {
				return e.accept(fev);
			}

			@Override
			public String getAbbrvName() {
				return abbrname;
			}
		};
	}
}