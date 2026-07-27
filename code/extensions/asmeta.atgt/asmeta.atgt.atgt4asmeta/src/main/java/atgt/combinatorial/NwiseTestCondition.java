package atgt.combinatorial;

import java.util.List;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.Expression;

/**
 * nwise with n > 2
 * 
 * @author garganti
 * 
 */
public abstract class NwiseTestCondition extends CombinatorialTestCondition {

	/** The variables. */
	protected List<? extends TypedInitExpression> vars;

	public NwiseTestCondition(String name, List<? extends TypedInitExpression> vs, Expression se) {
		super(name, se);
		//assert vars.size() > 2;
		this.vars = vs;
	}

	/** return n of nwise */
	public final int size() {
		return vars.size();
	}

}
