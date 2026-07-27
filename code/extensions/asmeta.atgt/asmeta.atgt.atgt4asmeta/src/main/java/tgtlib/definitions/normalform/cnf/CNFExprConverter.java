package tgtlib.definitions.normalform.cnf;

import tgtlib.definitions.expression.Expression;

/** algorithm for converting to CNF
 * 
 * @author garganti
 *
 */
public interface CNFExprConverter {

	/** returns the CNF. two consecutive call may be reuse some information
	 * (useful when constraints are added incrementally). For instance the cnf returned may contain an ID not originally presented in expr
	 * @param expr
	 * @return
	 */
	CNFExpression getCNF(Expression expr);

	public interface CNFExprConverterFactory{
		CNFExprConverter getCNFExprConverter();
	}	
}