package tgtlib.definitions.normalform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.type.BoolType;

/**
 * represents the list of expressions. It is intended to be used as immutable:
 * once is created not new terms can be added.
 * 
 * @author garganti
 * 
 * @version $Revision: 1.0 $
 */

public class Term implements Iterable<IdUNotIdExpression> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(Term.class);

	
	// literals: only IDs or not ids
	protected List<IdUNotIdExpression> lits;

	/**
	 * build the term with this literals (or negations
	 * 
	 * @param literalList
	 */
	public Term(List<? extends IdUNotIdExpression> literalList) {
		this();
		for (IdUNotIdExpression e : literalList)
			add(e);
	}

	/**
	 * empty term (it can be empty)
	 */
	private Term() {
		lits = new ArrayList<IdUNotIdExpression>();
	}

	/**
	 * copy constructor
	 * 
	 * @param term
	 */
	private Term(Term term) {
		this(term.lits);
	}

	/**
	 * Constructor for Term.
	 * 
	 * @param idExpression
	 *            IdExpression
	 */
	public Term(IdExpression idExpression) {
		this();
		// no check is needed
		lits.add(idExpression);
	}

	/**
	 * Constructor for Term.
	 * 
	 * @param notExpression
	 *            NotExpression
	 */
	public Term(IdUNotIdExpression notExpression) {
		this();
		add(notExpression);
	}

	/**
	 * Method isEmpty.
	 * 
	 * @return boolean
	 */
	boolean isEmpty() {
		return lits.isEmpty();
	}

	/**
	 * add an expression to a term the term must be and ID or not ID
	 * 
	 * @param expr
	 */
	private void add(IdUNotIdExpression expr) {
		// it cannot be a false or a true expression
		assert expr != BoolType.FALSE_CONST;
		assert expr != BoolType.TRUE_CONST;
		// check that it is a literal or a negation
		// useless if IdUNotIdExpression is used
		// assert (expr instanceof IdExpression || (expr instanceof
		// NotExpression && ((NotExpression) expr)
		// .getOperand() instanceof IdExpression));
		// check that it does not contain the opposite
		assert !(expr instanceof IdExpression && lits.contains(UnaryExpression
				.mkUnExpr(Operator.NOT, expr)));
		assert !(expr instanceof NotExpression && lits
				.contains(((NotExpression) expr).getOperand()));
		lits.add(expr);
	}

	/**
	 * returns the and expression of all the expressions contained in a term
	 * 
	 * 
	 * 
	 * @return Expression
	 */
	public Expression conjoint() {
		assert lits.size() >= 1;
		Expression result = lits.get(0);
		for (int i = 1; i < lits.size(); i++)
			result = new tgtlib.definitions.expression.AndExpression(result,
					lits.get(i));
		return result;
	}

	/**
	 * returns the OR expression of all the expressions contained in a term
	 * 
	 * @return Expression
	 */
	public Expression disjoint() {
		assert lits.size() >= 1;
		Expression result = lits.get(0);
		for (int i = 1; i < lits.size(); i++)
			result = new tgtlib.definitions.expression.OrExpression(result,
					lits.get(i));
		return result;
	}

	/**
	 * return the literals
	 * 
	 * @return Iterator<Expression>
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<IdUNotIdExpression> iterator() {
		return lits.iterator();
	}

	/**
	 * add a new literal and return a new Term
	 * 
	 * @param idToAdd
	 * 
	 * @return a NEW Term
	 */
	public Term addLiteral(IdUNotIdExpression idToAdd) {
		Term newTerm = new Term(this);
		newTerm.add(idToAdd);
		return newTerm;
	}

	/**
	 * @param i
	 * 
	 * @return a new term
	 */
	public Term removeInTerm(int i) {
		Term newTerm = new Term(this);
		newTerm.lits.remove(i);
		return newTerm;
	}

	/**
	 * @param i
	 * @param idToAdd
	 * 
	 * @return a new term
	 */
	public Term replaceInTerm(int i, IdUNotIdExpression idToAdd) {
		Term newTerm = new Term(this);
		assert (idToAdd instanceof IdExpression || (idToAdd instanceof NotExpression && ((NotExpression) idToAdd)
				.getOperand() instanceof IdExpression));
		newTerm.lits.set(i, idToAdd);
		return newTerm;
	}

	/**
	 * Method size.
	 * 
	 * @return int
	 */
	public int size() {
		return lits.size();
	}

	/**
	 * Method get.
	 * 
	 * @param j
	 *            int
	 * @return Expression
	 */
	public IdUNotIdExpression get(int j) {
		return lits.get(j);
	}

	/**
	 * Method toString.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (Expression e : lits) {
			if (e instanceof IdExpression)
				result.append(e.toString());
			else {
				result.append('~');
				result.append(((NotExpression) e).getOperand().toString());
			}
		}
		return result.toString();
	}

	/**
	 * Method getIds.
	 * 
	 * @return Set<IdExpression>
	 */
	public Set<IdExpression> getIds() {
		Set<IdExpression> ids = new HashSet<IdExpression>();
		for (Expression e : this) {
			if (e instanceof IdExpression) {
				ids.add((IdExpression) e);
			} else {
				// not
				ids.add((IdExpression) ((NotExpression) e).getOperand());
			}
		}
		return ids;
	}

	/**
	 * return the negation of the term intended with a and (*) between the
	 * elements
	 * 
	 * 
	 * @return Expression
	 */
	public Expression negation() {
		if (size() == 1 && lits.get(0) instanceof NotExpression)
			return ((NotExpression) lits.get(0)).getOperand();
		else
			return NotExpression.createNotExpression(conjoint());
	}

	/**
	 * return e new term that is the union between this and term2 (without
	 * duplicates) if term this contains a and term2 contains !a then return
	 * null NOTE that (a or !a = true) while ( a and !a = false) in both case
	 * return an empty term: no opposite is allowed in a term
	 * 
	 * @param term2
	 * 
	 * @return Term
	 */
	public Term merge(Term term2) {
		List<IdUNotIdExpression> resList = new ArrayList<IdUNotIdExpression>(
				this.lits);
		// avoid duplications
		for (IdUNotIdExpression e : term2.lits) {
			if (resList.contains(e))
				continue;
			// check if contains the opposite
			Expression opp = opposite(e);
			if (resList.contains(opp)) {
				// as AND contradiction found !
				// as OR a true always found !
				return null;
			} else {
				// if not contains opp, then add it
				resList.add(e);
			}
		}
		return new Term(resList);
	}

	/**
	 * Method opposite.
	 * 
	 * @param e
	 *            Expression
	 * @return Expression
	 */
	private Expression opposite(Expression e) {
		//
		if (e instanceof NotExpression) {
			return ((NotExpression) e).getOperand();
		} else {
			return UnaryExpression.mkUnExpr(Operator.NOT, e);
		}
	}

	/**
	 * Returns a view of the portion of this list between the specified
	 * fromIndex, inclusive, and toIndex, exclusive.
	 * 
	 * @param i
	 * @param j
	 * 
	 * @return Term
	 */
	public Term subTerm(int i, int j) {
		assert i < j && i >= 0;
		Term result = new Term();
		result.lits = lits.subList(i, j);
		return result;
	}

	/**
	 * same literals in the same order
	 * 
	 * @param o
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof Term) {
			Term t2 = (Term) o;
			return this.lits.equals(t2.lits);
		} else {
			return false;
		}
	}

	/**
	 * return the ints for this term.
	 *
	 * @param ids the ids (with the int for every ID)
	 * @return the list
	 */
	public List<Integer> convertTermInintClause(List<IdExpression> ids) {
		List<Integer> clause = new ArrayList<Integer>(ids.size());
		for (IdUNotIdExpression e: this) {
			int idIndex = getIntFromIdUNotId(ids, e);
			logger.debug("linking " + e + " -> " + idIndex );
			// it cannot contain already this expression
			if (clause.contains(idIndex))
				continue;
			if (clause.contains(-idIndex)) {
				// true found, just
				// TODO the clause is equivalent to true ??? TO check				
			}
			clause.add(idIndex);
		}
		return clause;
	}

	protected int getIntFromIdUNotId(List<IdExpression> ids, IdUNotIdExpression e) {
		int idIndex;
		if (e instanceof IdExpression) {
			IdExpression id = (IdExpression) e;
			idIndex = getIdIndex(ids, id);
		} else {
			// not id
			IdExpression id = (IdExpression) ((NotExpression) e).getOperand();
			idIndex = -getIdIndex(ids, id);
		}
		return idIndex;
	}
	
	/** index of id
	 * @param idsInE List<IdExpression>
	 * @param id IdExpression
	 * @return int
	 */
	protected int getIdIndex(List<IdExpression> ids, IdExpression id) {
		int index = ids.indexOf(id);
		assert index >= 0;
		// only one occurrence of the id in idsInE
		assert ids.lastIndexOf(id) == index;
		return index + 1;
	}

	

}
