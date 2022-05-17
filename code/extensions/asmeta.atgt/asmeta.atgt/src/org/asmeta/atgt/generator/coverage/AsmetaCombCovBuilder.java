package org.asmeta.atgt.generator.coverage;

import org.apache.log4j.Logger;

import atgt.combinatorial.NWiseCoverage;
import atgt.combinatorial.NWiseEqTestCondition;
import atgt.combinatorial.PairEqTestCondition;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.generator.AsmMonitoredDataExtractor;
import atgt.preferences.ATGToolPreferences.OrderKind;
import extgt.coverage.combinatorial.AntiDiagCovBuild;
import extgt.coverage.combinatorial.CombinatorialCovBuilder;
import extgt.coverage.combinatorial.NWiseCovBuilder;
import extgt.coverage.combinatorial.PairwiseCovBuilder;
import extgt.coverage.combinatorial.StdPairwiseCovBuild;
import tgtlib.coverage.CoverageTreeFactory;

/**
 * Given an monitoredDataExtractor, build the Combinatorial Coverage Tree
 * @author garganti
 * 
 */
public abstract class AsmetaCombCovBuilder extends
		CombinatorialCovBuilder<AsmetaAsSpec, AsmCoverage>  implements AsmetaCoverageBuilder{
	private static final CoverageTreeFactory<NWiseCoverage> COV_FACT = NWiseCoverage.factory;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AsmetaCombCovBuilder.class);

	public AsmetaCombCovBuilder(AsmetaMonitoredDataExtractor monDatExt) {
		super(monDatExt);
	}	
	/** TOFIX: per avere una AsmCoverageBuilder a partrie da una sottoclasse generica*/
	public static AsmetaCoverageBuilder get(PairwiseCovBuilder<AsmetaAsSpec, AsmTestCondition, AsmCoverage> cov) {
		return new AsmetaCoverageBuilder() {

			@Override
			public String getCoveragePrefix() {
				return cov.getCoveragePrefix();
			}

			@Override
			public AsmCoverage getTPTree(AsmetaAsSpec spec) {
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
	public static PairwiseCovBuilder<AsmetaAsSpec, AsmTestCondition, AsmCoverage> makePairwiseCovBuilder() {
//		// AG_INT_VERSION
//		/*
//		 * boolean considerInt = atgt.preferences.ATGToolPreferences.SALOPTION
//		 * .getValue(atgt.preferences.ATGToolPreferences.Integer);
//		 * log.debug("building PairwiseCovBuilder considering the intehgers? " +
//		 * considerInt); if (considerInt) return new PairwiseCovBuildWithInt();
//		 */
//		boolean useDiagonal = TP_ORDERING.getValueAsEnum() == OrderKind.ANTIDIAGONAL;
//		logger.debug("building PairwiseCovBuilder using antidiagonal? "	+ useDiagonal);
//		if (!useDiagonal) {
			return new StdPairwiseCovBuild<AsmetaAsSpec, AsmTestCondition, AsmCoverage>(
					AsmetaMonitoredDataExtractor.getMonitoredDataExtractor(),
					PairEqTestCondition.factory, COV_FACT);
//		} else {
//			return new AntiDiagCovBuild<AsmetaAsSpec, AsmTestCondition, AsmCoverage>(
//					AsmMonitoredDataExtractor.getMonitoredDataExtractor(),
//					PairEqTestCondition.factory, COV_FACT);
//		}
	}

	/**
	 * 
	 * @param n
	 *            must be greater then 2
	 * @return
	 */
	public static NWiseCovBuilder<AsmetaAsSpec, AsmTestCondition, NWiseCoverage> createNWiseCovBuilder(
			int n) {
		assert n > 2;
		return new NWiseCovBuilder<AsmetaAsSpec, AsmTestCondition, NWiseCoverage>(
				n, AsmetaMonitoredDataExtractor.getMonitoredDataExtractor(),
				NWiseCoverage.factory, NWiseEqTestCondition.factory);
	}

}