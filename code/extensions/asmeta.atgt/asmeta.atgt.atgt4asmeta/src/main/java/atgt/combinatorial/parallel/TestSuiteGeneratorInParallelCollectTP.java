package atgt.combinatorial.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import atgt.combinatorial.CollectedNWiseTC;
import atgt.combinatorial.CollectedNWiseTC.CHECK_RESULT;
import atgt.combinatorial.CombinatorialTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.generator.testsuite.TestGeneratorCollectTP;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.TestSuiteFactory;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.project.Project;
import tgtlib.specification.Specification;

/**
 * Collect in parallel all the tps. the tps are NEqtest conditions?
 * 
 * @author garganti
 *
 */
public class TestSuiteGeneratorInParallelCollectTP
<S extends Specification,   
T extends TestSequence<? extends TC>, 
TC extends TestPredicate<? extends T,?>,
TS extends TestSuite<TC,T>, 
C extends CoverageTree<? extends TC>> extends
		TestGeneratorCollectTP<S,T,TC,TS,C> {

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(TestSuiteGeneratorInParallelCollectTP.class);

	public TestSuiteGeneratorInParallelCollectTP(Project<S, TC, T, C> _project,
			TestSequenceGenerator generator, TestSuiteFactory testsuitefactory) {
		super(_project, generator, testsuitefactory);
		assert _project.specification.getAxiom().size() == 0;
	}

	@Override
	public TS forAsmCoverage(C cv){
		log.info("generating tests for " + cv.getName());
		// the collections
		List<CollectedNWiseTC> collections = new ArrayList<CollectedNWiseTC>();
		//
		List<TC> candidates = new ArrayList<TC>(getCandidates(cv));
		// Collect
		Collections.shuffle(candidates);
		log.info("shuffling the candidates");
		for (;;) {
			if (candidates.isEmpty()) break;
			// the first not implied
			CombinatorialTestCondition tc = (CombinatorialTestCondition) candidates.get(0);
			candidates.remove(0);
			log.debug("generating test for " + tc.getName() );
			// still to be added
			// order the collections
			Collections.sort(collections, new Comparator<CollectedNWiseTC>() {
				@Override
				public int compare(CollectedNWiseTC o1, CollectedNWiseTC o2) {
					return (int) (o1.size() - o2.size());
//					return (int) (o2.size() - o1.size());
//					return (int) (o2.numVariablesFixed() - o1.numVariablesFixed());
				}
			});
			if (log.isDebugEnabled()){
				// print the size of the first 10
				for(int j = 0; j <= 10 && j < collections.size(); j++) System.out.print(collections.get(j).size() + " ");
				System.out.println();
			}
			boolean added = false;
			for (CollectedNWiseTC c : collections) {
				CHECK_RESULT result = c.checkConsistencyByValue(tc);
				if (result == CHECK_RESULT.INCONSISTENT)
					continue;
				assert (result == CHECK_RESULT.NOT_IMPLIED) : result + " in " + c;
				// TODO check the constraints
				// assuming non constraints : it is ok to add
				c.addTestCondition(tc);
				added  = true;
				log.debug("added by enlarging the collection");
				// add implicated too
				checkImplied(candidates,c);
				break;
			}
			// if already added, then continue with the next one
			if (added)
				continue;
			log.info(tc.getName() + " inconsistent with previous, adding new collection [" +collections.size() + "] tc:" + tc.getCondition());
			// not added. add a ne collections
			CollectedNWiseTC cn = new CollectedNWiseTC();
			cn.addTestCondition(tc);
			collections.add(cn);
			checkImplied(candidates,cn);
		}

		// get the test from the collections.
		TS testSuite = tsfactory.buildEmptyTestSuite();
		for (CollectedNWiseTC cntc : collections) {
			// build the test from the collection?
			// TODO
			//AsmTestSequence t = new AsmTestSequence("p");
			AsmTestSequence t = new AsmTestSequence(null);
			// TODO fill the test 
			t.setGeneratedFor(cntc);
			// add also the links between TPS and test
			cntc.bindTestSeqTestPred(t);
			// ad dto tes test sequence
			throw new RuntimeException("ERRORE");
			// ho commentato questa riga successiva perch+ mi da errore 26 luglio 26
			//testSuite.addTest(t);
		}
		return testSuite;
	}

	private void checkImplied(List<TC> candidates, CollectedNWiseTC collection){
		// see which ones are implied:
		// get new iterators
		Iterator<TC> i = candidates.iterator();
		while (i.hasNext()) {
			CombinatorialTestCondition tc = (CombinatorialTestCondition) i.next();
			// if tc is implied in one of the collections, the add it to
			// it
			CHECK_RESULT result = collection.checkConsistencyByValue(tc);
			if (result == CHECK_RESULT.IMPLIED) {
				collection.addTestCondition(tc);
				log.debug( tc.getName() + " implied -- removed from candidates");
				//
				i.remove();
			}
		}
	}
	
}
