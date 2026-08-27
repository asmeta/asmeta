package atgt.combinatorial;

import java.util.List;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;

/**
 * generic nwise test condition with a generic expression, for example x<10 as condition. Not equal
 * 
 * @author garganti
 * 
 */
public class GenericNwiseTestCondition extends NwiseTestCondition {

	/**
	 * 
	 * @param string
	 * @param vs
	 * @param ecl
	 *            list of expressions about the i-th variable
	 */
	public GenericNwiseTestCondition(String string, List<TypedInitExpression> vs,
			List<Expression> ecl) {
		super(string, vs, AndExpression.makeAndExpression(ecl));
	}
}
