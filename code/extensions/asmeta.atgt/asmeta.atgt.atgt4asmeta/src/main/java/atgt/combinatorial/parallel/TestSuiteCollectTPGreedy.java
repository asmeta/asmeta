package atgt.combinatorial.parallel;

import java.util.ArrayList;
import java.util.Collections;
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
 * Collect with the classical greedy algorithm the tps are NEqtest conditions?
 * 
 * @author garganti
 *
 */
public class TestSuiteCollectTPGreedy<S extends Specification, T extends TestSequence<? extends TC>, TC extends TestPredicate<? extends T,?>,TS extends TestSuite<TC,T>, C extends CoverageTree<? extends TC>> extends
		TestGeneratorCollectTP<S,T,TC,TS,C> {

	public TestSuiteCollectTPGreedy(Project<S, TC, T, C> _project,
			TestSequenceGenerator generator, TestSuiteFactory testsuitefactory) {
		super(_project, generator, testsuitefactory);
		assert _project.specification.getAxiom().size() == 0;
	}

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(TestSuiteCollectTPGreedy.class);

	@Override
	protected TS forAsmCoverage(C cv) {
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
			// build new collection
			CollectedNWiseTC cn = new CollectedNWiseTC();
			cn.setRunning();
			//
			Iterator<TC> i = candidates.iterator();
			// add the first one
			cn.addTestCondition((CombinatorialTestCondition) i.next());
			i.remove();
			// add others
			while(i.hasNext()) {
				CombinatorialTestCondition tc = (CombinatorialTestCondition) i.next();
				CHECK_RESULT result = cn.checkConsistencyByValue(tc);
				if (result == CHECK_RESULT.INCONSISTENT)
					continue;				
				assert (result == CHECK_RESULT.NOT_IMPLIED || result == CHECK_RESULT.IMPLIED);
				// TODO check the constraints
				// assuming no constraints : it is ok to add
				cn.addTestCondition(tc);
				i.remove();
			}
			collections.add(cn);
			log.info("collection " + cn.size() + " " + candidates.size());
		}
		// get the test from the collections.
		TS testSuite = tsfactory.buildEmptyTestSuite();
		for (CollectedNWiseTC cntc : collections) {
			// build the test from the collection?
			// TODO
			//AsmTestSequence t = new AsmTestSequence("testx"+cntc.getName());
			AsmTestSequence t = new AsmTestSequence(null);
			// TODO fill the test
			t.addState();
			// add other data
			t.setGeneratedFor(cntc);
			// add also the links between TPS and test
			cntc.setAssertViolated(true);
			cntc.bindTestSeqTestPred(t);
			// add the test sequence
			throw new RuntimeException("ERRORE");
			// ho commentato questa riga successiva perch+ mi da errore 26 luglio 26
			// testSuite.addTest(t);
			//
			//searchOtherCoverages(cntc,t);
		}
		return testSuite;
	}
}