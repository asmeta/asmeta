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

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.Coverage;
import tgtlib.specification.Specification;

/**
 * generates the tests simply calling the test generator method for every test
 * predicate in the coverage. It does not follow any particular order.
 * 
 * @author garganti
 */

public abstract class TestGeneratorForCoverages extends AsmTestSuiteGenerator {
	
	/** Logger for this class. */
	private static final Logger log = Logger.getLogger(TestGeneratorForCoverages.class);

	/**
	 * Instantiates a new test generator for coverages.
	 * 
	 * @param spec
	 *            the specification
	 * @param coverage
	 *            the coverage already computed
	 */
	public TestGeneratorForCoverages(Specification spec, AsmCoverage coverage) {
		super(spec, coverage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.generator.TestGeneratorForCoverageTree#forCoverage(atgt.coverage
	 * .Coverage)
	 */
	@Override
	public final AsmTestSuite forCoverage(Coverage cv) {
		log.debug("generating for coverage " + cv.toString());
		AsmTestSuite result = new AsmTestSuite();
		for (AsmTestCondition tc : cv.allTPs()) {
			if (this.tcFilter.accept(tc)) {
				log.debug("running" + tc);
				fireTestConditionStarted(tc);
				AsmTestSuite partial = tc.accept(this);
				result.addAllTest(partial);
				fireTestConditionCompleted(tc);
			} else {
				log.debug("skipping " + tc);
			}
		}
		return result;
	}
}
