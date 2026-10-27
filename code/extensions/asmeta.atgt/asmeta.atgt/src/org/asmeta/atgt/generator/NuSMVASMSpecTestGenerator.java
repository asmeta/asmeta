package org.asmeta.atgt.generator;

import java.util.Collection;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CovBuilderBySubCov;

// generate test generator based on NuSMV for a given ASM specification (old way). It uses the class TestGenerationWithNuSMV to generate tests for each test purpose.
public class NuSMVASMSpecTestGenerator extends NuSMVTestGenerator<ASMSpecification> {


	private static Logger logger = Logger.getLogger(NuSMVASMSpecTestGenerator.class);

	NuSMVASMSpecTestGenerator(String asmFilePath) throws Exception {
		this(asmFilePath, true);
	}

	public NuSMVASMSpecTestGenerator(String asmFilePath, boolean coverageTp) throws Exception {
		super(asmFilePath, coverageTp, new ASMSpecificationReader());
		//Logger.getLogger(AsmetaLLoader.class).setLevel(Level.OFF);
		//Logger.getLogger(CoverageEvaluatorC.class).setLevel(Level.OFF);
	}

	
	/**
	 *
	 * @param maxTests
	 * @param regex
	 * @return
	 * @throws Exception
	 */
	public AsmTestSuite generateAbstractTests(int maxTests, String regex) throws Exception {
		// collect the coverage criteria
		buildTPTree(new MBTCoverage(DEFAULT_COV_BUILDER), maxTests, regex);
		return generateTests();
	}


	public AsmTestSuite generateAbstractTests(Collection<AsmCoverageBuilder> coverageCriteria, int maxTests, String regex) throws Exception {
		buildTPTree(new MBTCoverage(coverageCriteria), maxTests, regex);
		return generateTests();
	}
	
	

	/**
	 * Structural except MCDC which is difficult to use because there is an equal
	 * and Booleans
	 */
	public static class MBTCoverage extends CovBuilderBySubCov<ASMSpecification, AsmTestCondition, AsmCoverage> {

		public MBTCoverage(Collection<AsmCoverageBuilder> criteria) {
			super("MBT Coverage", AsmCoverageTree.factory);

			for (AsmCoverageBuilder c : criteria) {
				register(c);
			}

			// Aggiunge i visitor di default
			// Basic Rule Coverage Visitor
//			register(new BasicRuleVisitor());
			// Complete Rule Coverage Visitor
//			register(new CompleteRuleVisitor());
			// Rule Update Visitor
//			register(new RuleUpdateVisitor());
			// Full Predicate / MCDC Visitor
			// register(MCDCCoverage.getCoverage());
			// Rule Guard Visitor
			// NOT SURE IT WORKS
			// visitorCollection.add(new RuleGuardVisitor());
		}
	}
}