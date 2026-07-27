package tgtlib.definitions.normalform.cnf;

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
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.visitors.ImpliesRemover;
import tgtlib.definitions.normalform.NFExpressionConverter;
import tgtlib.definitions.normalform.PushNot;
import tgtlib.definitions.normalform.Term;

/**
 * Converter to CNF expression using simple equivalence rules
 * 
 * @author garganti
 * 
 * @version $Revision: 1.0 $
 */
public /*final*/ class CNFExprConverterNaive extends
		NFExpressionConverter<CNFExpression> implements CNFExprConverter {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CNFExprConverter.class);

	/**
	 * not to be used directly
	 * 
	 */
	private static final CNFExprEquivalentVisitor converter = new CNFExprEquivalentVisitor();

	private static final boolean PUSH_NOT = true;

	protected CNFExprConverterNaive() {
	}

	public static CNFExprConverterFactory instance = new CNFExprConverterFactory(){

		@Override
		public CNFExprConverter getCNFExprConverter() {
			return new CNFExprConverterNaive();
		}
	};

	/**
	 * returns the equivalent CNF. if empty it is equivalent to true (it is a tautology like a xor not a, a or not a, ...
	 * 
	 * @param expr
	 * 
	 * @return CNFExpression
	 */
	@Override
	public CNFExpression getCNF(Expression expr) {
		//logger.info("converting :" + expr);
		Expression p1 = expr;
		// remove implication
		p1 = p1.accept(ImpliesRemover.instance);		
		// push the not in the negation
		// NOTE: no longer necessary - but it may be useful to reduce the
		// complexity of CNF!!
		// necessary to get the idnf???
		// Convert to negation normal form.		
		//Move NOTs inwards by repeatedly applying DeMorgan's Law. Specifically, replace \neg (x \vee y) with (\neg x) \wedge (\neg y); replace \neg (x \wedge y) with (\neg x) \vee (\neg y); and replace \neg\neg x with x.
		p1 = p1.accept(PushNot.pushNot);
		// remove true and false constants
		p1 = p1.accept(RemoveFTConsts.instance);
		//assert !p1.toString().contains("false") : p1.toString();
		// assert not true or false constants
		if (p1 == BoolType.TRUE_CONST)
			throw new CNFException(expr, BoolType.TRUE_CONST);
		if (p1 == BoolType.FALSE_CONST)
			throw new CNFException(expr, BoolType.FALSE_CONST);
		CNFExpression res = p1.accept(converter);
		//logger.info("to CNF with n.terms :" + res.getTerms().size());
		return res;
	}

	public static class CNFExprEquivalentVisitor implements ExpressionVisitor<CNFExpression> {

		/**
		 * unite the terms CNF(e1 and e2) -> CNF(e1) U CNF(e2)
		 * 
		 * @param andExpression
		 *            AndExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forAndExpression(AndExpression)
		 */
		@Override
		public CNFExpression forAndExpression(AndExpression andExpression) {
			Expression a1 = andExpression.getFirstOperand();
			Expression a2 = andExpression.getSecondOperand();
			CNFExpression cnf1 = a1.accept(this);
			assert cnf1 != null;
			CNFExpression dnf2 = a2.accept(this);
			assert dnf2 != null;
			return uniteCNF(cnf1, dnf2);
		}

		private CNFExpression uniteCNF(CNFExpression cnf1, CNFExpression dnf2) {
			List<Term> result = new ArrayList<Term>();
			result.addAll(cnf1.getTerms());
			result.addAll(dnf2.getTerms());
			return new CNFExpression(result);
		}

		/**
		 * CNF(e1 or e2) -> [t11... and ... tin] or [t21 .. and .. t2m] -> CNF
		 * -> [t11 or t21] and ...
		 * 
		 * @param orExpression
		 *            OrExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forOrExpression(OrExpression)
		 */
		@Override
		public CNFExpression forOrExpression(OrExpression orExpression) {
			CNFExpression cnf1 = orExpression.getFirstOperand().accept(this);
			assert cnf1 != null;
			CNFExpression cnf2 = orExpression.getSecondOperand().accept(this);
			assert cnf2 != null;
			List<Term> result = new ArrayList<Term>();
			// get the terms
			//logger.debug("cnf1 " + cnf1);
			//logger.debug("cnf2 " + cnf2);
			// cnf1 = t11 and t12 and ... t1n
			for (Term t1 : cnf1.getTerms()) {
				// dnf2 = t21 and t22 and ... t2m
				for (Term t2 : cnf2.getTerms()) {
					// newterm: t11 or t21
					Term t1ort2 = t1.merge(t2);
					if (t1ort2 == null)
						// if empty (!a or a), then it is equal to true, skip it
						continue;
					result.add(t1ort2);
					//logger.debug("adding term " + t1ort2);
				}
			}
			return new CNFExpression(result);
		}

		/**
		 * Method forIdExpression.
		 * 
		 * @param idExpression
		 *            IdExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forIdExpression(IdExpression)
		 */
		@Override
		public CNFExpression forIdExpression(IdExpression idExpression) {
			//logger.debug("id " + idExpression);
			Term t = new Term(idExpression);
			return new CNFExpression(Collections.singletonList(t));
		}

		/**
		 * Method forNotExpression.
		 * 
		 * @param notExpression
		 *            NotExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotExpression(NotExpression)
		 */
		@Override
		public CNFExpression forNotExpression(NotExpression notExpression) {
			// particular case
			if (notExpression.getOperand() instanceof IdExpression) {
				Term t = new Term((IdUNotIdExpression)notExpression);
				return new CNFExpression(Collections.singletonList(t));
			} else {
				// pushNot
				if (PUSH_NOT) {
					return notExpression.accept(PushNot.pushNot).accept(this);
				} else {
					// allow not ( something ,..... not id)
					// NOTE that this code requires a lot of memory !!!!
					// example
					// ! ((a or b) and (c or d)) = (!a and !b) \/ (!c and ! d))
					// =
					List<Term> result = new ArrayList<Term>();
					CNFExpression internal = notExpression.getOperand().accept(
							this);
					List<List<IdUNotIdExpression>> first = internal.allCombinations();
					for (List<IdUNotIdExpression> term : first) {
						List<IdUNotIdExpression> negations = new ArrayList<IdUNotIdExpression>();
						for (Expression e : term) {
							negations.add((IdUNotIdExpression) mkNotExpr(e));
						}
						result.add(new Term(negations));
					}
					return new CNFExpression(result);
				}
			}
		}

		/**
		 * Method forXOrExpression.
		 * 
		 * @param xOrExpression
		 *            XOrExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forXOrExpression(XOrExpression)
		 */
		@Override
		public CNFExpression forXOrExpression(XOrExpression xOrExpression) {
			Expression e1 = xOrExpression.getFirstOperand();
			Expression e2 = xOrExpression.getSecondOperand();
			Expression xorEq = getXorSimpl(e1, e2, true);
			return xorEq.accept(this);
		}

		/**
		 * Method forNotEqualsExpression.
		 * 
		 * @param notEqualsExpression
		 *            NotEqualsExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotEqualsExpression(NotEqualsExpression)
		 */
		@Override
		public CNFExpression forNotEqualsExpression(
				NotEqualsExpression notEqualsExpression) {
			Expression e1 = notEqualsExpression.getFirstOperand();
			Expression e2 = notEqualsExpression.getSecondOperand();
			Expression xorEq = getXorSimpl(e1, e2, true);
			return xorEq.accept(this);
		}

		/**
		 * Method forEqualsExpression.
		 * 
		 *  a == b -> (a and b) or (not a and not b) -> [a or (not a and not b)] and [b or (not a and not b)] ->
		 *  ( a or not a) and (a or not b) and ( b or not a) and (b or not b) ->
		 * (a or not b) and ( b or not a)  
		 *  CNF(a==b) -> CNF(a or not b) U CNF (b or not a) 
		 * 
		 * @param equalsExpression
		 *            EqualsExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forEqualsExpression(EqualsExpression)
		 */
		@Override
		public CNFExpression forEqualsExpression(
				EqualsExpression equalsExpression) {
			Expression firstOperand = equalsExpression.getFirstOperand();
			Expression secondOperand = equalsExpression.getSecondOperand();
			Expression s1 = BinaryExpression.mkBinExpr(firstOperand, Operator.OR, NotExpression.createNotExpression(secondOperand));
			Expression s2 = BinaryExpression.mkBinExpr(secondOperand, Operator.OR, NotExpression.createNotExpression(firstOperand));
			CNFExpression cn1 = s1.accept(this);
			CNFExpression cn2 = s2.accept(this);
			// unit cn1 and cn2:
			return uniteCNF(cn1, cn2);			
		}

		/**
		 * Method forDivExpression.
		 * 
		 * @param divExpression
		 *            DivExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forDivExpression(DivExpression)
		 */
		@Override
		public CNFExpression forDivExpression(DivExpression divExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forGreaterEqualExpression.
		 * 
		 * @param greaterEqualExpression
		 *            GreaterEqualExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterEqualExpression(GreaterEqualExpression)
		 */
		@Override
		public CNFExpression forGreaterEqualExpression(
				GreaterEqualExpression greaterEqualExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forGreaterThanExpression.
		 * 
		 * @param greaterThanExpression
		 *            GreaterThanExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterThanExpression(GreaterThanExpression)
		 */
		@Override
		public CNFExpression forGreaterThanExpression(
				GreaterThanExpression greaterThanExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forImpliesExpression.
		 * 
		 * @param impliesExpression
		 *            ImpliesExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forImpliesExpression(ImpliesExpression)
		 */
		@Override
		public CNFExpression forImpliesExpression(ImpliesExpression impliesExpression) {
			throw new RuntimeException("not implemented yet");
		}

		/**
		 * Method forLessEqualExpression.
		 * 
		 * @param lessEqualExpression
		 *            LessEqualExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessEqualExpression(LessEqualExpression)
		 */
		@Override
		public CNFExpression forLessEqualExpression(
				LessEqualExpression lessEqualExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forLessThanExpression.
		 * 
		 * @param lessThanExpression
		 *            LessThanExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessThanExpression(LessThanExpression)
		 */
		@Override
		public CNFExpression forLessThanExpression(
				LessThanExpression lessThanExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forMinusExpression.
		 * 
		 * @param minusExpression
		 *            MinusExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forMinusExpression(MinusExpression)
		 */
		@Override
		public CNFExpression forMinusExpression(MinusExpression minusExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forMultExpression.
		 * 
		 * @param multExpression
		 *            MultExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forMultExpression(MultExpression)
		 */
		@Override
		public CNFExpression forMultExpression(MultExpression multExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forNegExpression.
		 * 
		 * @param negExpression
		 *            NegExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forNegExpression(NegExpression)
		 */
		@Override
		public CNFExpression forNegExpression(NegExpression negExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forNextExpression.
		 * 
		 * @param nextExpression
		 *            NextExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forNextExpression(NextExpression)
		 */
		@Override
		public CNFExpression forNextExpression(NextExpression nextExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forPlusExpression.
		 * 
		 * @param plusExpression
		 *            PlusExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forPlusExpression(PlusExpression)
		 */
		@Override
		public CNFExpression forPlusExpression(PlusExpression plusExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Method forPrimedIdExpression.
		 * 
		 * @param primedIdExpression
		 *            PrimedIdExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forPrimedIdExpression(PrimedIdExpression)
		 */
		@Override
		public CNFExpression forPrimedIdExpression(
				PrimedIdExpression primedIdExpression) {
			throw new RuntimeException("not implemented yet");
		}

		/**
		 * Method forModuloExpression.
		 * 
		 * @param moduloExpression
		 *            ModuloExpression
		 * @return CNFExpression
		 * @see tgtlib.definitions.expression.ExpressionVisitor#forModuloExpression(ModuloExpression)
		 */
		@Override
		public CNFExpression forModuloExpression(
				ModuloExpression moduloExpression) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public CNFExpression forFunctionTerm(FunctionTerm ft) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public CNFExpression forCaseExpression(CaseExpression caseExpression) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public CNFExpression forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public String toString() {
		return "CNFExprConverterNaive";
	}
}
