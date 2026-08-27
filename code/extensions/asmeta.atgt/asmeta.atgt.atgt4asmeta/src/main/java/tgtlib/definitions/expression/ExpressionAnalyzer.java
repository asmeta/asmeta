package tgtlib.definitions.expression;

/** analyze an expression and return a T. Tobe used for Expression Visitior when the visitor is not needed.
 * 
 * @author garganti
 *
 */
public interface ExpressionAnalyzer<T> {
	
	T analyze(Expression e);

}
