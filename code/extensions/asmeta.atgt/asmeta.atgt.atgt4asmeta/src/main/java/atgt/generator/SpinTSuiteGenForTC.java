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

import atgt.coverage.AsmCoverageTree;
import atgt.generator.testsuite.AsmTestSuiteGenerator;
import atgt.generator.testsuite.TestSuiteGeneratorForTC;
import atgt.project.AsmProject;
import atgt.translator.ToSpinTranslatorVisitor;

/**
 * it generates a test for a Test Condition generation functionalities are
 * delegated to the test sequence generator this computes an AsmTesSuite at the
 * end. It differs: - it has two possible translators - it searches the coverage
 * by modifying the translation
 * 
 * TODO: unisci con SALTSUITEGENFORTC
 * 
 * @author Angelo Gargantini
 */
public class SpinTSuiteGenForTC extends
		TestSuiteGeneratorForTC<SpinTSeqGenerator> {

	/** Logger for this class. */
	private static final Logger logger = Logger
			.getLogger(SpinTSuiteGenForTC.class);

	/**
	 * Creates the flat spin t suite gen for tc.
	 * 
	 * @param pro
	 *            the pro
	 * 
	 * @return the spin t suite gen for tc
	 */
	public static SpinTSuiteGenForTC createFlatSpinTSuiteGenForTC(AsmProject pro) {
		ToSpinTranslatorVisitor visitor = new atgt.translator.toSPINFlatVisitor();
		SpinTSeqGenerator generator = new SpinTSeqGenerator(pro.specification,
				(AsmCoverageTree) pro.getTestTree(), visitor);
		return new SpinTSuiteGenForTC(pro, generator);
	}

	/**
	 * Creates the chan spin t suite gen for tc.
	 * 
	 * @param pro
	 *            the pro
	 * 
	 * @return the test suite generator
	 */
	public static AsmTestSuiteGenerator createChanSpinTSuiteGenForTC(
			AsmProject pro) {
		ToSpinTranslatorVisitor visitor = new atgt.translator.toSPINChanVisitor();
		SpinTSeqGenerator generator = new SpinTSeqGenerator(pro.specification,
				(AsmCoverageTree) pro.getTestTree(), visitor);
		return new SpinTSuiteGenForTC(pro, generator);
	}

	/**
	 * sometimes the coverage can be computed during the generatioN (like in
	 * spin) without going through the C phase TODO add preference for this
	 */
	private boolean coverageDuringGeneration = false;

	/**
	 * Instantiates a new spin t suite gen for tc.
	 * 
	 * @param pro
	 *            the pro
	 * @param _generator
	 *            the _generator
	 */
	private SpinTSuiteGenForTC(AsmProject pro, SpinTSeqGenerator _generator) {
		super(pro, _generator);
		// init the generator
		_generator.setCoverages((AsmCoverageTree) pro.getTestTree());
		//
		searchOtherCovs = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.generator.TestSuiteGenerator#setSearchCommonCoverage(boolean)
	 */
	@Override
	public void setSearchCommonCoverage(boolean searchCommonCoverage) {
		// do not change the set search for coverages, change only the
		// translator
		// if so it is desired by the user
		// by executing the spin the coverage can be evaluated by spin itself.
		// this however can make the compilation of the promela file a little bit
		// longer
		if (generator.visitor instanceof ToSpinTranslatorVisitor)
			if (coverageDuringGeneration) {
				logger.debug(searchCommonCoverage ? "computing the coverage by spin itself "
						: " not computing coverage");
				((ToSpinTranslatorVisitor) generator.visitor)
						.setSearchCommonCoverage(searchCommonCoverage);
				super.setSearchCommonCoverage(false);
			} else {
				logger.debug(searchCommonCoverage ? "computing the coverage by C "
						: " not computing coverage");
				((ToSpinTranslatorVisitor) generator.visitor)
						.setSearchCommonCoverage(false);
				// TODO experiments if the coverage by C works or not
				super.setSearchCommonCoverage(searchCommonCoverage);
			}
		else {
			super.setSearchCommonCoverage(searchCommonCoverage);
		}
	}

	public void setCoverageDuringGeneration(boolean coverageDuringGeneration) {
		this.coverageDuringGeneration = coverageDuringGeneration;
	}
}