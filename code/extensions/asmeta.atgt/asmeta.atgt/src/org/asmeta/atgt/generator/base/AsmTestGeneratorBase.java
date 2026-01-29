package org.asmeta.atgt.generator.base;


import java.util.Iterator;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import tgtlib.coverage.CovBuilderBySubCov;
import tgtlib.specification.Specification;

public class AsmTestGeneratorBase<S extends Specification> {

	static private Logger logger = Logger.getLogger(AsmTestGeneratorBase.class);
	
	/** compute coverage?*/
	protected final boolean coverageTp;

	protected S spec;
	
	// the coverage tree built according to some criteria
	protected AsmCoverage ct;

	protected AsmTestGeneratorBase(boolean coverageTp){
		this.coverageTp = coverageTp;
	}

	/**
	 * Builds the TP tree and queue all the termTran according to the regex
	 *
	 * @param criteria the criteria
	 * @param maxTests the max tests
	 * @param regex the regex
	 */
	public void buildTPTree(CovBuilderBySubCov<S, AsmTestCondition, AsmCoverage> criteria, int maxTests, String regex) {
		logger.debug("generating the tp tree for criteria " + criteria.getCoveragePrefix());
		// build the tree depending on the criteria
		ct = criteria.getTPTree(spec);
		// queue tps
		queueTPs(maxTests, regex);
		// print them
		for (AsmTestCondition tp : ct.allTPs()) {
			logger.debug(tp.getName() + "\t" + tp.getCondition());
		}
	}

	/** Queue all tops for test generation
	 * @param maxNTP
	 * @param ct
	 * @param regex
	 */
	public void queueTPs(int maxNTP, String regex) {
		// queue all TPs
		int i = 1;
		for (Iterator<AsmTestCondition> iterator = ct.allTPs().iterator(); iterator.hasNext() && i <= maxNTP;) {
			AsmTestCondition tc = iterator.next();
			String name = tc.getName();
			if (!name.matches(regex)) {
				continue;
			}
			tc.setToVerify(true);
			logger.debug("termTran " + name + " queued");
			if (i++ > maxNTP) {
				logger.debug("max number of termTran reached");
				break;
			}
		}
	}

	
}
