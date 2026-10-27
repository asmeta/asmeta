package org.asmeta.atgt.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.base.AsmTestGeneratorBase;
import org.asmeta.atgt.generator.nusmv.ConverterCounterExample;
import org.asmeta.atgt.generator.nusmv.Counterexample;
import org.asmeta.atgt.generator.nusmv.TestGenerationWithNuSMV;
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
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;
import tgtlib.specification.Specification;

/** generate test generator based on NuSMV for a given ASM specification. It uses the class TestGenerationWithNuSMV to generate tests for each test purpose.
 * 
 * @author Angelo Gargantini
 *
 * @param <S>
 */
public abstract class NuSMVTestGenerator<S extends Specification> extends AsmTestGenerator<S>{
	
	private static Logger logger = Logger.getLogger(NuSMVTestGenerator.class);

	
	static boolean removeUnChangedControlled = true;
	static boolean removeUnaskedChanges = true;
	private String tp="";
	private String genTests="";

	
	private TestGenerationWithNuSMV generator;
	private AsmCollection asmCollection;


	NuSMVTestGenerator(String asmFilePath, boolean coverageTp, SpecificationReader<S> specReader) throws Exception {
		super(asmFilePath, coverageTp, specReader);
		assert new File(asmFilePath).exists();
		asmCollection = ASMParser.setUpReadAsm(new File(asmFilePath));
		generator = new TestGenerationWithNuSMV(asmFilePath);
	}
	
	@Override
	protected AsmTestSuite generateTestforASM() throws Exception{
		return generateTestforASM(null);
	}

	protected AsmTestSuite generateTestforASM(FileWriter fw) throws Exception {
		// specific to NUSMV
		AsmTestSuite asmTestSuite = new AsmTestSuite();
		logger.info("generating tests for tps (" + ct.getNumberofTPs()+")");
		for (AsmTestCondition tp : ct.allTPs()) {
			logger.info("generation for TP " + tp.getName() + " -" + tp.getCondition() + " " + tp.getStatusDescription());
			if (tp.getStatus() == TestConditionState.Queued) {
				//
				Counterexample test = generator.checkTpWithModelChecker(tp.getCondition());
				if (test == null) {
					String msg="test generation failed - show never happen";
					logger.error(msg);
					if (fw!=null) {
						println(msg,fw);
					}
					continue;
				}
				if (test == Counterexample.EMPTY) {
					String msg= "test generation failed - model checker error " + tp.getName() + " -" + tp.getCondition();
					if (fw!=null) {
						println(msg,fw);
					}
					logger.warn(msg);
				}
				if (test == Counterexample.INFEASIBLE) {
					String msg = "test generation impossible since it is infeasible " + tp.getName() + " -" + tp.getCondition();
					if (fw!=null) {
						println(msg,fw);
					}
					logger.warn(msg);
				}
				// if it is impossible and empty writes the test which is empty
				AsmTestSequence asmTest = ConverterCounterExample.convert(test, spec, tp);
				// close the test
				asmTest.close();
				// connect to thsi to
				tp.bindTestSeqTestPred(asmTest);
				asmTestSuite.addTest(asmTest);
				// To avoid errors while translating in C to compute coverage:
				// TODO to fix ATGT and build a new JAR, or reference ATGT projects.
				// attenzione che questo modifica il test che � passato, meglio fare una copia
				if(coverageTp) {
					if (spec instanceof ASMSpecification asmspec) {
						computeCoverage(ct, asmTest, asmspec);
					} else {
						System.err.println("Warning in NuSMVTestGenerator: the specification is not an ASMSpecification, coverage cannot be computed");
					}
				}
				// clean up the test
				// removing controlled repetitions
				if (removeUnChangedControlled) {
					UnchangedRemover.conRemover.optimize(asmTest);
				}
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
		//System.out.println(termTran);
		genTests="# generated tests " + asmTestSuite.size();
		//System.out.println(genTests);
		return asmTestSuite;
	}

	/**
	 * @param ct
	 * @param asmTest
	 * @param spec
	 */
	static void computeCoverage(AsmCoverage ct, AsmTestSequence asmTest, ASMSpecification spec) {
		// avoid the use of C code
		//AsmCoverageEvaluatorC coverageEval = new AsmCoverageEvaluatorC(getSpec(), ct);
		//
		AsmCoverageEvaluator coverageEval = new AsmCoverageEvaluator(ct);
		NavigableAsmInputs inputs = new NavigableAsmInputs(asmTest, spec);
		Vector<AsmTestCondition> covered = coverageEval.computeCoverage(inputs);
		logger.info("binding the tps covered (" + covered.size() + ")");
		for (AsmTestCondition tc : covered) {
			assert tc.getStatus() == TestConditionState.Covered;
			tc.bindTestSeqTestPred(asmTest);
		}
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

	public void setAllTpToBeCovered() {
		for (AsmTestCondition tp : ct.allTPs()) {
			tp.reset();
		}
	}

	
	
}
