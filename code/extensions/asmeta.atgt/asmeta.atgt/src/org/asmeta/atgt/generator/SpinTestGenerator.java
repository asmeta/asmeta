package org.asmeta.atgt.generator;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestCondition;
import atgt.generator.SpinTSuiteGenForTC;
import atgt.preferences.ATGToolPreferences;
import atgt.project.AsmProject;
import tgtlib.specification.ParseException;

/**
 * 
 * generate a JUnit test case for a given Java SUT. It generates the asmeta tests using SPIN
 *
 */
public class SpinTestGenerator extends AsmTestGenerator {

	public SpinTestGenerator(String asmfile, boolean coverageTp) throws ParseException {
		super(asmfile, coverageTp);
	}

	protected static final boolean SEARCH_COMMON_COVERAGE = false;
	
	/**
	 * Generate testfor asm.
	 *
	 * @param asmfile
	 *            the asmfile
	 * @param maxNTP
	 *            the max number of test predicates
	 * @param generator 
	 * @return the asm test suite
	 * @throws Exception 
	 */
	@Override
	protected AsmTestSuite generateTestforASM(AsmCoverage ct) throws Exception {
		// specific to SPIN
		AsmProject pro = new AsmProject(getSpec(), ct);
		// ATGToolPreferences.BFS.setChecked(true);
		ATGToolPreferences.minusILowerCase.setChecked(false);
		SpinTSuiteGenForTC spt = SpinTSuiteGenForTC.createFlatSpinTSuiteGenForTC(pro);
		spt.setSearchCommonCoverage(SEARCH_COMMON_COVERAGE);
		AsmTestSuite ts = ct.accept(spt);
		int i = 1;
		for (TestCondition tc : ct.allTPs()) {
			//System.out.print(i++ + " " + tc.getName() + " ");
			//System.out.println(tc.getStatusDescription());
		}
		return ts;
	}
}
