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
package atgt.generator;

import org.apache.log4j.Logger;

import atgt.coverage.TestEvent;
import atgt.generator.testsuite.TestListener;

// TODO: Auto-generated Javadoc
/**
 * The Class TestGeneratorLogger.
 */
public class TestGeneratorLogger implements TestListener {

	/** Logger for this class. */
	private static final Logger logger = Logger
			.getLogger(TestGeneratorLogger.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.generator.TestListener#TestConditionStarted(atgt.coverage.TestEvent)
	 */
	@Override
	public void TestConditionStarted(TestEvent te) {
		logger.info("Starting test condition " + te.getSource().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.generator.TestListener#TestConditionCompleted(atgt.coverage.TestEvent)
	 */
	@Override
	public void TestConditionCompleted(TestEvent te) {
		logger.info("Test condition " + te.getSource().toString()
				+ " completed!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.generator.TestListener#TestConditionStepCompleted(atgt.coverage.TestEvent)
	 */
	@Override
	public void TestConditionStepCompleted(TestEvent te) {
		logger.info("  " + "Completed the step for "
				+ te.getSource().toString() + ": " + te.getDescription());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.generator.TestListener#TestConditionError(atgt.coverage.TestEvent)
	 */
	@Override
	public void TestConditionError(TestEvent te) {
		// txtMessage.append("MyEventListener:Errore in " +
		// te.getSource().toString()+"\n");
		logger.info("Error: " + te.getDescription());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.generator.TestListener#CoverageCompleted(atgt.coverage.TestEvent)
	 */
	@Override
	public void CoverageCompleted(TestEvent te) {
		logger.info(" Coverage " + te.getSource().toString() + " completed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.generator.TestListener#CoveragesCompleted(atgt.coverage.TestEvent)
	 */
	@Override
	public void CoveragesCompleted(TestEvent te) {
		logger.info(" Coverages " + te.getSource().toString() + " completed");
	}

}
