package tgtlib.definitions.normalform.dnf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.NotIDExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.normalform.BoolNFExpression;
import tgtlib.definitions.normalform.NFExpressionConverter;
import tgtlib.definitions.normalform.PushNot;
import tgtlib.definitions.normalform.Term;
/**
 * Converter to DNF expression
 * 
 * @author garganti
 *
 * @version $Revision: 1.0 $
 */
public final class DNFExprConverter extends NFExpressionConverter<DNFExpression> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DNFExprConverter.class);
	
	private DNFExprConverter(){}
		
	private static DNFExprConverterVisitor converter = new DNFExprConverter.DNFExprConverterVisitor();
	
	/**
	 * Method getDNF.
	 * @param expr Expression
	 * @return DNFExpression
	 */
	public static DNFExpression getDNF(Expression expr){
		// push the not in teh negation 
		// NOTE: no longer necessary -but it may be useful to reduce the complexisty of DNF!!
		expr = expr.accept(PushNot.pushNot);
		logger.debug("not pushed " + expr);
		return expr.accept(converter);
	}

	
	static class DNFExprConverterVisitor implements ExpressionVisitor<DNFExpression>{ 
	
		
		@Override
		public DNFExpression forCaseExpression(CaseExpression caseExpression) {
			throw new RuntimeException("not implemented yet");
		}

	/** merge the terms
	 * 
	 * @param andExpression AndExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forAndExpression(AndExpression)
	 */
	@Override
	public DNFExpression forAndExpression(AndExpression andExpression) {
		Expression a1 = andExpression.getFirstOperand();
		Expression a2 = andExpression.getSecondOperand();
		BoolNFExpression dnf1 = a1.accept(this);
		assert dnf1 != null;
		BoolNFExpression dnf2 = a2.accept(this);
		assert dnf2 != null;
		List<Term> result = new ArrayList<Term>();
		// get the terms
		// dnf1 = t11 \/ t12 \/ ... t1n
		for(Term t1: dnf1.getTerms()){
			logger.debug("t1 "+ t1);
			// dnf2 = t21 \/ t22 \/ ... t2m
			for(Term t2: dnf2.getTerms()){
				logger.debug("t2 "+ t2);
				// newterm: t11 /\ t21
				Term t1andt2 = t1.merge(t2);
				if (t1andt2 == null) continue;
				result.add(t1andt2);
				logger.debug("adding term "+ t1andt2);
			}
			
		}
		return new DNFExpression(result);
	}
	/** simple: make the union of the terms
	 * 
	 * @param orExpression OrExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forOrExpression(OrExpression)
	 */
	@Override
	public DNFExpression forOrExpression(OrExpression orExpression) {
		BoolNFExpression dnf1 = orExpression.getFirstOperand().accept(this);
		assert dnf1 != null;
		BoolNFExpression dnf2 = orExpression.getSecondOperand().accept(this);
		assert dnf2 != null;
		List<Term> result = new ArrayList<Term>();
		result.addAll(dnf1.getTerms());
		result.addAll(dnf2.getTerms());
		return new DNFExpression(result);
	}
	/**
	 * Method forIdExpression.
	 * @param idExpression IdExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forIdExpression(IdExpression)
	 */
	@Override
	public DNFExpression forIdExpression(IdExpression idExpression) {
		System.out.print("id"+ idExpression);
		Term t = new Term(idExpression);
		return new DNFExpression(Collections.singletonList(t));
	}

	/**
	 * Method forNotExpression.
	 * @param notExpression NotExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotExpression(NotExpression)
	 */
	@Override
	public DNFExpression forNotExpression(NotExpression notExpression) {
		// particular case
		if ( notExpression.getOperand() instanceof IdExpression){
			Term t = new Term((NotIDExpression)notExpression);
			return new DNFExpression(Collections.singletonList(t));
		} else{
			// example
			// ! (ab + cd) = !a!c + !a!d + !b!c + !c!d		
			List<Term> result = new ArrayList<Term>();
			BoolNFExpression internal = notExpression.getOperand().accept(this);
			List<List<IdUNotIdExpression>> first = internal.allCombinations();
			for(List<IdUNotIdExpression> term: first){
				List<IdUNotIdExpression> negations = new ArrayList<IdUNotIdExpression>();
				for(Expression e:term){
					negations.add((IdUNotIdExpression) mkNotExpr(e));
				}		
			result.add(new Term(negations));
		}
		return new DNFExpression(result);
		}
	}
	

	/**
	 * Method forXOrExpression.
	 * @param xOrExpression XOrExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forXOrExpression(XOrExpression)
	 */
	@Override
	public DNFExpression forXOrExpression(XOrExpression xOrExpression) {
		Expression e1 = xOrExpression.getFirstOperand();
		Expression e2 = xOrExpression.getSecondOperand();
		BinaryExpression xorEq = getXorSimpl(e1, e2, true);
		return xorEq.accept(this);
	}

	
	/**
	 * Method forDivExpression.
	 * @param divExpression DivExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forDivExpression(DivExpression)
	 */
	@Override
	public DNFExpression forDivExpression(DivExpression divExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forEqualsExpression.
	 * @param equalsExpression EqualsExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forEqualsExpression(EqualsExpression)
	 */
	@Override
	public DNFExpression forEqualsExpression(EqualsExpression equalsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forGreaterEqualExpression.
	 * @param greaterEqualExpression GreaterEqualExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterEqualExpression(GreaterEqualExpression)
	 */
	@Override
	public DNFExpression forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forGreaterThanExpression.
	 * @param greaterThanExpression GreaterThanExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterThanExpression(GreaterThanExpression)
	 */
	@Override
	public DNFExpression forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Method forImpliesExpression.
	 * @param impliesExpression ImpliesExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forImpliesExpression(ImpliesExpression)
	 */
	@Override
	public DNFExpression forImpliesExpression(
			ImpliesExpression impliesExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forLessEqualExpression.
	 * @param lessEqualExpression LessEqualExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessEqualExpression(LessEqualExpression)
	 */
	@Override
	public DNFExpression forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forLessThanExpression.
	 * @param lessThanExpression LessThanExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessThanExpression(LessThanExpression)
	 */
	@Override
	public DNFExpression forLessThanExpression(
			LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forMinusExpression.
	 * @param minusExpression MinusExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forMinusExpression(MinusExpression)
	 */
	@Override
	public DNFExpression forMinusExpression(MinusExpression minusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forMultExpression.
	 * @param multExpression MultExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forMultExpression(MultExpression)
	 */
	@Override
	public DNFExpression forMultExpression(MultExpression multExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forNegExpression.
	 * @param negExpression NegExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNegExpression(NegExpression)
	 */
	@Override
	public DNFExpression forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forNextExpression.
	 * @param nextExpression NextExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNextExpression(NextExpression)
	 */
	@Override
	public DNFExpression forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forNotEqualsExpression.
	 * @param notEqualsExpression NotEqualsExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotEqualsExpression(NotEqualsExpression)
	 */
	@Override
	public DNFExpression forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Method forPlusExpression.
	 * @param plusExpression PlusExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forPlusExpression(PlusExpression)
	 */
	@Override
	public DNFExpression forPlusExpression(PlusExpression plusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method forPrimedIdExpression.
	 * @param primedIdExpression PrimedIdExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forPrimedIdExpression(PrimedIdExpression)
	 */
	@Override
	public DNFExpression forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Method forModuloExpression.
	 * @param moduloExpression ModuloExpression
	 * @return DNFExpression
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forModuloExpression(ModuloExpression)
	 */
	@Override
	public DNFExpression forModuloExpression(ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DNFExpression forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DNFExpression forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
	}
}
