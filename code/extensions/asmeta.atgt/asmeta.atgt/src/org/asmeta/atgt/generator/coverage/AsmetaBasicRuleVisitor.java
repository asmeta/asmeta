package org.asmeta.atgt.generator.coverage;

import java.util.List;
import java.util.Vector;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.Coverage;
import tgtlib.definitions.NamedTerm;


public class AsmetaBasicRuleVisitor implements AsmetaCoverageBuilder {

	/**
	 * costruisce un nuovo basic rule visitor messo public per permettere la
	 * creazione da parte del plugin di eclipse.
	 */
	public AsmetaBasicRuleVisitor() {
	}

	@Override
	public String getCoveragePrefix() {
		return "ABR";		
	}

	@Override
	public AsmCoverage getTPTree(AsmetaAsSpec spec) {
		AsmetaConditionExtractor ce = new AsmetaConditionExtractor();
		List<AsmTestCondition> list = new Vector<AsmTestCondition>();
		List<NamedTerm> conditions = ce.visit(spec.asm.getMainrule().getRuleBody());
		for (NamedTerm ne : conditions) {
				list.add(new AsmTestCondition(ne.getName(), ne.getCondition()));
		}
		return new Coverage("ABR", list);
	}
}


