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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.normalform.Term;
import tgtlib.definitions.normalform.iscas.And;
import tgtlib.definitions.normalform.iscas.Bit;
import tgtlib.definitions.normalform.iscas.BitVisitor;
import tgtlib.definitions.normalform.iscas.Buff;
import tgtlib.definitions.normalform.iscas.FromExpressionToIscas;
import tgtlib.definitions.normalform.iscas.NotBit;
import tgtlib.definitions.normalform.iscas.NotVar;
import tgtlib.definitions.normalform.iscas.Or;
import tgtlib.definitions.normalform.iscas.Var;
import tgtlib.definitions.normalform.iscas.Xor;
/** vedi alcuni documenti come:
 *  http://people.inf.ethz.ch/daniekro/classes/251-0247-00/f2007/readings/Tseitin70.pdf
 *  http://nl.wikipedia.org/wiki/Tseitin-transformatie
 *  verify.rwth-aachen.de/lp10/exercises/exercise3.pdf
 *  
 * @author pa
 *
 */
public class CNFExprConverterTseitinViaIscas implements BitVisitor<List<Term>>, CNFExprConverterTseitin {
	// per i bit
	static IdExpressionCreator icc = new IdExpressionCreator();
	// all the negations
	static Map<IdExpression, NotExpression> negations = new HashMap<IdExpression, NotExpression>();

	public static CNFExprConverterFactory instance = new CNFExprConverterFactory(){
		@Override
		public CNFExprConverter getCNFExprConverter() {
			return new CNFExprConverterTseitinViaIscas();
		}
		
	};
	// converter to iscas
	FromExpressionToIscas conv1;
	
	private CNFExprConverterTseitinViaIscas(){
		// build a new convert to iscas
		conv1 = new FromExpressionToIscas();
	}
	
	@Override
	public CNFExpression getCNF(Expression e1) {
		// remove true and false
		Expression e = e1.accept(RemoveFTConsts.instance);
		if (e == BoolType.TRUE_CONST) throw new CNFException(e1,BoolType.TRUE_CONST);
		if (e == BoolType.FALSE_CONST) throw new CNFException(e1,BoolType.FALSE_CONST);
		
		//e = e.accept(PushNot.pushNot);
		List<Term> terms = new ArrayList<Term>();
		conv1.getBits(e);
		for(Bit b:conv1.getBits()) {
			terms.addAll(b.accept(this));
		}
		IdUNotIdExpression output = getLiteral(conv1.getOutput());
		terms.add(new Term(Arrays.asList(output)));
		return new CNFExpression(terms);
	}

	
	@Override
	public List<Term> forVar(Var var) {
		throw new RuntimeException("not implemnted");
	}

	@Override
	public List<Term> forAnd(And and) {
		IdExpression bitId = (IdExpression) getLiteral(and);
		IdUNotIdExpression lid = getLiteral(and.getLeft());
		IdUNotIdExpression rid = getLiteral(and.getRight());
		IdUNotIdExpression negx = getNegation(bitId);
		IdUNotIdExpression negl = getNegation(lid);
		IdUNotIdExpression negr = getNegation(rid);				
		List<Term> terms = new ArrayList<Term>();
		// if the Bit is AND
		// add x <-> (a and b)  =>  (!x + a ) * (!x + b) * (!a + !b + x)
		addInterm(terms, lid, negx);
		addInterm(terms, rid, negx);
		addInterm(terms,negl, negr, bitId);
		return terms;
	}

	@Override
	public List<Term> forOr(Or or) {
		IdExpression bitId = (IdExpression) getLiteral(or);
		IdUNotIdExpression lid = getLiteral(or.getLeft());
		IdUNotIdExpression rid = getLiteral(or.getRight());
		IdUNotIdExpression negx = getNegation(bitId);
		IdUNotIdExpression negl = getNegation(lid);
		IdUNotIdExpression negr = getNegation(rid);				
		List<Term> terms = new ArrayList<Term>();
		// x <-> a or b => (x or not a) * (x or not b) * (not x or a or b)
		addInterm(terms,bitId, negl);
		addInterm(terms,bitId, negr);
		addInterm(terms,negx, lid, rid);
		return terms;
	}

	@Override
	public List<Term> forXor(Xor xor) {
		IdExpression bitId = (IdExpression) getLiteral(xor);
		IdUNotIdExpression lid = getLiteral(xor.getLeft());
		IdUNotIdExpression rid = getLiteral(xor.getRight());
		IdUNotIdExpression negx = getNegation(bitId);
		IdUNotIdExpression negl = getNegation(lid);
		IdUNotIdExpression negr = getNegation(rid);				
		List<Term> terms = new ArrayList<Term>();
		// x <-> a xor b => !x+a+b * !x+!a+!b * x+!a+b + x+a+!b 
		addInterm(terms,negx,lid,rid);
		addInterm(terms,negx,negl,negr);
		addInterm(terms,bitId,negl,rid);
		addInterm(terms,bitId,lid,negr);
		return terms;
	}

	@Override
	public List<Term> forNotBit(NotBit notBit) {
		// x = not y diventa (x or y) and (not x or not y)
		Bit in = notBit.getNegated();
		IdExpression x = (IdExpression) getLiteral(notBit);
		IdUNotIdExpression notx = getNegation(x);
		IdUNotIdExpression y = getLiteral(in);
		IdUNotIdExpression noty = getNegation(y);
		List<Term> terms = new ArrayList<Term>();
		addInterm(terms,x,y);
		addInterm(terms, noty, notx);
		return terms;
	}

	@Override
	public List<Term> forNotVar(NotVar notVar) {
		throw new RuntimeException("not implemnted");
		//return Collections.EMPTY_LIST;
	}

	@Override
	public List<Term> forBuff(Buff buff) {
		// skip Buff, do not produce any literal
		return Collections.EMPTY_LIST;
	}

	/** id or not id */
	private static IdUNotIdExpression getNegation(Expression bitId) {
		if (bitId instanceof IdExpression){
			NotExpression negx = negations.get(bitId);
			if (negx == null){
				negx = NotExpression.createNotExpression(bitId);
				negations.put((IdExpression) bitId, negx);
			}
			return (IdUNotIdExpression) negx;
		} else {
			IdExpression id = (IdExpression) ((NotExpression)bitId).getOperand();
			return id;
		}
	}
	
	/** return the idexpression or notidexpression for a bit
	 * 
	 * @param b
	 * @return
	 */
	private static IdUNotIdExpression getLiteral(Bit b){
		if (b instanceof Var){
			return ((Var)b).getIdExpression();
		} else if (b instanceof NotVar){
			Var v = ((NotVar)b).getNegated();
			return getNegation(v.getIdExpression());
		} else if (b instanceof Buff){
			return ((Buff)b).getVar().getIdExpression();
		} else{
			IdExpression id = icc.createIdExpression(b.iscasName(), null);
			return id;
		}
		
	}
	// add in term checking not to add not a and a 
	private void addInterm(List<Term> terms, IdUNotIdExpression ... exprs) {
		List<IdUNotIdExpression> list = new ArrayList<IdUNotIdExpression>();
		for(IdUNotIdExpression e: exprs){
			if ( e instanceof IdExpression && list.contains(UnaryExpression.mkUnExpr(Operator.NOT, e))){
				return;
			} else if (e instanceof NotExpression && list.contains(((NotExpression)e).getOperand())){
				return;
			} else{
				list.add(e);
			}		
		}
		terms.add(new Term(list));
	}

	
}