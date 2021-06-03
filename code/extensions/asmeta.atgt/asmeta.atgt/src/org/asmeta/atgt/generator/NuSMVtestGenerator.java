package org.asmeta.atgt.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.asmeta.atgt.testoptimizer.UnchangedRemover;
import org.asmeta.atgt.testoptimizer.UnecessaryChangesRemover;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import atgt.coverage.AsmCoverage;
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

	public static boolean removeUnChangedControlles = true;

	public static boolean removeUnaskedChanges = true;

	protected String asmFile;

	private AsmCollection asmCollection;
	public static Logger logger = Logger.getLogger(NuSMVtestGenerator.class);

	private String tp="";
	private String genTests="";
	
	public NuSMVtestGenerator(String asmFilePath) throws Exception {
		this(asmFilePath, true);
	}

	public NuSMVtestGenerator(String asmFilePath, boolean coverageTp) throws Exception {
		super(asmFilePath, coverageTp);
		//Logger.getLogger(AsmetaLLoader.class).setLevel(Level.OFF);
		//Logger.getLogger(CoverageEvaluatorC.class).setLevel(Level.OFF);
		this.asmFile = asmFilePath;
		asmCollection = ASMParser.setUpReadAsm(new File(asmFilePath));
	}

	@Override
	protected AsmTestSuite generateTestforASM(AsmCoverage ct) throws Exception{
		return generateTestforASM(ct, null);
	}
	
	
	protected AsmTestSuite generateTestforASM(AsmCoverage ct, FileWriter fw) throws Exception {
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
					String msg="test generation failed - show never happen";
					logger.error(msg);
					if (fw!=null) println(msg,fw);
					continue;
				}
				if (test == Counterexample.EMPTY) {
					String msg= "test generation failed - model checker error " + tp.getName() + " -" + tp.getCondition();
					if (fw!=null) println(msg,fw);
					logger.warn(msg);
				}
				if (test == Counterexample.INFEASIBLE) {
					String msg = "test generation impossible since it is infeasible " + tp.getName() + " -" + tp.getCondition();
					if (fw!=null) println(msg,fw);
					logger.warn(msg);
				}
				// if it is impossible and empty writes the test which is empty 
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
				// clean up the test
				// removing controlled repetitions
				if (removeUnChangedControlles) UnchangedRemover.conRemover.optimize(asmTest);
				// removing useless monitored
				if (removeUnaskedChanges) {
					UnecessaryChangesRemover eucr  = new UnecessaryChangesRemover(asmCollection);
					eucr.optimize(asmTest);				
				}
				//
				/*
				 * for(AsmTestCondition tc: ct.allTPs()){
				 * System.out.print(tc.getUniqueID()+ " ");
				 * System.out.println(tc.getStatusDescription()); }
				 */
			}
		}
		tp="# tps " + ct.getNumberofTPs();
		//System.out.println(tp);
		genTests="# generated tests " + asmTestSuite.size();
		//System.out.println(genTests);
		return asmTestSuite;
	}
	
	public String getTp() {
		return tp;
	}
	public String getGenTests() {
		return genTests;
	}
	
	private void println(String s, FileWriter fw) throws IOException {
		fw.append(s+ "\n");
		fw.flush();
		System.out.println(s);
	}
}