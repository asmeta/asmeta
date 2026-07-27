/*
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

import static atgt.preferences.ATGToolPreferences.TP_ORDERING;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestCondition;
import atgt.generator.AsmMonitoredDataExtractor;
import atgt.preferences.ATGToolPreferences.OrderKind;
import atgt.specification.ASMSpecification;
import extgt.coverage.combinatorial.AntiDiagCovBuild;
import extgt.coverage.combinatorial.CombinatorialCovBuilder;
import extgt.coverage.combinatorial.MonitoredData;
import extgt.coverage.combinatorial.NWiseCovBuilder;
import extgt.coverage.combinatorial.PairwiseCovBuilder;
import extgt.coverage.combinatorial.StdPairwiseCovBuild;
import tgtlib.coverage.CoverageTreeFactory;


/**
 * Given an monitoredDataExtractor, build the Combinatorial Coverage Tree
 * @author garganti
 * 
 */
public abstract class AsmCombCovBuilder extends
		CombinatorialCovBuilder<ASMSpecification, AsmCoverage>  implements AsmCoverageBuilder{
	private static final CoverageTreeFactory<NWiseCoverage> COV_FACT = NWiseCoverage.factory;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AsmCombCovBuilder.class);

	public AsmCombCovBuilder(AsmMonitoredDataExtractor monDatExt) {
		super(monDatExt);
	}	
	/** TOFIX: per avere una AsmCoverageBuilder a partrie da una sottoclasse generica*/
	public static AsmCoverageBuilder get(PairwiseCovBuilder<ASMSpecification, AsmTestCondition, AsmCoverage> cov) {
		return new AsmCoverageBuilder() {

			@Override
			public String getCoveragePrefix() {
				return cov.getCoveragePrefix();
			}

			@Override
			public AsmCoverage getTPTree(ASMSpecification spec) {
				return cov.getTPTree(spec);
			}
			
		} ;
	}
	
	
	/**
	 * Instantiates a new pairwise cov builder. use this builder, since it
	 * builds every time a new pairwise coverage builder (for example
	 * diagonla/antidiagonal) static factory
	 * 
	 * @return the pairwise cov builder
	 */
	public static PairwiseCovBuilder<ASMSpecification, AsmTestCondition, AsmCoverage> makePairwiseCovBuilder() {
		// AG_INT_VERSION
		/*
		 * boolean considerInt = atgt.preferences.ATGToolPreferences.SALOPTION
		 * .getValue(atgt.preferences.ATGToolPreferences.Integer);
		 * log.debug("building PairwiseCovBuilder considering the intehgers? " +
		 * considerInt); if (considerInt) return new PairwiseCovBuildWithInt();
		 */
		boolean useDiagonal = TP_ORDERING.getValueAsEnum() == OrderKind.ANTIDIAGONAL;
		logger.debug("building PairwiseCovBuilder using antidiagonal? "	+ useDiagonal);
		if (!useDiagonal) {
			return new StdPairwiseCovBuild<ASMSpecification, AsmTestCondition, AsmCoverage>(
					AsmMonitoredDataExtractor.getMonitoredDataExtractor(),
					PairEqTestCondition.factory, COV_FACT);
		} else {
			return new AntiDiagCovBuild<ASMSpecification, AsmTestCondition, AsmCoverage>(
					AsmMonitoredDataExtractor.getMonitoredDataExtractor(),
					PairEqTestCondition.factory, COV_FACT);
		}

	}

	/**
	 * 
	 * @param n
	 *            must be greater then 2
	 * @return
	 */
	public static NWiseCovBuilder<ASMSpecification, AsmTestCondition, NWiseCoverage> createNWiseCovBuilder(
			int n) {
		assert n > 2;
		return new NWiseCovBuilder<ASMSpecification, AsmTestCondition, NWiseCoverage>(
				n, AsmMonitoredDataExtractor.getMonitoredDataExtractor(),
				NWiseCoverage.factory, NWiseEqTestCondition.factory);
	}

}
