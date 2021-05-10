package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
import atgt.specification.location.Location;

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
		///
		TestGenerationWithNuSMV n = new TestGenerationWithNuSMV(asmFile); 
		// specific to NUSMV
		AsmTestSuite asmTestSuite = new AsmTestSuite();
		logger.info("generating tests for tps (" + ct.getNumberofTPs()+")");
		for (AsmTestCondition tp : ct.allTPs()) {
			logger.info("generation for TP " + tp.getName() + " -" + tp.getCondition() + " " + tp.getStatusDescription());
			if (tp.getStatus() == TestConditionState.Queued) {
				//
				Counterexample test = n.checkTpWithModelChecker(tp.getCondition());
				if (test == null) {
					logger.debug("test generation failed");
					continue;
				}
				AsmTestSequence asmTest = ConverterCounterExample.convert(test, getSpec(), tp);
				
				// close the test
				asmTest.close();
				// connect to thsi to
				tp.bindTestSeqTestPred(asmTest);
				asmTestSuite.addTest(asmTest);
				// To avoid errors while translating in C to compute coverage: 
				// TODO to fix ATGT and build a new JAR, or reference ATGT projects.
				// attenzione che questo modifica il test che è passato, meglio fare una copia
				if(coverageTp) {
					// avoid the use of C code
					//AsmCoverageEvaluatorC coverageEval = new AsmCoverageEvaluatorC(getSpec(), ct);
					//
					AsmCoverageEvaluator coverageEval = new AsmCoverageEvaluator(ct);
					NavigableAsmInputs inputs = new NavigableAsmInputs(asmTest, getSpec());
					Vector<AsmTestCondition> covered = coverageEval.computeCoverage(inputs);
					logger.info("binding the tp covered (" + covered.size() + ")");
					for (AsmTestCondition tc : covered) {
						assert tc.getStatus() == TestConditionState.Covered; 
						tc.bindTestSeqTestPred(asmTest);
					}
				}
				// TEMP non riesco a fare andare le generazione solo di quelli cambiati, la faccio qui // TEMP
				// FIXME
				// IF CLEANUP - PROBLEMS ONLY WHEN COMPUTIGN COVERAGE
				Map<Location,String> lastvalues = new HashMap<>();
				for(Map<Location, String> state: asmTest.allInstructions()) {
					for (Iterator<Entry<Location, String>> iterator = state.entrySet().iterator(); iterator.hasNext();) {
						Entry<Location, String> assignemnt = iterator.next();
						Location loc = assignemnt.getKey();
						String val = assignemnt.getValue();
						if (lastvalues.get(loc) != null && lastvalues.get(loc).equals(val)) {
							//remove this entry loc and val
							iterator.remove();
						} else {
							lastvalues.put(loc, val);
						}
					}
				}
				//
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