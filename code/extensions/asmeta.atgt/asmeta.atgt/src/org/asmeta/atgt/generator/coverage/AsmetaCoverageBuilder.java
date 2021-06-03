package org.asmeta.atgt.generator.coverage;

import atgt.coverage.AsmCoverage;
import tgtlib.coverage.CoverageBuilder;

/**
 * given a specification returns a tree of test predicates, by analyzing the
 * specification. It can be a CoverageTree or a simple Coverage
 *
 * @author garganti
 */


public interface AsmetaCoverageBuilder extends CoverageBuilder<AsmetaAsSpec, AsmCoverage>{
	
	
	@Override
	public AsmCoverage getTPTree(AsmetaAsSpec spec);
	

}
