package org.asmeta.atgt.generator.combinatorial;

import java.util.Enumeration;

import org.apache.log4j.Logger;

import atgt.combinatorial.NWiseCoverage;
import atgt.combinatorial.PairEqTestCondition;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestCondition;
import atgt.generator.AsmMonitoredDataExtractor;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import extgt.coverage.combinatorial.MonitoredData;
import extgt.coverage.combinatorial.StdPairwiseCovBuild;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.Type;

/**
 * given a specification extracts the data. is a Specification Visitor which
 * returns a MonitoredData instance not only monitored also controlled
 *
 * standard: only finite domains of type EnumType
 */
public class AsmAllDataExtractor extends AsmMonitoredDataExtractor {

	static public AsmAllDataExtractor INSTANCE = new AsmAllDataExtractor();

	static public AsmCoverageBuilder getAsmCombCovBuilder() {
		StdPairwiseCovBuild<ASMSpecification, AsmTestCondition, AsmCoverage> cov = new StdPairwiseCovBuild<>(
				INSTANCE, PairEqTestCondition.factory, NWiseCoverage.factory);
		return new AsmCoverageBuilder() {
			@Override
			public String getCoveragePrefix() {
				return cov.getCoveragePrefix();
			}

			@Override
			public AsmCoverage getTPTree(ASMSpecification spec) {
				return cov.getTPTree(spec);
			}

		};
	}

	private AsmAllDataExtractor() {}

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(AsmAllDataExtractor.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.specification.SpecificationAnalyzer#analyze(atgt.specification.
	 * ASMSpecification)
	 */

	/**
	 * returns the monitored data for the SP. It takes into account only boolean and
	 * enumerative. Other types are ignored.
	 *
	 * @param SP the sP
	 *
	 * @return the monitored data
	 */
	@Override
	public MonitoredData analyze(ASMSpecification SP) {
		//
		MonitoredData result = new MonitoredData();
		// take the monitored variables
		for (Enumeration<atgt.specification.location.Variable> e = SP.allVariables(); e.hasMoreElements();) {
			Variable var = e.nextElement();
			if (!(var.isControlled() || var.isMonitored())) {
				logger.info("ignoring variable " + var.getName()
						+ " because is not monitored and not controlled - what type is this???");
				continue;
			}
			// process var
			Type type = var.getType();
			if (!(type instanceof ElementsType)) {
				logger.info("ignoring variable " + var.getName() + " because of type " + type); //$NON-NLS-1$
				continue;
			}
			assert (type instanceof ElementsType);
			logger.info("adding " + var.getName() + " of type " + type); //$NON-NLS-1$
			result.add(var);
		}
		return result;
	}
}