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
package atgt.generator.testsuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.generator.AsmTestSeqGenerator;
import atgt.testseqexport.toXML;
import tgtlib.definitions.TestSequence;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.project.Project;
import tgtlib.specification.Specification;
import tgtlib.util.Pair;

/**
 * it generates a test for a Test Conditions generation functionalities are
 * delegated to the test generator. (is as type, so the subclasses can use
 * specialized version of AsmTesSeqGen.
 * 
 * TO BE UNITED WITH A TESTSEQ GENERATOR
 * 
 * @author Angelo Gargantini
 */
public abstract class TestSuiteGeneratorForTC<T extends TestSequenceGenerator<AsmTestCondition, ? extends TestSequence<?>, ?>>
		extends TestGeneratorForCoverages {

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(TestSuiteGeneratorForTC.class);

	/** the generator to be used for single TP */
	protected T generator;

	/**
	 * Instantiates a new test suite generator for tc.
	 * 
	 * @param pro
	 *            the pro
	 * @param gen
	 *            the gen
	 */
	public TestSuiteGeneratorForTC(Specification spec, AsmCoverage cov, T gen) {
		super(spec, cov);
		generator = gen;
	}

	public TestSuiteGeneratorForTC(Project<?, ?, ?, AsmCoverage> pro, T gen) {
		this(pro.specification, pro.getTestTree(), gen);
	}

	/**
	 * return the AsmTestSequence for a single test condition TC. null if not
	 * found 
	 * 
	 * @param tc
	 *            the tc
	 * 
	 * @return the test for tc
	 */
	@Override
	protected Pair<MCAnalysisResult, AsmTestSequence> getTestForTC(AsmTestCondition tc) {
		// reset the time
		long startTime = System.currentTimeMillis();
		try {
			// set the test suite generator in case the test sequence generator
			// needs it
			if (generator instanceof AsmTestSeqGenerator) {
				((AsmTestSeqGenerator) generator).setTestGenerator(this);
			}
			// RUN THE MODEL CHECKER !!!
			Pair<MCAnalysisResult, AsmTestSequence> result = (Pair<MCAnalysisResult, AsmTestSequence>) generator
					.executeAndAnalyze(tc);
			MCAnalysisResult tr = result.getFirst();
			tr.setTime(System.currentTimeMillis() - startTime);
			fireTestConditionCompleted(tc);
			// save if requested
			// Salva il testcondition su file and set time
			if (tr.isTestFound()) {
				result.getSecond().time = tr.getTime();
				if (this.automaticSave) {
					File f = new File(tc.getName() + ".tc");
					PrintStream dst;
					try {
						dst = new PrintStream(new FileOutputStream(f));
						dst.println((new toXML().export(result.getSecond())));
						dst.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		} catch (ModelCheckerExecutionException e) {
			// backup the old state
			e.printStackTrace();
			return new Pair<MCAnalysisResult, AsmTestSequence>(MCAnalysisResult.notFound(e.toString()),null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Pair<MCAnalysisResult, AsmTestSequence>(MCAnalysisResult.notFound(e.toString()),null);
		}
	}

}
