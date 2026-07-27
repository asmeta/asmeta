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
package atgt.combinatorial;

import java.util.Collection;

import org.apache.log4j.Logger;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.Coverage;
import atgt.coverage.DoQuequedUCovered;
import atgt.coverage.SkipCoveredTCFilter;
import atgt.coverage.TestCondition;
import atgt.coverage.tpstatus.TestConditionState;
//import atgt.generator.testsuite.SalTSuiteGenCollect;
import atgt.generator.testsuite.TestSuiteGeneratorCov;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.reduction.TestSuiteRed;


/**
 * The Class Util.
 */
public class Util {

	/**
	 * Instantiates a new util.
	 */
	private Util() {
	}

	/** The Constant logger. */
	static final Logger logger = Logger.getLogger(Util.class);

	
	
	
	/**
	 * build a specification 2^n: n vars boolean.
	 * 
	 * @param n
	 *            the n
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification two_powerN(int n) {
		ASMSpecification asm = new ASMSpecification();
		IdExpressionCreator iecc = new IdExpressionCreator();
		asm.name = "two_power_" + String.valueOf(n);
		// se metto 80 da errore si Stack overflow
		for (int i = 0; i < n; i++) {
			IdExpression id = iecc.createIdExpression("v" + i, BoolType.BOOLTYPE);
			Variable v = new Variable(id, null);
			v.setMonitored();
			asm.addVariable(v);
		}
		return asm;
	}

	/**
	 * Single spec unit test file. Search for common coverage (to allow
	 * reduction)
	 * 
	 * @param spec
	 *            the specification for which pairwise has to be tested
	 * @param comments
	 *            TODO
	 * @param skipAlreadyCovered
	 *            compute coverage and skip already covered. if false reduction
	 *            wont't work
	 * 
	 * @return the asm test suite
	 */
	static AsmProject generateNwiseTestSuite(ASMSpecification spec,
			String comments, boolean skipAlreadyCovered,int n) {

//		return generateNwiseTestSuite(spec, comments, skipAlreadyCovered,
//				new TestGenMethod() {
//					@Override
//					public SalTSuiteGenCollect getTGM(AsmProject asmPro) {
//						return new SalTSuiteGenCollect(asmPro);
//					}
//				},n);
		return null;
	}

	static public abstract class TestGenMethod {

		abstract public TestSuiteGeneratorCov getTGM(AsmProject asmPro);

	}

	
	
