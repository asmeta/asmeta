package tgtlib.definitions.normalform.dnf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.definitions.normalform.BoolNFExpression;
import tgtlib.definitions.normalform.Term;

/** dnf expressions
 * 
 * @author garganti
 *
 * @version $Revision: 1.0 $
 */
public class DNFExpression extends BoolNFExpression{

	/**
	 * Constructor for DNFExpression.
	 * @param expreS String
	 */
	public DNFExpression(String expreS) {
		super(parse(expreS));
	}

	/** given some terms
	 * 
	 * @param newDNF
	 */
	public DNFExpression(List<Term> newDNF) {
		super(newDNF);
	}
	
	/**
	 * Instantiates a new dNF expression. with only one id
	 *
	 * @param idExpression the id expression
	 */
	DNFExpression(IdUNotIdExpression idExpression) {
		super(idExpression);
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

	/** parse a string like ab!c + def and returns the list of term * @param expreS String
	 * @return List<Term>
	s*/
	static List<Term> parse(String expreS) {
		IdExpressionCreator iec = new IdExpressionCreator(){
			@Override
			public IdExpression createIdExpression(String _id, Type _type) {
				return super.createIdExpression(_id, BoolType.BOOLTYPE);
			}
		};
		String[] conjoints = expreS.split("\\+");
		List<Term> expre = new ArrayList<Term>();
		for (String co : conjoints) {
			Term coExpre = DNFExpression.parse(co.trim(), iec);
			expre.add(coExpre);
		}
		return expre;
	}

	/** from DNF to condition * @param e List<Term>
	 * @return Expression
	 */
	@Override
	public Expression makeExpressionFromTerms(List<Term> e) {
		return makeOrExpression(e);
	}
	
	/**
	 * Method makeOrExpression.
	 * @param e List<Term>
	 * @return Expression
	 */
	public static Expression makeOrExpression(List<Term> e) {
		assert e.size() > 0;
		// if only one term, return and
		Expression conjoint0 = e.get(0).conjoint();
		if (e.size() == 1) {
			return conjoint0;
		} else {
			return new OrExpression(conjoint0, makeOrExpression(e.subList(1, e.size())));
		}
	}
	
	/** to string as abc + def
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return toString(false);		
	}
	/**
	 * Method toString.
	 * @param addAnd boolean
	 * @return String
	 */
	public String toString(boolean addAnd) {
		StringBuffer result = new StringBuffer();
		for (Term term : getTerms()) {
			if (result.length() > 0)
				result.append(" + ");
			for (int i = 0 ; i < term.size(); i++){
				Expression lit = term.get(i);
				if (addAnd && i> 0 ){
					result.append(" & ");
				} 
				if (lit instanceof NotExpression) {
					NotExpression negLit = (NotExpression) lit;
					result.append('!').append(
							((IdExpression) negLit.getOperand()).getIdString());
				} else {
					result.append(((IdExpression) lit).getIdString());
				}
			}
		}
		return result.toString();
	}

	/**
	 * Method accept.
	 * @param ask ExpressionVisitor<T>
	 * @return T
	 * @see tgtlib.definitions.expression.Expression#accept(ExpressionVisitor<T>)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		return getEqExpression().accept(ask);
	}

	/**
	 * return a new DNF expression with a new Term at the i-th position
	 * 
	 * @param i
	 * @param newTerm
	 *            : if empty, just remove the i-th term
	
	 * @return BoolNFExpression
	 */
	public BoolNFExpression replaceTerm(int i, Term newTerm) {
		// new expression
		List<Term> newDNF = new ArrayList<Term>(getTerms());
		// replace the term
		if (newTerm.size() != 0 )
			newDNF.set(i, newTerm);
		else
			newDNF.remove(i);
		return new DNFExpression(newDNF);
	}

	/** equals
	 * eualsin literls in the same order ...
	 * ac + b /= b +ac
	 * @param o Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (o instanceof DNFExpression) {
			BoolNFExpression e2 = (BoolNFExpression) o;
			return this.terms.equals(e2.getTerms());
		} else 
			return false;
	}

	/** like abc!d : used for DNF * @param co String
	 * @param icc IdExpressionCreator
	 * @return Term
	 */
	static public Term parse(String co, IdExpressionCreator icc) {
		List<IdUNotIdExpression> termContent = new ArrayList<IdUNotIdExpression>();
		int begin = 0;
		while (begin < co.length()) {
			if (co.charAt(begin) == '!' || co.charAt(begin) == '~') {
				char id = co.charAt(begin + 1);
				if (!Character.isLetter(id))
					throw new RuntimeException("parsing term");
				termContent.add((IdUNotIdExpression) NotExpression.createNotExpression(icc.createIdExpression(String
						.valueOf(id), null)));
				begin += 2;
			} else {
				char id = co.charAt(begin);
				if (!Character.isLetter(id))
					throw new RuntimeException("parsing term id '" + id
							+ " found");
				termContent.add(icc.createIdExpression(String.valueOf(id), null));
				begin += 1;
			}
		}
		return new Term(termContent);
	}
	
}
