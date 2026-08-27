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
package atgt.generator.collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import atgt.coverage.AsmTestSequence;
import atgt.coverage.TestCondition;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.testsuite.ordering.TPCollectedChangeListener;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.TestSequence;
import tgtlib.generator.MCInput;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.generator.ordering.TPProcessor;

/**
 * this class implements several strategies to collect compatible TPs.
 * 
 * T is the type of the TestCondition to be collected. the must be of one
 * subclass only according to T a different collectedTestcondition is returned
 *
 * @param <T> the generic type for test conditions to be collected
 * @param <Q> the generic type representing the input
 * @param <R> the generic type the result of the collection
 */
public abstract class TPCompatibleCollector<T extends TestCondition<?>,Q extends MCInput<?>, R extends CollectedTestCondition<T, ?>> extends
		TPProcessor<R> {

	/** Logger for this class. */
	private static final Logger log = Logger.getLogger(TPCompatibleCollector.class);

	public enum ComputeWitness {
		never, // never compute the witness
		afterEveryTp, // every every test predicate (also at the end of the collection)
		atTheEnd // at the end of the collection (not by an extra call to the model checker)
	}
	// when computing the witness
	private static ComputeWitness computeWitness; 
	
	/** check if the witness of the collection is already a model?*/
	protected static boolean checkCollModel = false;

	
	/**
	 * @param computeWitness the computeWitness to set
	 */
	public static void setComputeWitness(ComputeWitness computeWitness) {
		TPCompatibleCollector.computeWitness = computeWitness;
	}

	/**
	 * @return the computeWitness
	 */
	public static ComputeWitness getComputeWitness() {
		return computeWitness;
	}


	/**
	 * @param computeFinalWitness to compute final witness (useful to avoid an extra call to the model checker)
	 * @param checkCollectionModel the check collection model when adding a new tp, check if the previous collection model is a model valid for it too.
	 */
	public static void setWitnessOptions(boolean computeFinalWitness, boolean checkCollectionModel){
		if (checkCollectionModel){
			setComputeWitness(ComputeWitness.afterEveryTp);
			checkCollModel = true;
		} else{
			checkCollModel = false;
			if (computeFinalWitness)
				setComputeWitness(ComputeWitness.atTheEnd);
			else
				setComputeWitness(ComputeWitness.never);				
		}
	}
	
/////// collector management part
	// a seconda dei TestCondition (T)
	// costruisce un certo TP compaitble collectotr
	// deve essere estensibile (se introduco un T nuovo e un nuovo collect posso pluggerli)
	// the collected test condition to be used

	public static class CollectorRegistrator<T extends TestCondition<?>> {
		
		HashMap<Class<? extends CoverageTree<T>>,TPCollectorFactory > collections; 

		CollectorRegistrator(){
			collections = new HashMap<Class<? extends  CoverageTree<T>>, TPCollectorFactory>();
			// for NWISE this this of default
			// TODO: remove
			// register(NWiseCoverage.class,TPNWiseCompatibleCollector.getFactory());			
		}
		/**
		 * Register collector. It must not registered yet
		 *
		 * @param class1 the class1 cl
		 * @param collfactory the coll clazz
		 */
		public void register(Class<? extends  CoverageTree<T>> class1, TPCollectorFactory collfactory){
			register( class1, collfactory, false);
		}

		/**
		 * Register collector.
		 *
		 * @param class1 the class1 cl
		 * @param collfactory the coll clazz
		 */
		public void register(Class<? extends CoverageTree<T>> class1, TPCollectorFactory collfactory, boolean override){
			log.info("registering a " + collfactory.getClass() + " for " + class1);
			assert(override || collections.get(class1) ==null): "class "+ class1 + " already registered";
			collections.put(class1, collfactory);			
		}
		
		/**
		 * unregister collector.
		 *
		 * @param class1 the class1 cl
		 * @param collfactory the coll clazz
		 */
		public void unregister(Class<? extends  CoverageTree<T>> class1){
			log.debug("un registering  for " + class1);
			collections.remove(class1);			
		}
		/** return the right collector factory for the coverage class
		 * 
		 * @param coverageClass
		 * @return the coverage builder
		 */
		public TPCollectorFactory getCollector(Class<? extends  CoverageTree<T>> coverageClass){
			// get type T (it is not possible because type erasure
			// check if exists class Collected
			TPCollectorFactory collector = collections.get(coverageClass);
			// return the instance of collector
			return collector;
		}
		
	}
	
	//TODO fix the type 
	static public final CollectorRegistrator collectorRegistrator = new CollectorRegistrator();
	
	
	
	/**
	 * the tp processor collector uses this auxiliary tp processor which can be a list
	 * (natural order) or whatever
	 */
	protected CollectionIterator<T,R> testCondsSeq;

	/**
	 * to be used to check consistency. NOte that axioms are already considered
	 * in
	 */
	//protected TestSequenceGenerator<T,?,?> tsgenerator;
	protected TestSequenceGenerator tsgenerator;

	/** the test conditions that are feasible */
	protected ArrayList<T> feasible;

//	protected TPCompatibleCollector(TPProcessor<T> tpp, TestSequenceGenerator<T,?,?> generator){
	protected TPCompatibleCollector(TPProcessor<T> tpp, TestSequenceGenerator generator){
			this(new StandardCollectionIterator<T,R>(tpp), generator);
	}

//	protected TPCompatibleCollector(CollectionIterator<T,R> tCondsIter, TestSequenceGenerator<T, ?, ?> generator){
	protected TPCompatibleCollector(CollectionIterator<T,R> tCondsIter, TestSequenceGenerator generator){
		testCondsSeq = tCondsIter;
		tsgenerator = generator;
		feasible = new ArrayList<T>();
	}
	
	/** 
	 * builds the collected testcondition to be used 
	 * 
	 */
	abstract public R createEmptyCollectedTestCondition();

	/**
	 * return the test conditions which can be composed together if none return
	 * null
	 * 
	 * @return at least one pair in collect, or null if the collect does not
	 *         contain any tp: no longer tps to collect
	 */
	@Override
	public final R next() {
		// collect and return
		return collect();
	}

	@Override
	public final void remove() {
		// do nothing (should remove all the tps in this composing?)
		// now when a tp is composed from the pipe is also removed
	}

	@Override
	public final void reset() {
		throw new RuntimeException("not supported");
	}

	/**
	 * Collect: ordinary collect strategy (from first element).
	 *
	 * @return the list< pair test condition> collected together: it can be null
	 */
	public final R collect() {
		// create  new collected test predicate
		R collect = createEmptyCollectedTestCondition();
		// if necessary register the testCondsSeq
		if (testCondsSeq instanceof TPCollectedChangeListener)
			collect.registerListener((TPCollectedChangeListener) testCondsSeq);
		// restart the underlying iterator
		testCondsSeq.reset();
		for(;;) {
			T ptc = testCondsSeq.getNextCandidate(collect);
			if (ptc == null) break; 
			if (ptc.getStatus() == TestConditionState.Covered){
				// already covered (may be becasue the colelction stopepd aerlier)
				assert ptc.allCoveredBy().size() > 0;
				log.debug("skipping " + ptc + " already covered by " + ((AsmTestSequence) ptc.allCoveredBy().iterator().next()).getName());
				continue;
			}
			// must be a queued otherwise should not be considered !
			assert ptc.getStatus() == TestConditionState.Queued;
			// skip if marked unfeasible
			// it should never happen
			/*if (ptc.getStatus() == TestConditionState.UNFEASIBLE) {
				testCondsSeq.remove();
				continue;
			}*/
			trytoadd(collect, ptc);
		}
		if (collect.isEmpty())
			return null;
		// compute the witness (no call to the model checker should be done)
		if (getComputeWitness() == ComputeWitness.atTheEnd) {
			computeWitness(collect);
		}
		// close the collection
		closeCollection(collect);
		log.debug("Collected: [" + collect.size() + "] " + collect.toString());
		return collect;
	}

	/** try to collect ptc (the candidate) with collect
	 * 
	 * @param collect: it may also be empty (in this case consider only ptc)
	 * @param ptc
	 */
	private final void trytoadd(R collect, T ptc) {
		assert ptc.getStatus() == TestConditionState.Queued : ptc.getStatusDescription();
		CHECK_RESULT result;
		// if computewitness not at every step and the tp is feasible and the first, just add
		if(collect.isEmpty() && getComputeWitness() != ComputeWitness.afterEveryTp && feasible.contains(ptc)){
			// add already the test predicate since it is feasible
			result = CHECK_RESULT.TO_ADD;
		} else {
			if (checkCollModel && !collect.isEmpty()){
				//log.debug("check model and collect not empty");
				//possible only if compute witness at every step
				assert getComputeWitness() == ComputeWitness.afterEveryTp;
				boolean isAModel = checkModel(collect.getWitness(),ptc);
				log.debug("check model? " + checkCollModel + ", collect empty? "+ collect.isEmpty() + ", is a model? "+ isAModel);		 
				if (isAModel) { 
					result = CHECK_RESULT.TO_ADD;
				}
				else {
					// it is not a model: try in any case to see if collectable
					// it should change the witness in case
					result = checkConsistency(collect,ptc);
				}
			} else { 
				log.debug("check model? " + checkCollModel + ", collect empty?"+ collect.isEmpty());		 
				// is empty or !checkCollmodel 
				result = checkConsistency(collect,ptc);				
			}
		}
		//log.debug(ptc + " found " + result + " [condition: " + ptc.getCondition() + "]");
		switch (result) {
		case TO_ADD:
			// there exist a model for pt and collect
			log.debug("tp "+ ptc + " found TO ADD");
			collect.addTestCondition(ptc);
			// note that the witness may be already set
			//assert(collect.getWitness() != null);
			// remove from the underlying collection (otherwise will be considered again)
			testCondsSeq.remove();
			break;
		case INCOMPATIBLE:
			checkInfeasible(ptc);
			// after that it is unfeasible or still queued
			assert (ptc.getStatus() == TestConditionState.UNFEASIBLE || ptc.getStatus() == TestConditionState.Queued): ptc.getStatusDescription();
			log.debug("tp "+ ptc + " found INCOMPATIBLE ("+ ptc.getStatus() + ")");
			break;
		case MARK_INFEASIBLE:
			// its is unfeasible !
			ptc.markInfeasible();
			testCondsSeq.remove();
			log.debug("tp "+ ptc + " found INFEASIBLE");
			break;
		default:
			assert false;
		}
		// the witness is set if and only if requested, and the collection is not empty 
		// by check consistency 
		assert !(collect.size() != 0) || ( (getComputeWitness() == ComputeWitness.afterEveryTp) == (collect.getWitness() != null)): 
			"cw " + getComputeWitness() + " size " + collect.size() + " result " + result + " collect.getWitness() " + collect.getWitness();
		return;
	}

	/**
	 * check if the ptc is feasible or not. It also removes from the underlying collection. 
	 *
	 * @param ptc the ptc
	 * @return true, if ptc is infeasible
	 */
	private boolean checkInfeasible(T ptc) {
		assert ptc.getStatus() == TestConditionState.Queued : ptc.getStatusDescription();		
		// check if the test predicate t is infeasible
		// if already checked feasible, do not check again
		if (!feasible.contains(ptc)) {
			if (!isFeasibleWithAxioms(ptc)) {
				log.debug("  checked ALONE with axioms --> UNFEASIBLE :" + ptc);
				// its is unfeasible !
				ptc.markInfeasible();
				testCondsSeq.remove();
				return true;
			} else {
				log.debug("  checked ALONE with axioms --> FEASIBLE :" + ptc);
				feasible.add(ptc);
				assert ptc.getStatus() == TestConditionState.Queued : ptc.getStatusDescription();		
				return false;
			}
		}
		return false;
	}

	/** is it feasible considering also the axioms?
	 * Assume that it is feasible without axioms but with them?*/
	protected abstract boolean isFeasibleWithAxioms(T ptc);

	public enum CHECK_RESULT{
		TO_ADD, // add to the collection 
		INCOMPATIBLE, // it cannot be added because it is incompatible with the other tps in the collection: check if it is infeasible
		MARK_INFEASIBLE;// it is unfeasible, no further checks
	} 
	
	/**
	 * check if ptc is compatible with collect and in case it may also set the witness for it in collect.
	 *
	 * @param collect the collect: it can be empty
	 * @param ptc the ptc 
	 * @return check result what to do with ptc? 
	 * 
	 */
	protected abstract CHECK_RESULT checkConsistency(R collect, T ptc);


	/**
	 * Check model for the test predicates
	 *
	 * @param witness the witness
	 * @param ptc the ptc
	 * @return true if it is model
	 */
	protected abstract boolean checkModel(TestSequence witness, T ptc);

	/** finished to collect the compute the witness for the collection (no call to the model checker with 
	 * the conjoint should be done)
	 * @throws IOException 
	 * @throws ModelCheckerExecutionException */
	protected abstract void computeWitness(R collect);
	
	/** close the collection */
	protected abstract void closeCollection(R collect);

}