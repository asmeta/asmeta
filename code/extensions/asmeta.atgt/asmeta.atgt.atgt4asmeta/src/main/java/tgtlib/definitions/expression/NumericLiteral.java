package tgtlib.definitions.expression;

import tgtlib.definitions.expression.type.IntegerType;

/** represent the numbers literals  
 *  
 * @author garganti
 *
 */
public class NumericLiteral extends IdExpression {
	
	public NumericLiteral(Number number) {
		super(number.toString(), IntegerType.INTEGER_TYPE);
	}
}
