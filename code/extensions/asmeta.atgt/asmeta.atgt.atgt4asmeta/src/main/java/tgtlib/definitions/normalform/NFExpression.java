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
package tgtlib.definitions.normalform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tgtlib.definitions.expression.Expression;

/**
 * represents normal form expressions
 * [[a1,a2,...,an],[b1,b2,...,bm],....]  where every element is an Id, or primed Id , or
 * [primed] negated id (it can be also an atomic boolean expression like x < 5)
 * 
 * T is a single Term (list of expressions or a subset of them)
 *
 * @param <T> the generic type representing the term (it can be a list or any iterable over S
 * @param <S> the generic type representing what is inside T
 * @author garganti
 */
public abstract class NFExpression<T extends Iterable<S>,S extends Expression> implements Iterable<T>{

	protected List<T> terms;
	
	/** Creates a new instance of an empty NFExpression */
	public NFExpression() {
		terms = new LinkedList<T>();
	}

	/** build an unmodifiable NF expression
	 * 
	 * @param expressions
	 */
	public NFExpression(List<T> expressions) {
		this.terms = Collections.unmodifiableList(expressions);
	}

	/** NF expression with only one IdExpression: idExpressions must be included
	 * PrimedIdUIdExpression in general
	 * */
	public NFExpression(S exp) {
		terms = new LinkedList<T>();
		T  newTerm = makeNewTerm(exp);
		terms.add(newTerm);
	}
	/** new term with only one expression
	 * 
	 * @param exp
	 * @return
	 */
	protected abstract T makeNewTerm(S exp);

	@Override
	public Iterator<T> iterator() {
		return terms.iterator();
	}

	/** generate the combinations (transpose the matrix
	 * index = 0: first element
	 * index = size -1 : last element
	 */
	private static <P, Q extends Iterable<P>> List<List<P>> combinations(List<Q> a, int index){
		List<List<P>> result = new ArrayList<List<P>>();
		// base of recursion
		if (a.size() - 1 == index){
			// [[a,b,c]] -> [a],[b],[c]
			for(P t: a.get(index))
				result.add(Collections.singletonList(t));
			return result;
		} else{
			Q consider = a.get(index);
			List<List<P>> combineWith = combinations(a, index +1);
			for(P l1 : consider)
				for (List<P> ll2: combineWith){
					List<P> toAdd = new ArrayList<P>();
					toAdd.add(l1);
					toAdd.addAll(ll2);
					result.add(toAdd);
				}
			return result;
		}
	}

	public static <P, Q extends Iterable<P>> List<List<P>> combinations(List<Q> li) {
		return combinations(li,0);
	}

	public List<List<S>> allCombinations() {
		return combinations(this.terms);
	}

	
}
