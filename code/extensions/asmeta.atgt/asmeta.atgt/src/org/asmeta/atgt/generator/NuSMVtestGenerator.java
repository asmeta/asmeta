package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.eval.AsmCoverageEvaluator;
import atgt.coverage.evalc.NavigableAsmInputs;
import atgt.coverage.tpstatus.TestConditionState;

/**
 * 
 * generate a JUnit test case for a given Java SUT
 * using NuSMV
 *
 */
public class NuSMVtestGenerator extends AsmTestGenerator {

	protected String asmFile;
	
	public static Logger logger = Logger.getLogger(NuSMVtestGenerator.class);

	public NuSMVtestGenerator(String asmFilePath) {
		this(asmFilePath, true, CriteriaEnum.getCoverageCriteria(AsmTestGenerator.DEFAULT_CRITERIA));
	}

	public NuSMVtestGenerator(String asmFilePath, boolean coverageTp, Collection<AsmCoverageBuilder> criteria) {
		super(asmFilePath, coverageTp, criteria);
		//Logger.getLogger(AsmetaLLoader.class).setLevel(Level.OFF);
		//Logger.getLogger(CoverageEvaluatorC.class).setLevel(Level.OFF);
		this.asmFile = asmFilePath;
	}

	@Override
	protected AsmTestSuite generateTestforASM(AsmCoverage ct) throws Exception {
		assert new File(asmFile).exists();
		// specific to NUSMV
		AsmTestSuite asmTestSuite = new AsmTestSuite();
		logger.info("generating tests for tps (" + ct.getNumberofTPs()+")");
		for (AsmTestCondition tp : ct.allTPs()) {
			System.out.println(tp.getName() + " -" + tp.getCondition());
			if (tp.getStatus() == TestConditionState.Queued) {
				TestGenerationWithNuSMV n = new TestGenerationWithNuSMV(asmFile, tp.getCondition());
				Counterexample test = n.checkTpWithModelChecker();
				if (test == null) {
					continue;
				}
				AsmTestSequence asmTest = ConverterCounterExample.convert(test, getSpec(), tp);
				// close the test
				asmTest.close();
				// connect to thsi to
				tp.bindTestSeqTestPred(asmTest);
				asmTestSuite.addTest(asmTest);
				//System.out.println(asmTest);
				// To avoid errors while translating in C to compute coverage: 
				// TODO to fix ATGT and build a new JAR, or reference ATGT projects.
				if(coverageTp) {
					// avoid teh use of C code
					//AsmCoverageEvaluatorC coverageEval = new AsmCoverageEvaluatorC(getSpec(), ct);
					AsmCoverageEvaluator coverageEval = new AsmCoverageEvaluator(ct);
					NavigableAsmInputs inputs = new NavigableAsmInputs(asmTest, getSpec());
					Vector<AsmTestCondition> covered = coverageEval.computeCoverage(inputs);
					logger.info("binding the tp covered (" + covered.size() + ")");
					for (AsmTestCondition tc : covered) {
						assert tc.getStatus() == TestConditionState.Covered; 
						tc.bindTestSeqTestPred(asmTest);
					}
				}
				/*
				 * for(AsmTestCondition tc: ct.allTPs()){
				 * System.out.print(tc.getUniqueID()+ " ");
				 * System.out.println(tc.getStatusDescription()); }
				 */
			}
		}
		System.out.println("# tps " + ct.getNumberofTPs());
		System.out.println("# generated tests " + asmTestSuite.size());
		return asmTestSuite;
	}
}