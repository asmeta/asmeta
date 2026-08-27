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

import java.io.File;

import org.apache.log4j.Logger;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.preferences.TGLibPreferences;
import tgtlib.util.CmdExecutor;
import tgtlib.util.Pair;

/**
 * test generators based on external programs: each needs - a temp directory
 * where to store the files - some external programs to execute. It uses files
 * to return the result of the running the model checker Moreover, when
 * analyzing the results, it also builds the test sequences. (only one analyze
 * is used) Maybe, in the future two methods like for the super class;
 * 
 * @param <Q>
 *            the generic type for test predicates
 * @param <T>
 *            the generic type for test sequences
 * @param <I>
 *            the generic type for input to the model checker
 * @author garganti
 * @version $Revision: 1.0 $
 */

public abstract class ExternalToolTGen<Q extends TestPredicate<? extends T, ?>, T extends tgtlib.definitions.TestSequence<? extends Q>, I extends MCInput<? extends Q>>
		extends TestSequenceGenerator<Q, T, MCExecutionResultReader> {

	/**
	 * Constructor for ExternalToolTGen.
	 * 
	 * @param q
	 *            TestSequenceFactory<T,Q>
	 */
	protected ExternalToolTGen(TestSequenceFactory<T, ? super Q> q) {
		super(q);
	}

	/** the directory where to store temp files. */
	protected static File tempDir;

	/** The Constant deleteTempFiles. */
	protected static boolean deleteTempFiles = true;

	/** Logger for this class. */
	private static final Logger logger = Logger
			.getLogger(ExternalToolTGen.class);

	/**
	 * INTIALIZE the resources. Not in the constructor, so it can be changed
	 * every time
	 */
	protected void initTempFilePrefs() {
		tempDir = tgtlib.preferences.Utility.getTempDirPref();
		deleteTempFiles = TGLibPreferences.DELETE_TMP.getValue();
	}

	@Override
	public void initResources() {
		initTempFilePrefs();
		// set the time out
		if (TGLibPreferences.TIMEOUT.isChecked())
			CmdExecutor.setTimeOut(Integer.parseInt(TGLibPreferences.TIMEOUT
					.getValue().toString()));

	}

	/**
	 * check if the external program is correctly installed * @return boolean
	 */
	protected boolean exists() {
		return true;
	}

	/**
	 * Run the model checker and returns the input where results can be read.
	 * 
	 * @param input
	 *            to be given to the model checker (normally a String Buffer +
	 *            test goal) spec The specification as a String Buffer
	 *            representing the Spec in the language of the model checker (in
	 *            the future get the File or the spec directly. for example
	 *            using SAL the String for spec is not possible)
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return the stream containing the result produced by the model checker IT
	 *         CANNOT BE NULL!!! * * @throws ModelCheckerExecutionException if
	 *         the model checker was not able to run (or it was interrupted)
	 */
	public abstract MCExecutionResultReader runModelChecker(I input)
			throws ModelCheckerExecutionException;

	/**
	 * read the inputStrem, parse the results, and put in test sequence (the
	 * test sequence content is passed so that the calling decides where to put
	 * the events.
	 * 
	 * @param in
	 *            the input stream that is analyzed and parsed
	 * 
	 * 
	 * 
	 * @return the result for the analysis
	 */
	@Override
	public final MCAnalysisResult analyses(MCExecutionResultReader in) {
		throw new RuntimeException("use analyze and build test");
	};

	/**
	 * this analyze and build the test sequence at the same time
	 * 
	 * @param in : the result (do not close it at the end, it will be closed outside)
	 * @param tp
	 * 
	 * @return MCAnalysisResult
	 */
	public abstract MCAnalysisResult analyses(MCExecutionResultReader in, T tp);

	
	/**
	 * build the test sequence after having analyzed the test result
	 * 
	 * @param in
	 *            MCExecutionResultReader
	 * @param tp
	 *            T
	 */
	@Override
	public final void buildTest(MCExecutionResultReader in, T tp) {
		throw new RuntimeException("use analyze and build test");
	};

	/**
	 * run the model checker, builds a new TestSequence and put the results in
	 * it, return also the analysis results. It passes only the Test predicates
	 * to the model checker. TODO use only this one
	 * 
	 * @param tp
	 *            the tp
	 * @return the pair : the analysis result and the test (null if not found
	 *         for any reason) * @throws ModelCheckerExecutionException the
	 *         model checker execution exception * @throws IOException Signals
	 *         that an I/O exception has occurred.
	 */
	@Override
	public final Pair<MCAnalysisResult, T> executeAndAnalyze(Q tp)
			throws ModelCheckerExecutionException {
		assert tp != null;
		assert tp.getCondition() != null;
		// init super resources (temp file)
		initResources();
		//
		// run the model checker
		MCExecutionResultReader execResult = runModelChecker(tp);
		// build a new test sequence to be filled
		try {
			T ts = tsFactory.buildTestSequence(tp);
			// call the analysis and the building of the test sequence
			MCAnalysisResult tr = analyses(execResult, ts);
			// it should be
			// assert execResult.isValid();
			// close (if not already closed)
			if  (execResult.isValid()) 
				execResult.close();
			// TODO write better constraints among these two
			// assert !tr.isTestFound() || ts != null :
			// " if a test is found, it must be returned";
			// assert !tr.isUnfeasible() || ts != null :
			// " if a tc is infeasible, no test can be found";
			return new Pair<MCAnalysisResult, T>(tr, ts);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(-1);
			return null;
		}
	}

}
