/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.definitions.normalform.cnf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import tgtlib.definitions.expression.IdExpressionCreator;
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
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.visitors.ImpliesRemover;
import tgtlib.definitions.normalform.Term;

/**
 * vedi alcuni documenti come:
 * http://people.inf.ethz.ch/daniekro/classes/251-0247
 * -00/f2007/readings/Tseitin70.pdf
 * http://nl.wikipedia.org/wiki/Tseitin-transformatie
 * verify.rwth-aachen.de/lp10/exercises/exercise3.pdf
 * 
 * @author garganti
 * 
 */
public class CNFExprConverterTseitinNoIscas implements CNFExprConverterTseitin {

	// ATTENZIONE, se l'espressione contiene già questi ID si hanno problemi
	// this is the reason to avoid letters like "e" and so on
	public static final String ID_PREFIX = "phi";
	// per i bit
	IdExpressionCreator icc = new IdExpressionCreator();
	//
	int id_n = 0;

	// all the negations
	private Map<IdExpression, NotIDExpression> negations;
	// id for all the expressions
	// shared among conversions, to allow the reuse of parts
	private Map<Expression, IdExpression> definedIds;

	protected CNFExprConverterVisitor converter;

	public static CNFExprConverterFactory instance = new CNFExprConverterFactory() {
		@Override
		public CNFExprConverter getCNFExprConverter() {
			return new CNFExprConverterTseitinNoIscas();
		}
	};

	protected CNFExprConverterTseitinNoIscas() {
		// restarts the count
		id_n = 0;
		// init the negations and the id defined for it
		negations = new HashMap<IdExpression, NotIDExpression>();
		definedIds = new HashMap<Expression, IdExpression>();
	}

	/**
	 * two successive calls of getCNF can reuse some information
	 * 
	 */
	@Override
	public final CNFExpression getCNF(Expression e1) {
		// remove true and false
		Expression e = e1.accept(RemoveFTConsts.instance);
		// remove implication
		e = e.accept(ImpliesRemover.instance);
		assert ! e.toString().contains("implies") : e.toString();
		// if it is true or false, it cannot be converted to CNF
		if (e == BoolType.TRUE_CONST)
			throw new CNFException(e1,  BoolType.TRUE_CONST);
		if (e == BoolType.FALSE_CONST)
			throw new CNFException(e1, BoolType.FALSE_CONST);
		return callConverter(e);
	}

	protected CNFExpression callConverter(Expression e) {
		// reset the terms (every time, not only when resetting)
		converter = new CNFExprConverterVisitor(e);
		return converter.getCNF();
	}

	// useful when "a or b or c" -> a,b,c
	protected Collection<Expression> collect(BinaryExpression e) {
		Collection<Expression> result = new HashSet<Expression>();
		addExpressions(e, result, e.getClass());
		return result;
	}

	// visit the expression tree
	protected void addExpressions(Expression e, Collection<Expression> exps,
			Class<? extends BinaryExpression> exprType) {
		if (e.getClass() == exprType) {
			addExpressions(((BinaryExpression) e).getFirstOperand(), exps,
					exprType);
			addExpressions(((BinaryExpression) e).getSecondOperand(), exps,
					exprType);
		} else {
			exps.add(e);
		}
	}

	enum opType {
		AND, OR, NOR, NAND, NXOR
	}