	/**
	 * Generate the nwise test suite. Return the project with the right test suite
	 * 
	 * @param spec the specification
	 * @param comments the comments to print
	 * @param skipAlreadyCovered the skip already covered
	 * @param tgm the test generator method
	 * @param nwise the strebght (2 = pairwise ...)
	 * 
	 * @return the asm test suite
	 */
	public static AsmProject generateNwiseTestSuite(ASMSpecification spec,
			String comments, boolean skipAlreadyCovered, TestGenMethod tgm, int nwise) {
		logger.info("starting generation for " + spec.getName() + " " + nwise + "-wise");

		// // start-up
		//long startTime = System.currentTimeMillis();
		//logger.info("**  Starting test for: " + spec.name + " " + comments);
		//System.out.print('.');

		Coverage ct;
		if (nwise == 2)
			// pairwise coverage build-up
			ct = (Coverage) AsmCombCovBuilder.makePairwiseCovBuilder().getTPTree(spec);
		else 
			ct = AsmCombCovBuilder.createNWiseCovBuilder(nwise).getTPTree(spec); 
		for (TestCondition tc : ct.allTPs())
			tc.setToVerify(true); // select all
		logger.info(nwise + "wise coverage has " + ct.getNumberofTPs() + "tps"); 
//				+ " test pairs: " + ct.allTestConditions().toString());

		// // test suite build-up and reduction
		AsmProject asmPro = new AsmProject(spec, ct);
		TestSuiteGeneratorCov stgen = tgm.getTGM(asmPro);
		stgen.setSearchCommonCoverage(true); /****MODIFICATO!!********/
		if (skipAlreadyCovered)
			stgen.setTestConditionFilter(SkipCoveredTCFilter.SkipCoveredTCFilter);
		else
			stgen.setTestConditionFilter(DoQuequedUCovered.DoQuequedUCovered);
				//	.setTestConditionFilter(DefaultTestConditionFilter.DefaultTestConditionFilter);
		// start the generation
		stgen.generateTestsWait();
		AsmTestSuite result = (AsmTestSuite) stgen.getRunResult();
		logger.info("generation finished with "+ result.getTests().size() + " tests");
		//assertFalse(result.isEmpty());
		if (logger.isDebugEnabled()) {
			logger.debug("checking all test conditions");
			int assertV = 0, covered = 0;
			int infeasible = 0;
			for (TestCondition tc : ct.allTPs()) {
				if (tc.getStatus() == TestConditionState.AssertViolated){
					assertV++;
				} else if (tc.getStatus() == TestConditionState.Covered){
					covered++;
				} else if (tc.getStatus() == TestConditionState.UNFEASIBLE){
					infeasible ++;
					logger.info(tc.getName() + " " + tc.getCondition()+ " is infeasible" );
				} else{
					logger.error("tc " + tc.getName() + " in status " + tc.getStatus());
				}
			}
			logger.info("violated: " + assertV + " covered: " + covered + " infeasible: " + infeasible);
		}

		logger.debug("test suite ["+result.size()+"] is:\n " + result.toString());

		// // end-up reporting
		// /int reduced = TestSuiteRed.analyzeAsmTestSuite(result);
		//float time = ((float) ((System.currentTimeMillis() - startTime) / 100)) / 10;
		//logger.info("test suite size: " + result.size()	+ " (not yet reduced) found in " + time + " seconds.");
		asmPro.addAsmTestSuite(result);
		return asmPro;
	}

	/**
	 * Find best test suite.
	 * 
	 * @param toTest
	 *            specifica da testare
	 * @param times
	 *            numero di volte
	 * @param skipCovered
	 *            the skip covered
	 * 
	 * @return la migliore test suite (come numero di test dopo la riduzione)
	 */
	static public AsmTestSuite findBestTestSuite(ASMSpecification toTest, int times, boolean skipCovered, int nwise) {
		// // start-up
		// logger.info("***************** Repeated ("+times+" times) Testing
		// for: "+toTest.name);
		long startTime = System.currentTimeMillis();
		float time = startTime;
		// the best suite found
		AsmTestSuite result, best=null; 
		Collection<AsmTestSequence> reduced=null, best_reduced = null;
		
		for (int count = 0; count < times; count++) {
			// run again the MC
			result = Util.generateNwiseTestSuite(toTest," try " + (count + 1), skipCovered,nwise).getAsmTestSuite();
			// if first time, take this as best
			if (best == null) best = result;
			else if  (result.size()<best.size()) best = result;
			// reduce the suite
			reduced =  (new TestSuiteRed<AsmTestCondition, AsmTestSequence>()).analyzeAsmTestSuite(result);
			if (best_reduced==null) best_reduced=reduced;
			else if (reduced.size() < best_reduced.size()) best_reduced = reduced;
			// has the same data?
			//boolean same_suite = !((result.size() < best.size() && reduced.size() > best_reduced.size()) || (result.size() > best.size() && reduced.size() < best_reduced.size()));
			// debug
			logger.debug("["+reduced.size()+"]");
			// if this is better than best
		}
		// reporting
		// get the total time
		time = ((float) ((System.currentTimeMillis() - startTime) / 100)) / 10;
		logger.info("[tries: " + times + "] \t[best size: " + best.size()
				+ "] \t[best reduced: " + best_reduced.size() + "] \t[avg. time: " + time/times
				+ "s] \t[spec:" + toTest.name + "].");
		
		logger.debug("test suite is: \n"+ reduced.toString());
		return best;
	}
	
	
}
