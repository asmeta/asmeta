package atgt.combinatorial.parallel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import atgt.combinatorial.CollectedNWiseTC;
import atgt.combinatorial.CollectedNWiseTC.CHECK_RESULT;
import atgt.combinatorial.CombinatorialTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.generator.testsuite.TestGeneratorCollectTP;
import extgt.coverage.combinatorial.EqTestCondition;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.TestSuiteFactory;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.project.Project;
import tgtlib.specification.Specification;

/**
 * Collect in parallel all the tps. the tps are NEqtest conditions?
 * 
 * @author garganti
 *
 */
public class TSGenInMinOverlap<
S extends Specification,   
T extends TestSequence<? extends TC>, 
TC extends TestPredicate<? extends T,?>,
TS extends TestSuite<TC,T>, 
C extends CoverageTree<? extends TC>
> 
extends TestGeneratorCollectTP<S,T,TC,TS,C> {

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(TSGenInMinOverlap.class);

	public TSGenInMinOverlap(Project<S, TC, T, C> _project,
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
		while(!candidates.isEmpty()){
			int minOverlap = Integer.MAX_VALUE;
			int minSize = Integer.MAX_VALUE;
			CombinatorialTestCondition tcToadd = null;
			CollectedNWiseTC collectionToAdd = null;
			// for every test predicates
			for(Iterator<TC> i = candidates.iterator(); i.hasNext();){
				// select the best combination tc and collection
				// best: fewer overlap and in case of parity, fewer tests in the collection
				// if tc is inconsistent with all the collections, add a new collection
				CombinatorialTestCondition ctc = (CombinatorialTestCondition) i.next();				
				for(CollectedNWiseTC coll: collections){
					// compare with the collection
					CHECK_RESULT result = coll.checkConsistencyByValue(ctc);
					assert result != CHECK_RESULT.IMPLIED;
					if (result == CHECK_RESULT.INCONSISTENT) continue;
					assert (result == CHECK_RESULT.NOT_IMPLIED);
					// compute overlap
					int overlap = computeOverlap(coll,(EqTestCondition) ctc);
					if (overlap < minOverlap ||  (overlap <= minOverlap && coll.size() < minSize)){
						tcToadd = ctc;
						collectionToAdd = coll;
						minOverlap = overlap;
						minSize = (int) coll.size();
					}					
				}
				// no min found, add new collection
				if (tcToadd ==null){
					// not addable to any collection add a new collections
					log.info(ctc.getName() + " inconsistent with all the collections, adding new collection, tc:" + ctc.getCondition());
					CollectedNWiseTC cn = new CollectedNWiseTC();
					cn.addTestCondition(ctc);
					collections.add(cn);
					// remove
					i.remove();
				}				
			}
			if (candidates.isEmpty()) break;
			// now add to the collection, if the min is found
			assert tcToadd !=null;
			collectionToAdd.addTestCondition(tcToadd);
			log.debug(tcToadd.getName() +  " added by enlarging the collection. overlapping " +  minOverlap + " size in the collection " + collectionToAdd.size());
			// add implied too 
			checkImplied(candidates,collectionToAdd);
			// remove from candidates
			candidates.remove(tcToadd);
		}
		// get the test from the collections.
		TS testSuite = tsfactory.buildEmptyTestSuite();
		for (CollectedNWiseTC cntc : collections) {
			// build the test from the collection?
			// TODO
			//AsmTestSequence t = new AsmTestSequence(cntc){};
			AsmTestSequence t = new AsmTestSequence(null){};
			// TODO fill the test 
			// t.setGeneratedFor(cntc);
			// add also the links between TPS and test
			cntc.bindTestSeqTestPred(t);
			// add to the test sequence
			throw new RuntimeException("ERRORE");
			// ho commentato questa riga successiva perch+ mi da errore 26 luglio 26
			// testSuite.addTest(t);
			//
		}
		return testSuite;
	}

	// returns the number of variable overlapping
	// TODO da migliorare
	private int computeOverlap(CollectedNWiseTC coll, EqTestCondition tc) {
		int overlap = 0;
		// assert coll is not implied with tc
		for(int i = 0; i < tc.size(); i++){
			TypedInitExpression var = tc.getVar(i);
			if (coll.isSettingVar(var)) overlap++;
			// value is equal
		}
		return overlap;
	}

	// che3ck and if implied, add and remove from candidates
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
