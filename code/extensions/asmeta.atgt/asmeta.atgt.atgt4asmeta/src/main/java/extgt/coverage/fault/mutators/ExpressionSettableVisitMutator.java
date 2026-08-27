package extgt.coverage.fault.mutators;

import java.util.Collection;
import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GetConditions;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.util.Pair;

/** 
 * Build a mutator for given set of conditions (id or not id) 
 * 
 * @author garganti
 *
 * @param <T>
 */
public abstract class ExpressionSettableVisitMutator<T extends ExtraVariableFault>
		extends ExpressionVisitMutator<T> {

	/** return a new mutator
	 * 
	 * @param ids
	 * @return
	 */
	protected abstract T getNewVisitorForIds(List<IdUNotIdExpression> ids);

	@Override
	public final ExpressionMutator getExpressionMutator(Expression e) {
		// get the ids
		List<IdUNotIdExpression> idOfe = e.accept(GetConditions.getConds);
		assert ! idOfe.contains(BoolType.FALSE_CONST);
		assert ! idOfe.contains(BoolType.TRUE_CONST);
		return getExpressionMutator(idOfe);
	}

	public final ExpressionMutator getExpressionMutator(List<IdUNotIdExpression> idOfe) {
		fev = getNewVisitorForIds(idOfe);
		final String name = getName();
		final String abbrname = getAbbrvName();
		return new ExpressionMutator() {
			@Override
			public String getName() {
				return name;
			}

			@Override
			public List<Pair<Integer, Expression>> getMutations(Expression e) {
				// check that the ids in the expression are already considered
				// TODO remove the check and pull up this method
				ExtraVariableFault fevevf = fev;
				List<IdUNotIdExpression> considered = fevevf.getConditions();
				// if the ids are not set yes, set them
				assert considered != null;
				// check (only if assert is enabled) try use assert false
				try {
					assert false;
				}catch(AssertionError ae){
					// e cannot contain extra variables !
					// used only in the assertion
					Collection<IdUNotIdExpression> idOfe = e.accept(GetConditions.getConds);
					assert considered.containsAll(idOfe) : "toConsider "
						+ considered + " in the expression " + idOfe;
				}
				return e.accept(fev);
			}

			@Override
			public String getAbbrvName() {
				return abbrname;
			}
		};
	}
}