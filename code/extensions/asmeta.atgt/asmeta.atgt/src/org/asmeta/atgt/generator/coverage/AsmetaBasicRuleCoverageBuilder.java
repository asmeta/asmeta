package org.asmeta.atgt.generator.coverage;

import java.util.List;
import java.util.Vector;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.Coverage;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.type.EnumConstCreator;


public class AsmetaBasicRuleCoverageBuilder extends AsmetaCoverageBuilder {

	/**
	 * costruisce un nuovo basic rule visitor messo public per permettere la
	 * creazione da parte del plugin di eclipse.
	 */
	public AsmetaBasicRuleCoverageBuilder() {
	}

	@Override
	public String getCoveragePrefix() {
		return "ABR";		
	}

	@Override
	public AsmCoverage getTPTree(AsmetaAsSpec spec) {
		EnumConstCreator icc = new EnumConstCreator();
		AsmetaConditionExtractor ce = new AsmetaConditionExtractor(icc, spec);
		List<AsmTestCondition> list = new Vector<AsmTestCondition>();
		List<NamedTerm> conditions = ce.visit(spec.asm.getMainrule().getRuleBody());
		for (NamedTerm ne : conditions) {
				list.add(new AsmTestCondition(ne.getName(), ne.getCondition()));
		}
		return new Coverage("ABR", list);
	}
}


