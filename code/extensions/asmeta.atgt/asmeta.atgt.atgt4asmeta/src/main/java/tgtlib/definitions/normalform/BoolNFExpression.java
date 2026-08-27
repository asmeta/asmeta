package tgtlib.definitions.normalform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;

/**
 * Boolean NF expressions with only literals. They can be CNF or DNF.
 * The true and false constants cannot be in the terms.
 * 
 * @author garganti
 *
 */
public abstract class BoolNFExpression extends NFExpression<Term,IdUNotIdExpression> implements Expression {

	/** the ids in this expression - ordered */
	private List<IdExpression> ids;
	
	/** Representation as Expression */
	private Expression eqExpression;

	public BoolNFExpression(List<Term> terms) {
		super(terms);
	}

	public BoolNFExpression(IdUNotIdExpression idExpression) {
		super(idExpression);
	}
	
	/** return the list of terms (unmodifable)
	 * 
	 * @return
	 */
	public List<Term> getTerms() {
		return terms;
	}

	/** returns the list of ids
	 * 
	 * @return
	 */
	public List<IdExpression> getLiterals() {
		if (ids == null) {
			// use a set to remove duplicates
			Set<IdExpression> tempids = new HashSet<IdExpression>();
			for (Term t : getTerms())
				tempids.addAll(t.getIds());			
			ids = new ArrayList<IdExpression>(tempids);
		}
		return ids;
	}

	/** return the equivalent expression as And OR
	 * 
	 * @return
	 */
	public Expression getEqExpression() {
		if (eqExpression == null){
			assert getTerms().size() > 0;
			eqExpression = makeExpressionFromTerms(getTerms());
		}
		return eqExpression;
	}

	/**
	 * 
	 * @param terms
	 * @return
	 */
	protected abstract Expression makeExpressionFromTerms(List<Term> terms);

}
