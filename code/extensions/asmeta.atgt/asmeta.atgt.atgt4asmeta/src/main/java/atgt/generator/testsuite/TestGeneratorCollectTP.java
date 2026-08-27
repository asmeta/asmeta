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
package atgt.generator.testsuite;

import static atgt.preferences.ATGToolPreferences.CollectTPS;
import static atgt.preferences.ATGToolPreferences.TP_ORDERING;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestCondition;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.collection.CollectedTestCondition;
import atgt.generator.collection.TPCollectorFactory;
import atgt.generator.collection.TPCompatibleCollector;
import atgt.generator.collection.TPCompatibleCollector.ComputeWitness;
import atgt.generator.testsuite.ordering.PreferNovelty;
import atgt.generator.testsuite.ordering.PreferNoveltyCollect;
import atgt.preferences.ATGToolPreferences.OrderKind;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.TestSuiteFactory;
import tgtlib.definitions.expression.visitors.ExpressionEvaluator;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.generator.ordering.GenerationOrder;
import tgtlib.generator.ordering.TPProcessor;
import tgtlib.generator.ordering.TPProcessorFactory;
import tgtlib.project.Project;
import tgtlib.specification.Axiom;
import tgtlib.specification.Specification;
import tgtlib.util.Pair;

/**
 * The Class TestGeneratorCollectTP.
 * It generates for the entire coverage by using the collect if requested.
 * It should work also when not collecting is set
 * To be used directly on the AsmCoverage (that contains all the test predicates)
 * 
 * it operates with the collect algorithm
 */