	// visits the expression recursively:
	// add the necessary definitions in terms and build the new ids.
	// creates the id and if necessary add its definition in terms
	protected class CNFExprConverterVisitor implements
			ExpressionVisitor<IdUNotIdExpression> {

		protected Expression eUnderConversion;
		// the terms containing the definitions
		// insertion must be done carefully, use method add new term
		protected List<Term> terms;

		protected CNFExprConverterVisitor(Expression e) {
			eUnderConversion = e;
			terms = new ArrayList<Term>();
		}

		protected CNFExpression getCNF() {
			// visits the expression
			IdUNotIdExpression finalExpr = eUnderConversion.accept(this);
			// it may
			if (finalExpr != null)
				addAsNewTerm(finalExpr);
			return buildCNF();
		}

		protected CNFExpression buildCNF() {
			return new CNFExpression(terms);
		}

		@Override
		public IdUNotIdExpression forIdExpression(IdExpression idExpression) {
			// this does not need anything
			return idExpression;
		}

		@Override
		public IdUNotIdExpression forAndExpression(AndExpression and) {
			return processExpression(and, opType.AND);
		}

		@Override
		public IdUNotIdExpression forOrExpression(OrExpression or) {
			return processExpression(or, opType.OR);
		}

		/**
		 * Given the definition e = op
		 * 
		 * @param e
		 * @param conjs
		 * @return
		 */
		private IdUNotIdExpression processExpression(BinaryExpression e,
				opType or) {
			// if already defined
			if (definedIds.containsKey(e))
				return definedIds.get(e);
			// othewise collect
			// else
			Collection<Expression> conjs = collect(e);
			return processExpression(e, conjs, or);
		}

		private IdUNotIdExpression processExpression(Expression e,
				Collection<Expression> conjs, opType or) {
			IdExpression bitId;
			if (e != eUnderConversion) {
				bitId = getNewIdLiteral(e);
			} else {
				bitId = null;
			}
			List<IdUNotIdExpression> temp = new ArrayList<IdUNotIdExpression>();
			// add only all the dijoints
			for (Expression c : conjs) {
				// visits the expression
				temp.add(c.accept(converter));
			}
			// add a new term with the definitions
			addEqTermFor(bitId, or, temp);
			return bitId;
		}

		@Override
		public IdUNotIdExpression forXOrExpression(XOrExpression xOr) {
			// if already defined
			if (definedIds.containsKey(xOr))
				return definedIds.get(xOr);
			// to be defined
			IdUNotIdExpression lid = xOr.getFirstOperand().accept(this);
			IdUNotIdExpression rid = xOr.getSecondOperand().accept(this);
			IdUNotIdExpression negl = getNegation(lid);
			IdUNotIdExpression negr = getNegation(rid);
			if (xOr == eUnderConversion) {
				// true
				// a xor b = (!a or !b) and (a or b)
				addAsNewTerm(negl, negr);
				addAsNewTerm(lid, rid);
				return null;
			} else {
				IdExpression bitId = getNewIdLiteral(xOr);
				IdUNotIdExpression negx = getNegation(bitId);
				// x <-> a xor b => !x+a+b * !x+!a+!b * x+!a+b + x+a+!b
				addAsNewTerm(negx, lid, rid);
				addAsNewTerm(negx, negl, negr);
				addAsNewTerm(bitId, negl, rid);
				addAsNewTerm(bitId, lid, negr);
				return bitId;
			}
		}

		@Override
		public IdUNotIdExpression forNotExpression(NotExpression not) {
			// if already defined
			if (definedIds.containsKey(not))
				return definedIds.get(not);
			// get the operand and visit it
			Expression in = not.getOperand();
			if (in instanceof NotExpression) {
				// eliminate the double not
				return ((NotExpression) in).getOperand().accept(this);
			} else if (in instanceof IdExpression) {
				// no need to be defined.it is not a
				// if negations.put(in, not);
				return getNegation((IdExpression) in);
			} else if (in instanceof OrExpression) {
				Collection<Expression> conjs = collect((OrExpression) in);
				// NOR
				return processExpression(not, conjs, opType.NOR);
			} else if (in instanceof AndExpression) {
				// NAND
				Collection<Expression> conjs = collect((AndExpression) in);
				return processExpression(not, conjs, opType.NAND);
			} else if (in instanceof XOrExpression){
				// NOT XOR ...
				Expression e1 = ((XOrExpression) in).getFirstOperand();
				Expression e2 = ((XOrExpression) in).getSecondOperand();
				return processExpression(not, Arrays.asList(e1,e2),opType.NXOR);
			} else{				
					throw new RuntimeException("not followed by ???" + not.getOperand().getClass());
				
			}
		}

		private void addAsNewTerm(List<IdUNotIdExpression> exprs) {
			addAsNewTerm(exprs.toArray(new IdUNotIdExpression[exprs.size()]));
		}

		// add in terms as new term a set of conjoints checking not to add not a
		// and a
		// in a term adding a and not a is ininfluent, just skip
		protected void addAsNewTerm(IdUNotIdExpression... exprs) {
			List<IdUNotIdExpression> list = new ArrayList<IdUNotIdExpression>();
			for (IdUNotIdExpression e : exprs) {
				if (e instanceof IdExpression
						&& list.contains(UnaryExpression.mkUnExpr(Operator.NOT,
								e))) {
					return;
				} else if (e instanceof NotIDExpression
						&& list.contains(((NotExpression) e).getOperand())) {
					return;
				} else {
					list.add(e);
				}
			}
			terms.add(new Term(list));
		}

		/**
		 * add the terms for id = op literals
		 * 
		 * @return
		 */
		void addEqTermFor(IdExpression id, opType op,
				List<IdUNotIdExpression> literals) {
			IdUNotIdExpression nid = (id != null) ? getNegation(id) : null;
			List<IdUNotIdExpression> temp = new ArrayList<>();
			switch (op) {
			case AND:
				// x = (a and b) => (! x + a ) * (!x + b) * (!a + !b + x)
				// x = and_i a_i => (+_i !a_i + x) *_i (!id + a_i)
				// if x null (true) => *_i a_i
				for (IdUNotIdExpression l : literals) {
					if (id == null) {
						addAsNewTerm(l);
					} else {
						// (!id + a_i)
						addAsNewTerm(l, nid);
						// +_i !a_i
						temp.add(getNegation(l));
					}
				}
				if (id != null) {
					temp.add(id);
					addAsNewTerm(temp);
				}
				break;
			case OR:
				// x = a or b => (x + ! a) * (x + ! b) * (! x + a + b)
				// i operands
				// x = or_i a_i => *_i (x or ! a_i) * (! x or_i a_i)
				// if x null (true): => +_i a_i
				for (IdUNotIdExpression l : literals) {
					if (id == null) {
						temp.add(l);
					} else {
						// (x or ! a_i)
						addAsNewTerm(id, getNegation(l));
						// (! x or_i a_i)
						temp.add(l);
					}
				}
				if (id != null) {
					temp.add(nid);
				}
				addAsNewTerm(temp);
				break;
			case NOR:
				// x = !(a or b) ==> (a or b or x) and (!a or !x) and (!b or !x)
				// i operands
				// x = not (or_i a_i) ==> (x +_i a_i) * *_i (!a_i + !x)
				// if x == null (true): => *_i !a_i
				for (IdUNotIdExpression l : literals) {
					if (id == null) {
						addAsNewTerm(getNegation(l));
					} else {
						temp.add(l);
						// (!a_i + !x)
						addAsNewTerm(getNegation(l), nid);
					}
				}
				if (id != null) {
					temp.add(id);
					addAsNewTerm(temp);
				}
				break;
			case NAND:
				// x = !(a and b) ==> (!a or !b or !x) and (a or x) and (b or x)
				// i operands
				// x = not (and_i a_i) ==> (!x +_i !a_i) * *_i (a_i + x)
				// if x == null (true): => +_i !a_i
				for (IdUNotIdExpression l : literals) {
					if (id == null) {
						temp.add(getNegation(l));
					} else {
						addAsNewTerm(l, id);
						temp.add(getNegation(l));
					}
				}
				if (id != null) {
					temp.add(nid);
				}
				addAsNewTerm(temp);
				break;
			case NXOR:
				if (literals.size() != 2) throw new RuntimeException();
				IdUNotIdExpression l1 = literals.get(0);
				IdUNotIdExpression nl1 = getNegation(l1);
				IdUNotIdExpression l2 = literals.get(1);
				IdUNotIdExpression nl2 = getNegation(l2);
				if (id == null) {
					// not (a xor b) <=> ( a = b) <=> (a or !b) and (!a or b)
					addAsNewTerm(l1, nl2);
					addAsNewTerm(nl1,l2);					
				} else {
					// provato con yices
					// (assert (/= 
					//           (= c (not (/= a b)))
					//           (and (or a (not b) (not c)) (or (not a) b (not c)) (or (not a) (not b) c) (or a b c))					
					//         ))
					addAsNewTerm(nid, l1, nl2);
					addAsNewTerm(nid, nl1,l2);					
					addAsNewTerm(id,nl1, nl2);
					addAsNewTerm(id,l1,l2);					
				}
				break;
			default:
				throw new RuntimeException();
			}
		}

		@Override
		public IdUNotIdExpression forEqualsExpression(
				EqualsExpression equalsExpression) {
			throw new RuntimeException();
		}

		@Override
		public IdUNotIdExpression forPrimedIdExpression(
				PrimedIdExpression primedIdExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forNextExpression(
				NextExpression nextExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forFunctionTerm(FunctionTerm ft) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forImpliesExpression(
				ImpliesExpression impliesExpression) {
			throw new RuntimeException("it should not contain implies !!!"); 
		}

		@Override
		public IdUNotIdExpression forGreaterEqualExpression(
				GreaterEqualExpression greaterEqualExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forGreaterThanExpression(
				GreaterThanExpression greaterThanExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forLessEqualExpression(
				LessEqualExpression lessEqualExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forLessThanExpression(
				LessThanExpression lessThanExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forNotEqualsExpression(
				NotEqualsExpression notEqualsExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forDivExpression(DivExpression divExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forPlusExpression(
				PlusExpression plusExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forMinusExpression(
				MinusExpression minusExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forMultExpression(
				MultExpression multExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forNegExpression(NegExpression negExpression) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdUNotIdExpression forModuloExpression(
				ModuloExpression moduloExpression) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public IdUNotIdExpression forCaseExpression(CaseExpression caseExpression) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public IdUNotIdExpression forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * given an expression e, it returns the ID or not ID for the not e.
	 * Examples: x => not x (with x an id, not is memorized)
	 * 
	 * expr is id or not id
	 */
	private IdUNotIdExpression getNegation(IdUNotIdExpression expr) {
		if (expr instanceof IdExpression) {
			// if not id ...
			NotIDExpression negx = negations.get(expr);
			if (negx == null) {
				negx = (NotIDExpression) NotExpression
						.createNotExpression(expr);
				negations.put((IdExpression) expr, negx);
			}
			return negx;
		} else {
			// it is expr = not id
			return (IdUNotIdExpression) ((NotExpression) expr).getOperand();
		}
	}

	/**
	 * return the idexpreesion or not idexpression for an expression (not
	 * defined yet)
	 * 
	 * @param e
	 * @return
	 */
	private IdExpression getNewIdLiteral(Expression e) {
		IdExpression id = definedIds.get(e);
		assert id == null;
		id = icc.createIdExpression(ID_PREFIX + id_n++, null);
		definedIds.put(e, id);
		return id;
	}

	/**
	 * return the idexpression or not idexpression for an expression (already
	 * defined, unless id or not id)
	 * 
	 * @param e
	 * @return
	 */
	private IdUNotIdExpression getLiteral(Expression e) {
		if (e instanceof IdExpression) {
			return (IdExpression) e;
		} else if (e instanceof NotExpression) {
			Expression operand = ((NotExpression) e).getOperand();
			IdUNotIdExpression idL = getLiteral(operand);
			return getNegation(idL);
			// else{
			// it may that e is not defined because is not (not a) and "not a"
			// is not defined
			// return (IdExpression)((NotExpression)operand).getOperand();
			// }
		} else if (e instanceof OrExpression || e instanceof AndExpression
				|| e instanceof XOrExpression) {
			IdExpression id = definedIds.get(e);
			assert id != null;
			return id;
		}
		throw new RuntimeException("");
	}

	@Override
	public String toString() {
		return "CNFExprConverterTseitinNoIscas";
	}

}