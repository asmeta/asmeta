package tgtlib.definitions.normalform.cnf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.definitions.normalform.BoolNFExpression;
import tgtlib.definitions.normalform.Term;

/**
 * cnf expressions
 * 
 * @author garganti
 * 
 * @version $Revision: 1.0 $
 */
public class CNFExpression extends BoolNFExpression {
	private static final Logger logger = Logger.getLogger(CNFExpression.class);
	
	/**
	 * Constructor for CNFExpression.
	 * @param result List<Term>
	 */
	public CNFExpression(List<Term> result) {
		super(result);
	}

	/**
	 * Method accept.
	 * @param visitor ExpressionVisitor<T>
	 * @return T
	 * @see tgtlib.definitions.expression.Expression#accept(ExpressionVisitor<T>)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> visitor) {
		throw new RuntimeException("not implemented yet");
	}

	/**
	 * Method makeNewTerm.
	 * @param exp PrimedIdUIdExpression
	 * @return Term
	 */
	@Override
	protected Term makeNewTerm(IdUNotIdExpression exp) {
		return new Term(Collections.singletonList(exp));
	}

	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString() {
		return terms.toString();
	}

	/**
	 * Method makeExpressionFromTerms.
	 * @param terms List<Term>
	 * @return Expression
	 */
	@Override
	protected Expression makeExpressionFromTerms(List<Term> terms) {
		assert terms.size() > 0;
		// if only one term, return and
		Expression disjoint0 = terms.get(0).disjoint();
		if (terms.size() == 1) {
			return disjoint0;
		} else {
			return new AndExpression(disjoint0,
					makeExpressionFromTerms(terms.subList(1, terms.size())));
		}
	}
	
	/** convert the expression to dimacs format
	 * 
	 * @return Dimacs
	 * 
	 */
	public Dimacs toDimacs() {
		return toDimacsGivenMap(this.getLiterals());
	}

	/**
	 * vars: the variables already fixed in the right order. It may be more or fewer than the real literals
	 */
	public Dimacs toDimacs(List<? extends Variable> vars) {
		List<IdExpression> idExpressions = new ArrayList<IdExpression>();
		for (Variable variable: vars ) {
			idExpressions.add(variable.getIdExpression());
		}
		// add the new ones of the CNF if not already contained
		for(IdExpression id: this.getLiterals()){
			if(!idExpressions.contains(id))
				idExpressions.add(id);
		}	
		return toDimacsGivenMap(idExpressions);
	}
	

	/** convert the expression to dimacs format 
	 * make private to avoid its use with fewer ids (for example when using tstin, that adds new ids)
	 * @return Dimacs
	 * @param ids the ids for the given correspondence between ids and numbers 
	 */
	protected Dimacs toDimacsGivenMap(List<IdExpression> ids) {
		logger.debug("converting to dimacs using ids "+ ids);
		// number of variables
		// number of clauses
		// build new dimacs
		Dimacs result = new Dimacs(ids, terms.size());
		// get the
		for (Term t: terms) {
			List<Integer> clause = t.convertTermInintClause(ids);
			result.addClause(clause);
		}
		return result;
	}
}