public class TestGeneratorCollectTP<
S extends Specification, 
T extends TestSequence<? extends TP>, 
TP extends TestPredicate<? extends T,?>,
TS extends TestSuite<TP, T>, 
C extends CoverageTree<? extends TP>> extends TestSuiteGeneratorCov<S,T,TP,TS,C>{
	
	/** Logger for this class. */
	private static final Logger log = Logger.getLogger(TestGeneratorCollectTP.class);


	/** the real generator for a single TC.*/	
	private TestSequenceGenerator<TP,T,?> generator;
	
	//public static TPProcessorFactory<AsmTestCondition,AsmTestSequence> tpProcessorFactory;
		
	/**
	 * Instantiates a new test generator collect tp.
	 * 
	 * @param _project
	 *          the _project
	 * @param generator
	 *          the generator
	 */
	public TestGeneratorCollectTP(Project<S,TP,T,C> _project,
			TestSequenceGenerator generator,TestSuiteFactory testsuitefactory) {
		this(_project.specification,_project.getTestTree(),generator,testsuitefactory);
	}

	
	private TestGeneratorCollectTP(S specification,
			C testTree, TestSequenceGenerator gen, TestSuiteFactory testsuitefactory) {
		super(specification,testTree,testsuitefactory);
		this.generator = gen;	
	}

	/**
	 * For asm coverage.
	 *
	 * @param cv the cv
	 * @return the asm test suite
	 * @throws IOException 
	 * @throws ModelCheckerExecutionException 
	 */
	protected TS forAsmCoverage(C cv){
		List<TP> candidates = getCandidates(cv);
		if (candidates.isEmpty())
			return tsfactory.buildEmptyTestSuite();
		
		// ATTENZIONE se non è vuota devo controllare che TP sono !!!
		// now works only with Combinatorial Coverage (only the inputs)
		// XXXX questo controllo va nel collector
		/*if (!(cv instanceof CombinatorialCoverage)) {
			log.error("calling collect generator with a Coverage which is not combinatorial, but "
							+ cv.getClass().getName());
			return AsmTestSuite.getEmptyTestSuite();
		}*/
		// final result
		TS result = tsfactory.buildEmptyTestSuite(); 		
		// initialize the processor to all the candidates
		TPProcessor<TP> processor = getTPProcessor(result,candidates);
		// using collect ???
		boolean collect = CollectTPS.getValue();
		// XXXX questo controllo va nel collector
		// collect now works only with PairWiseCoverage
		if (collect){
			//if (cv instanceof NWiseCoverage)
			//processor = new TPCompatibleCollector2<NWiseEqTestCondition>(project.specification.getAxiom(),generator.generator, processor, NWiseEqTestCondition.class);
			Iterable<? extends tgtlib.definitions.expression.type.Variable> allVariables = specification.getVariables();
			Collection<Axiom> axioms = specification.getAxiom();
			processor = getCollector((Class<? extends AsmCoverage>) cv.getClass(),allVariables,axioms,processor); 
			//else
			//	log.error("no collect with " + cv.getClass().getSimpleName());			
		}				

		log.info("using  collect version, "+ cv.getClass().getSimpleName() + " coverage version, collect: " + collect
				+ ", ordering " + processor.getClass().getSimpleName()
				+ ", computing coverage: " + searchOtherCovs
				+ ", tcfilter: " + tcFilter
				+ ", candidates: " + candidates.size());
		
		TP compatible = null;
		// forever so the candidate can be printed
		for (;;) {
			log.debug("Candidates:[" + candidates.size() + "] "	+ NamedTerm.getNames(candidates));
			compatible = processor.next();
			// if no more compatible, finished
			if (compatible == null)
				break;
			log.debug("current tc "+ compatible.getName() + "=[" + compatible.toString()+"], status: " + ((TestCondition) compatible).getStatusDescription());
			// check if it is still valid
			// it must remove unselected or already covered
			// infatti alcuni potrebbe coprire altri
			// se è collect è dannoso perché il filtro non sa
			// come gestire i CollectedtestConditio. E' anche inutile perché tutti quelli
			// che potevo coprire li ho messi in collect
			// attention, unless collection is a partial collection (some are not collected because it's useless or inefficient)
			if (!collect && !tcFilter.accept(compatible)) {
				processor.remove();
				log.debug("tc " + compatible.toString() + " removed by the filter because already " + ((TestCondition) compatible).getStatusDescription());
				continue;
			}
			//fireTestConditionStarted(compatible);
			// the testsuite
			((TestCondition) compatible).setRunning();
			// run the test case: the result must be empty or unity test suite
			// this call searches also for coverages
			TS genresult = forTestCondition(compatible);
			// either covered or infeasible
			assert  (((TestCondition) compatible).getStatus() ==  TestConditionState.UNFEASIBLE || ((TestCondition) compatible).getStatus() ==  TestConditionState.AssertViolated);
			log.debug("removing from queque ["+processor.getClass() +"] tp " + compatible.getName() + " with status " + ((TestCondition) compatible).getStatusDescription());
			processor.remove(); // so remove it
			// increase the final test suite
			assert genresult.size() <= 1;
			throw new RuntimeException("ERRORE");
			// ho commentato questa riha perch+ mi da errore 26 luglio 26
			//result.addAllTest(genresult);
			// flag the run tcs as completed and filter again remaining
			// candidates
			//fireTestConditionCompleted(compatible);
		}
		// verify result coverage...
		return result;
	}

	/** find the tp ordering processor
	 * TODO subistute to allow the introduction of new tp with a factory or similar (as for collector)
	 * @param result
	 * @param candidates 
	 * @param processor
	 * @return
	 */
	TPProcessor<TP> getTPProcessor(TS result, List<TP> candidates) {
		OrderKind ordering = TP_ORDERING.getValueAsEnum();
		switch(ordering){
			case NOVELTY:			
				return new PreferNovelty<S,TS,TS,TP>(result,specification,candidates);
			case NOVELTY_COL:
				return new PreferNoveltyCollect<S,TS,TS,TP>(result,specification,candidates);
			case RANDOM:
				return TPProcessorFactory.chooseRndFactory.getTPPRocessor(specification, result,candidates);				
			case AS_GENERATED:
				return new GenerationOrder<TP>(candidates);
			case ANTIDIAGONAL:
				// do nothing, take as generated
				return new GenerationOrder<TP>(candidates);
			default:
				throw new RuntimeException("unknown ordering");
		}
	}
	
	//verify result coverage...
	//	if (validate (result, cv.allTestConditions()))  {System.out.print("SUITE OK!!!"); return result;}
		//log.error("LA SUITE NON E' CORRETTA! :\n"+ result.toString());		
	//	return null;
	//}

	private boolean validate(AsmTestSuite suite, List<AsmTestCondition> candidates) {
		for (AsmTestCondition tc: candidates) {
			if (tc.getStatus()==TestConditionState.UNFEASIBLE) continue;
			//if (tc.allCoveredBy().isEmpty()) return false;
			boolean found=false;
			for (AsmTestSequence s:tc.allCoveredBy()) 
				if (s.tpCovered().contains(tc)) { found=true; break;}
			if (!found) return false;			
			
		}
		return true;
	}

	/** find the right collector
	 * 
	 * @param coverageClass
	 * @param allVariables
	 * @param axioms
	 * @param processor
	 * @return
	 */
	public TPProcessor<TP> getCollector(Class<? extends AsmCoverage> coverageClass, 
			Iterable<? extends tgtlib.definitions.expression.type.Variable> allVariables, Collection<Axiom> axioms, 
			TPProcessor<TP> processor){
		TPCollectorFactory factory = TPCompatibleCollector.collectorRegistrator.getCollector(coverageClass);
		assert factory != null : "class not registered " + coverageClass;
		return factory.build(allVariables,axioms,generator, processor);
	}

	@Override
	protected Pair<MCAnalysisResult, T> getTestForTC(TP tc) {
//	protected Pair<MCAnalysisResult, TestSequence<? extends TC>> getTestForTC(TC tc) {
		TestSequence ts;
		MCAnalysisResult anRes;
		// it may contain its witness
		if (tc instanceof CollectedTestCondition
				// already set the witness
				&& (TPCompatibleCollector.getComputeWitness() == ComputeWitness.atTheEnd ||
						TPCompatibleCollector.getComputeWitness() == ComputeWitness.afterEveryTp )) {
			log.debug("taking the witness from the collected test predicate");
			// get the witness
			CollectedTestCondition collected = (CollectedTestCondition) tc;
			ts = collected.getWitness();
			assert ts != null;
			anRes = MCAnalysisResult.found();
			try{
				ts.setGeneratedFor(tc);
			} catch(Throwable t){				
				System.err.println(" ***** ");
				System.err.println(" test generated for: " + ts.getGeneratedFor().getName());
				System.err.println(" collection " + collected.getNames());
				System.err.println(" is in collection ??? " + collected.contains((TestCondition) ts.getGeneratedFor()));
				t.printStackTrace();
				System.exit(0);
			}
			return new Pair<MCAnalysisResult, T>(anRes, (T)ts);
		} else{
			try {
				return generator.executeAndAnalyze(tc);
			} catch (ModelCheckerExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new Pair<MCAnalysisResult, T>(MCAnalysisResult.notFound(e.getMessage()), null);
			}
		}		
	}
	
	/** get the list of candidates for the coverage
	 * 
	 * @param cv
	 * @return
	 */
	protected List<TP> getCandidates(C cv) {
		// holds sorted test conditions
		List<TP> candidates = new ArrayList<TP>(); 
		// remove unselected or already covered
		for (TP tc: cv.allTPs()) {
			if (tcFilter.accept(tc)) {
				candidates.add(tc);
			}
		}
		return candidates;
	}

	@Override
	protected void addTestsForCoverage(C cov, TS testSuite) {
		TS intermediate = forAsmCoverage(cov);
		throw new RuntimeException("ERRORE");
		// ho commentato questa riha perche mi da errore 26 luglio 26
		//testSuite.addAllTest(intermediate);
	}

	@Override
	protected List<TP> computeCoverage(TestSequence ts) {
		if (ts.numberOfStates() != 1)
			throw new RuntimeException();
		log.debug("using the simple evaluator");
		ExpressionEvaluator eval = new ExpressionEvaluator(ts.getState(0), specification.getVariables());		
		Vector<TP> tgCovered = new Vector<TP>();
		for (TP current : coverage.allTPs()) {
			if (current.getCondition().accept(eval)) {
				tgCovered.add(current);
			}
		}
		return tgCovered;
	}
}