package org.asmeta.atgt.generator.coverage;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CoverageBuilder;

/**
 * given a specification returns a tree of test predicates, by analyzing the
 * specification. It can be a CoverageTree or a simple Coverage
 *
 * @author garganti
 */


//public interface AsmetaCoverageBuilder extends CoverageBuilder<AsmetaAsSpec, AsmCoverage>{
public abstract class AsmetaCoverageBuilder implements AsmCoverageBuilder{
	
	
	@Override
	public AsmCoverage getTPTree(ASMSpecification spec) {
		return getTPTree((AsmetaAsSpec)spec);
	}
		
	public abstract AsmCoverage getTPTree(AsmetaAsSpec spec);
	

}
