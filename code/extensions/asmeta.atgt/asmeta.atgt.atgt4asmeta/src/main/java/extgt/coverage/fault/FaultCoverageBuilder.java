/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/

package extgt.coverage.fault;

import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.coverage.CoverageBuilder;
import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.normalform.NFExpressionConverter;
import tgtlib.specification.Specification;
import tgtlib.specification.SpecificationAnalyzer;
import extgt.coverage.fault.mutators.ExpressionVisitMutator;

/**
 * given a Fault expression, return the xor of the mutation of all the guard in
 * the specification.
 *
 * @param <T> the generic type for specification
 * @param <P> the generic type for test predicate
 * @param <Q> the generic type coverage tree
 * @param <R> the generic type expression to be mutated
 * @author garganti
 */
public abstract class FaultCoverageBuilder<S extends Specification, P extends TestPredicate<?,?>, C extends CoverageTree<? super P>,R extends Expression>
		implements CoverageBuilder<S, C> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(FaultCoverageBuilder.class);

	/** simplify xor to push it near the literals? Default false;*/
	private static boolean simplifyXor = false;

	/**
	 * tp factory
	 * 
	 */
	private TestPredicateFactory<P> tpFact;

	/**
	 * Creates a new instance of FaultCoverageBuilder. TODO: reduce the signature
	 * 
	 * @param fev
	 *            the fev
	 * @param specAn
	 *            useless
	 * @param ctf
	 * useless
	 * @param tpf
	 *            TODO
	 */
	FaultCoverageBuilder(ExpressionVisitMutator<?> fev,
			SpecificationAnalyzer<List<NamedTerm>, S> specAn,
			CoverageTreeFactory<C> ctf, TestPredicateFactory<P> tpf) {
		this(tpf);
	}

	/**
	 * Instantiates a new fault coverage builder.
	 *
	 * @param factory the factory to build new test predicates
	 */
	public FaultCoverageBuilder(TestPredicateFactory<P> factory) {
		tpFact = factory;
	}

	/**
	 * make the xor between condition and its faulty implementation
	 * 
	 * @param condition
	 *            original condition
	 * @param f
	 *            faulty mutation
	 * @param name
	 *            name of the test predicate
	 * @return
	 */
	protected final P makeTestPredicate(Expression condition, Expression f, String name) {
		Expression dCond;
		if (simplifyXor){
			// simplify the xor
			dCond = NFExpressionConverter.getXorSimpl(condition, f,false);
			// simplify xor and also the not
			//dCond = DNFExprConverter.getXorEqPushNot(condition, f);
		} else {
			dCond = new XOrExpression(condition, f);			
		}
		P toAdd = tpFact.buildTestPredicate(name, dCond);
		// check if f is equal to condition, then mark as identical
		if (condition.equals(f)) {
			logger.debug("equivalent identical mutation found");
			toAdd.markInfeasible();
		}
		return toAdd;
	}
	
	
	/**
	 * Gets the mutation of e
	 *
	 * @param e the e (type R, an expression or similar)
	 * @return the list of mutations  
	 */
	abstract protected List<NamedTerm> getMutants(R e);

	/**
	 * @return the simplifyXor
	 */
	public static boolean isSimplifyXor() {
		return simplifyXor;
	}

	/**
	 * @param simplifyXor the simplifyXor to set
	 */
	public static void setSimplifyXor(boolean simplifyXor) {
		FaultCoverageBuilder.simplifyXor = simplifyXor;
	}
}
