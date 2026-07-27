/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.generator;

import java.io.IOException;

import org.apache.log4j.Logger;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.util.Pair;

/**
 * A tests generator generates test sequences with a particular model checker
 * (or other algorithm) it must provide:
 * 
 * <pre>
 * - a running environment for the single test case
 * - a translation from the output in a test case (parsing) provided by translator
 * - NOW: - a translation in the tool language.
 * </pre>
 * 
 * TODO move here (so also the MC which do not use a string can be used, for
 * example SAL, yices....)
 *
 * @param <Q> the generic type representing the test predicates
 * @param <T> the generic type representing the test sequence
 * @param <I> the generic type representing the input required by the tool
 * @param <R> the generic type representing the out of the execution of the tool (a file or whatever)
 */
//public abstract class TestSequenceGenerator<Q extends TestPredicate<T,?>, T extends tgtlib.definitions.TestSequence<Q>, R extends MCExecutionResult> {
public abstract class TestSequenceGenerator<Q extends TestPredicate<? extends T,?>, T extends TestSequence<? extends Q>, R extends MCExecutionResult> {

	static private final Logger logger = Logger.getLogger(TestSequenceGenerator.class);
	
	/** to build new test sequences */
	protected TestSequenceFactory<T, ? super Q> tsFactory;

	/**
	 * 
	 * @param q
	 *            the factory for test sequences
	 */
	protected TestSequenceGenerator(TestSequenceFactory<T, ? super Q> q) {
		tsFactory = q;
	}

	/**
	 * INTIALIZE the resources. Not in the constructor, so it can be changed
	 * every time
	 */
	public abstract void initResources();

	/**
	 * run for the single Test predicate TODO: is this really useful????
	 * Assuming that everything has been set before (like specification, axioms and so on)
	 * and just the tp is missing.
	 * 
	 * 
	 * @throws IOException
	 * 
	 */
	protected abstract R runModelChecker(Q tp)
			throws ModelCheckerExecutionException;

	/**
	 * read the inputStream, parse the results to decide the final result
	 * 
	 * @param in
	 *            the input stream that is analyzed and parsed
	 * @param tp
	 *            the test sequence initially empty which is filled when the
	 *            result is parsed
	 * 
	 * @return the result for the analysis
	 */
	public abstract MCAnalysisResult analyses(R in);

	/**
	 * take the analysis result and parse it and put in test sequence (the
	 * test sequence content is passed so that the calling decides where to put
	 * the events.)
	 * 
	 * @param in
	 *            the input stream that is analyzed and parsed
	 * @param tp
	 *            the test sequence initially empty which is filled when the
	 *            result is parsed
	 * 
	 */
	public abstract void buildTest(R in, T tp);
//	public abstract void buildTest(R in, TestSequence<? extends Q> tp);
	
	
	/**
	 * run the model checker, builds a new TestSequence and put the results in it,
	 * return also the analysis results. It passes only the Test predicates to the model checker.
	 * TODO use only this one
	 *
	 * @param tp the tp
	 * @return the pair : the analysis result and the test (null if not found for any reason)
	 * @throws ModelCheckerExecutionException the model checker execution exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Pair<MCAnalysisResult, T> executeAndAnalyze(Q tp) throws ModelCheckerExecutionException{
//	public Pair<MCAnalysisResult, TestSequence<? extends Q>> executeAndAnalyze(Q tp) throws ModelCheckerExecutionException{
		assert tp != null;
		assert tp.getCondition() != null;
		// init super resources (temp file)
		initResources();
		//		
		// run the model checker
		R execResult = runModelChecker(tp);
		// call the analysis
		//System.out.println(tp);
		MCAnalysisResult tr = analyses(execResult);
		T ts = null;
		if (tr.isTestFound()) {
			ts = buildTestFor(tp, execResult);
			// assert ts.size >0;
		}
		// close 
		execResult.close();
		// TODO write better constraints among these two
		//assert !tr.isTestFound() || ts != null : " if a test is found, it must be returned";
		//assert !tr.isUnfeasible() || ts != null : " if a tc is infeasible, no test can be found";
		return new Pair<MCAnalysisResult, T>(tr, ts);
	}

	/**build the test for tp reading the result from execResult
	 * 
	 * @param tp
	 * @param execResult
	 * @return
	 */
	public final T buildTestFor(Q tp, R execResult) {
		logger.debug("building test sequence for " + tp + "[" + tp.getClass() + "]" + ", tsfactory " + tsFactory.getClass());
		// build a new test sequence to be filled
		T ts = tsFactory.buildTestSequence(tp);
		buildTest(execResult, ts);
		return ts;
	}
}