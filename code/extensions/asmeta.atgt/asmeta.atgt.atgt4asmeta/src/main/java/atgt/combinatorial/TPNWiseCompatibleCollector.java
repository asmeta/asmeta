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
package atgt.combinatorial;

import java.util.Collection;

import org.apache.log4j.Logger;

import atgt.coverage.TestCondition;
import atgt.generator.collection.TPCollectorFactory;
import atgt.generator.collection.TPCompatibleCollector;
import tgtlib.definitions.TestSequence;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.MCInput;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.generator.ordering.TPProcessor;
import tgtlib.specification.Axiom;
import tgtlib.util.Pair;

public class TPNWiseCompatibleCollector<Q extends MCInput<? extends TestCondition<?>>>
		extends TPCompatibleCollector<CombinatorialTestCondition, Q,CollectedNWiseTC> {

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(TPNWiseCompatibleCollector.class);

	
	/** The axioms: not used sinche the test generator whould take car of that already */
	//Collection<Axiom> axioms;
	// up to now only a flag to say if there exists axioms or not
	boolean axiomPresence;
	
	/** variables TODO no used yet. maybe in the future **/
	private Iterable<? extends tgtlib.definitions.expression.type.Variable> vars;

	/**
	 * Instantiates a new tP compatible collector.
	 * 
	 * @param _axioms
	 *            the _axioms
	 * @param coverageClass
	 *            TODO
	 * @param get
	 *            type T (it is not possible because type erasure
	 */
	public TPNWiseCompatibleCollector(Iterable<? extends tgtlib.definitions.expression.type.Variable> vars,
			Collection<Axiom> _axioms,
			// TestSequenceGenerator<TestCondition,?,Q> generator,
			//TestSequenceGenerator<CombinatorialTestCondition, ?, ?> generator,
			TestSequenceGenerator generator,
			TPProcessor<CombinatorialTestCondition> tp) {
		super(tp,generator);
		this.vars = vars;
		//axioms = _axioms;
		axiomPresence = _axioms.size() > 0; 		
	}

	@Override
	protected atgt.generator.collection.TPCompatibleCollector.CHECK_RESULT checkConsistency(
			CollectedNWiseTC collect, CombinatorialTestCondition ptc) {
		atgt.combinatorial.CollectedNWiseTC.CHECK_RESULT result;
		if (collect.isEmpty()){
			// it is not implied: it could be unfeasible
			result = atgt.combinatorial.CollectedNWiseTC.CHECK_RESULT.NOT_IMPLIED;
		} else {
			// check the consistency of collect with ptc without axioms
			result = collect.checkConsistencyByValue(ptc);
			
		}
		log.debug(ptc + " found " + result + " [condition: "+ ptc.getCondition() + "]");
		switch (result) {
		case INCONSISTENT:
			// collect -> not ptc (ogni modello per collect non è modello per ptc)
			// e.g. a = ON vs a = OFF
			// skip (check if its infeasible)
			return CHECK_RESULT.INCOMPATIBLE;
		case IMPLIED:
			// collect -> ptc (ogni modello per collect è modello di ptc)
			return CHECK_RESULT.TO_ADD;			
		case NOT_IMPLIED:
			// esiste un modello per collect e ptc, però bisogna considerere gli
			// assiomi
			// not implied: call the model checker
			if (consistentWithAxioms(ptc,collect)) {
				log.debug("  checked with axioms --> CONSISTENT (model found)");
				return CHECK_RESULT.TO_ADD;
			} else {
				log.debug("  checked with axioms --> INCONSISTENT (no model found)");
				// return that cannot added, to be checkd if it is unfeasible
				return CHECK_RESULT.INCOMPATIBLE;
			}
		default:
			throw new RuntimeException();
		}
	}

	/**
	 * return true if a model for ptc AND collect AND (AXIOMS) exists
	 * 
	 * @param ptc
	 * @param collect
	 *            : the collect, potrebbe essere vuota (per il primo)
	 * @param axioms
	 * @return
	 */
	public boolean consistentWithAxioms(CombinatorialTestCondition ptc, CollectedNWiseTC collect) {
		// check if consistent with axioms
		if (axiomPresence) {
			TestCondition<?> co = collect.conjointt(ptc);
			CombinatorialTestCondition tc = new CombinatorialTestCondition(co.getName(), co.getCondition()) {
			};
			return checkModelEx(tc);
		}
		log.debug("no axioms, assuming consistency");
		return true;
	}

	@Override
	protected boolean isFeasibleWithAxioms(CombinatorialTestCondition ptc) {
		if (axiomPresence) {
			return checkModelEx(ptc);
		}
		log.debug("no axioms, assuming feasibility");
		return true;
	}
	/**
	 * check if a model for tc exists
	 * 
	 * @param tc
	 * @return
	 */
	private boolean checkModelEx(CombinatorialTestCondition tc) {
		// call the test seq generator
		Pair<MCAnalysisResult, ?> result;
		try {
			result = tsgenerator.executeAndAnalyze(tc);
			MCAnalysisResult ar = result.getFirst();
			//
			if (ar.isUnfeasible())
				return false;
			if (ar.isTestFound())
				return true;
		} catch (ModelCheckerExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//
		throw new RuntimeException("some errors in checking consistency");
	}

	/**
	 * return true if a model for the ptc exists
	 * 
	 * @param ptc
	 * @param axioms2
	 * @return
	 */
	public boolean consistent(CombinatorialTestCondition ptc, Collection<Axiom> axioms2) {
		log.debug("building model for of " + ptc);
		return checkModelEx(ptc);
	}

	/**
	 * builds the collected testcondition to be used
	 * 
	 */
	@Override
	public CollectedNWiseTC createEmptyCollectedTestCondition() {
		return new CollectedNWiseTC();
	}

	/**
	 * return the catory for this class
	 * 
	 * @return
	 */
	public static TPCollectorFactory getFactory() {
		return new TPCollectorFactory<CombinatorialTestCondition>() {

			@Override
			public TPCompatibleCollector build(Iterable<? extends tgtlib.definitions.expression.type.Variable> vars,
					Collection<Axiom> _axioms,
//					TestSequenceGenerator<CombinatorialTestCondition, ?,  ?> generator,
					TestSequenceGenerator generator,
					TPProcessor<CombinatorialTestCondition> tp) {
				// TODO Auto-generated method stub
				// return new TPNWiseCompatibleCollector<TestCondition,
				// MCInput<TestCondition>>(vars, _axioms, generator, tp);
				return new TPNWiseCompatibleCollector<MCInput<CombinatorialTestCondition>>(vars, _axioms, generator, tp);
			}
		};
	}

	@Override
	protected void computeWitness(CollectedNWiseTC collect) {
		throw new RuntimeException("not implemented yet"); 
	}

	@Override
	protected void closeCollection(CollectedNWiseTC collect) {
	}

	@Override
	protected boolean checkModel(TestSequence witness,CombinatorialTestCondition ptc) {
		throw new RuntimeException("not implemented yet");
	}

}
