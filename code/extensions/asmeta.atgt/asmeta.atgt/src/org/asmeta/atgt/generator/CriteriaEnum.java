package org.asmeta.atgt.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.asmeta.atgt.generator.combinatorial.AsmDataExtractor;

import atgt.combinatorial.AsmCombCovBuilder;
import atgt.combinatorial.NWiseCoverage;
import atgt.combinatorial.NWiseEqTestCondition;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.BasicRuleVisitor;
import atgt.coverage.CompleteRuleVisitor;
import atgt.coverage.MCDCCoverage;
import atgt.coverage.RuleGuardVisitor;
import atgt.coverage.RuleUpdateVisitor;
import atgt.specification.ASMSpecification;
import extgt.coverage.combinatorial.NWiseCovBuilder;

/** enumeration form coverage criteria 
 * 
 * Wrapppers arounf 
 * 
 * */
public enum CriteriaEnum {
	
	BASIC_RULE("BasicRule",new BasicRuleVisitor()), 
	COMPLETE_RULE("CompleteRule",new CompleteRuleVisitor()),
	RULE_GUARD("RuleGuard",new RuleGuardVisitor()),
	RULE_UPDATE("RuleUpdate",new RuleUpdateVisitor()),
	MCDC("MCDC",MCDCCoverage.getCoverage()),
	// combinatorial
	COMBINATORIAL_MON("pairwise monitored", AsmCombCovBuilder.get(AsmCombCovBuilder.makePairwiseCovBuilder())),
	COMBINATORIAL_ALL("pairwise all", org.asmeta.atgt.generator.combinatorial.AsmDataExtractor.getAsmCombCovBuilder()),
	
	THREEWISE("3wise", triwiseCoveBuilder());

	/** TOFIX: temp per avere una AsmCoverageBuilder a partrie da una sottoclasse generica*/
	private static AsmCoverageBuilder triwiseCoveBuilder() {
		NWiseCovBuilder<ASMSpecification, AsmTestCondition, NWiseCoverage> cov = new NWiseCovBuilder<ASMSpecification, AsmTestCondition, NWiseCoverage>(
				3, new AsmDataExtractor(){},
				NWiseCoverage.factory, NWiseEqTestCondition.factory);
		
		return new AsmCoverageBuilder() {

			@Override
			public String getCoveragePrefix() {
				return"3-WISE";
			}

			@Override
			public AsmCoverage getTPTree(ASMSpecification spec) {
				return cov.getTPTree(spec);
			}
			
		} ;
	}

	
	CriteriaEnum(String name, AsmCoverageBuilder criteria) {
		this.name=name;
		this.criteria=criteria;
	}
	
	public String getAbbrvName() {
		return criteria.getCoveragePrefix();
	}
	
	public String name;
	public AsmCoverageBuilder criteria;
	
	
	// ******** UTILITIES *************
	public static List<String> toListOfString(CriteriaEnum... criteria) {
		return Arrays.asList(criteria).stream().map(c -> c.name()).collect(Collectors.toList());
	}
	// criteria --> list of string
	public static List<String> toListOfString(Collection<CriteriaEnum> criteria) {
		return toListOfString(criteria.toArray(new CriteriaEnum[0]));
	}
	
	// list of strings --> criteria
	public static List<CriteriaEnum> toListOfCriteriaEnum(List<String> criteria) {
		List<CriteriaEnum> res = new ArrayList<>();
		if (criteria==null || criteria.size()==0) return res;
		for (String c : criteria) res.add(CriteriaEnum.valueOf(c));
		return res;
	}
	
	public static List<AsmCoverageBuilder> getCoverageCriteria(Collection<CriteriaEnum> criteria) {
		List<AsmCoverageBuilder> res = new ArrayList<>();
		if (criteria==null || criteria.size()==0) return res;
		for (CriteriaEnum c : criteria) res.add(c.criteria);
		return res;
	}
	public static List<AsmCoverageBuilder> getCoverageCriteria(CriteriaEnum ... criteria) {
		return Arrays.asList(criteria).stream().map(c  -> c.criteria).collect(Collectors.toList());
	}
	
}
