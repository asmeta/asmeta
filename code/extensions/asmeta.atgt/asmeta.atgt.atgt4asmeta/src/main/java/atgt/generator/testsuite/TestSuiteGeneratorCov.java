package atgt.generator.testsuite;

import java.util.List;

import org.apache.log4j.Logger;

import atgt.coverage.DefaultTestConditionFilter;
import atgt.coverage.TestCondition;
import atgt.coverage.tpstatus.TestConditionState;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.TestSuiteFactory;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.TestConditionFilter;
import tgtlib.generator.TestSuiteGenerator;
import tgtlib.specification.Specification;
import tgtlib.util.Pair;

/**
 * test suite generator with computation of coverage + tcfilter
 * 
 * TO BE GENERIC (no ASM)
 *
 * @param <S> the generic type TO BE DELETED????
 * @param <T> the generic type
 * @param <C> the generic type
 * @author garganti
 */
public abstract class TestSuiteGeneratorCov<
S extends Specification,  
T extends TestSequence<? extends TP>, 
TP extends TestPredicate<? extends T,?>, 
TS extends TestSuite<?, ?>, 
C extends CoverageTree<?>
>
		extends TestSuiteGenerator<S, TS, C> {

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(TestSuiteGeneratorCov.class);

	
	/** the filter to be used to decide if a test predicate must be considered or not*/
	protected TestConditionFilter tcFilter;	


	protected TestSuiteGeneratorCov(S spec, C cov, TestSuiteFactory tsfactory) {
		super(spec, cov,tsfactory);
		this.tcFilter = DefaultTestConditionFilter.DefaultTestConditionFilter;
	}

	/** search other coverages?. */
	protected boolean searchOtherCovs;

	/**
	 * set if is required to have common coverages *.
	 * 
	 * @param searchCommonCoverage
	 *            the search common coverage
	 */
	public void setSearchCommonCoverage(boolean searchCommonCoverage) {
		this.searchOtherCovs = searchCommonCoverage;
	}

	/**
	 * return the AsmTestSuite given exactly by the test sequence generate for
	 * the TC (which could be very complex). it computes also the coverage if
	 * requested
	 * 
	 * @param tc
	 *            the tc
	 * 
	 * @return the asm test suite : never null, if its is not found return an
	 *         empty testsuite
	 */
	final public TS forTestCondition(TP tc) {
		//
		log.debug("starting test suite generation for " + tc.getName());
		// fireTestConditionStarted(tc);
		// test condition becomes running
		((TestCondition)tc).setRunning();
		// get the tests
//		Pair<MCAnalysisResult, TestSequence<? extends TC>> res = getTestForTC((TC)tc);
		Pair<MCAnalysisResult, T> res = getTestForTC(tc);
		TestSequence<? extends TP> ts = res.getSecond();
		MCAnalysisResult anRes = res.getFirst();
		// test condition is infeasible
		if (anRes.isUnfeasible()) {
			assert ts == null || ts.numberOfStates() == 0;
			log.info("infeasible test predicate found " + tc.getName());
			tc.markInfeasible();
			((TestCondition)tc).setAssertViolated(false);
			return tsfactory.buildEmptyTestSuite();
		}
		// test should be found unless an error occurs
		if (!anRes.isTestFound()) {
			//assert ts == null;
			assert ts == null || ts.numberOfStates() == 0;
			((TestCondition)tc).setAssertViolated(false);
			log.error("model checker did not found model/cex :"
					+ anRes.getMessage());
			return tsfactory.buildEmptyTestSuite();
		}
		assert anRes.isTestFound();
		log.debug("test found for  tc " + tc.getName() + " ts:" + ts.toString());
		assert ts != null;
		((TestCondition)tc).setAssertViolated(true);
		// search for common coverages?
		if (searchOtherCovs) {
			searchOtherCoverages(((TestCondition)tc), (T) ts);
		}
		// in any case bind test and test predicate
		((TestCondition)tc).bindTestSeqTestPred(ts);
		TestSuite result = tsfactory.buildEmptyTestSuite();
		result.addTest(ts);
		return (TS) result;
	}

	/**
	 * Search other coverages. see also method markCoverage
	 * 
	 * @param tc
	 *            the tc the test condition which had been violated
	 * @param ts
	 *            the ts for which to compute the coverage
	 */
	private void searchOtherCoverages(TestCondition tc, T ts) {
		assert tc.getStatus() == TestConditionState.AssertViolated;
		assert ts.getGeneratedFor() == tc;
		log.debug("searching other coverages in "
				+ (coverage == null ? " null coverage ??? " : coverage
						.getName()));
		// get the tests condition covered by ts
		List<TP> tgCovered = computeCoverage(ts);
		log.debug("covered " + tgCovered);
		// mark as covered
		for (TP tcCov : tgCovered) {
			// FIXME up to now only test conditions - in the future all TC
			TestCondition tg = (TestCondition) tcCov;
			// mark as covered by tr
			if (tg != tc) {
				// every tg must be queued, or already covered or assertion
				// violated (in case it is covered again)
				assert (tg.getStatus() == TestConditionState.Queued)
						|| (tg.getStatus() == TestConditionState.Covered)
						|| (tg.getStatus() == TestConditionState.AssertViolated)
						|| (tg.getStatus() == TestConditionState.TODO) : 
							tg.getUniqueID() + " " + tg.getStatusDescription() + " " + tg.getCondition().toString();
				tg.bindTestSeqTestPred(ts);
				assert (tg.getStatus() == TestConditionState.Covered)
						|| (tg.getStatus() == TestConditionState.AssertViolated) : tg
						.getName() + " : " + tg.getStatusDescription();
			}
		}
		// check that the selected test predicate is actually covered. If it is
		// include in the coverage
		// but it is not in the tg covered, then there is a error
		if (contains(coverage,(TP) tc) && !tgCovered.contains(tc))
			throw new RuntimeException(
					"test generated for tc "
							+ tc.getName()
							+ " which is in coverage tree but is not covered; tc status: "
							+ tc.getStatusDescription() + "\ntest predicates "
							+ tc.getCondition().toString() + "\ntest sequence "
							+ ts.toString());

	}

	private boolean contains(C coverage, TP tc) {
		// TODO Auto-generated method stub
		// TODOOOOO
		return false;
	}

	/**
	 * Sets the test condition filter.
	 * 
	 * @param _tcFilter
	 *            the new test condition filter
	 */
	public void setTestConditionFilter(TestConditionFilter _tcFilter) {
		this.tcFilter = _tcFilter;
	}

	/** try to build the test for the single TP - which may be very complex */
//	protected abstract Pair<MCAnalysisResult, TestSequence<? extends TC>> getTestForTC(TC tc);
	protected abstract Pair<MCAnalysisResult, T> getTestForTC(TP tc);
	

	/** compute the coverage
	 * 
	 * @param ts
	 * @return
	 */
	protected abstract List<TP> computeCoverage(T ts);

}
